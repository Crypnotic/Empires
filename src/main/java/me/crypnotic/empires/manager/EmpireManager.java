/*
 * This file is part of Empires, licensed under the MIT License (MIT).
 *
 * Copyright (c) Crypnotic <https://www.crypnotic.me>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package me.crypnotic.empires.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Chunk;

import me.crypnotic.empires.EmpiresPlugin;
import me.crypnotic.empires.api.Strings;
import me.crypnotic.empires.api.config.Config;
import me.crypnotic.empires.api.config.ConfigSection;
import me.crypnotic.empires.api.config.ConfigType;
import me.crypnotic.empires.api.empire.Empire;
import me.crypnotic.empires.api.empire.Territory;
import me.crypnotic.empires.api.player.EmpirePlayer;

public class EmpireManager {

	private final EmpiresPlugin plugin;
	private final ConfigManager configManager;
	private final Map<String, Empire> empires;

	public EmpireManager(EmpiresPlugin plugin, ConfigManager configManager) {
		this.plugin = plugin;
		this.configManager = configManager;
		this.empires = new HashMap<String, Empire>();
	}

	public boolean init() {
		Config config = configManager.get(ConfigType.EMPIRES);

		for (String id : config.getKeys("empires")) {
			ConfigSection section = config.getSection("empires." + id);

			String name = section.get("name").asString();
			UUID owner = section.get("owner").asUUID();
			Double balance = section.get("balance").asDouble();
			Territory territory = Territory.deserialize(section.get("territory").asString());
			Set<UUID> members = section.get("members").asUUIDSet();

			Empire empire = new Empire(id, name, owner, balance, territory, members);

			empires.put(id, empire);
		}

		return true;
	}

	public Empire add(String name, UUID owner) {
		String id = name.toLowerCase();
		if (empires.containsKey(id)) {
			return null;
		}
		Empire empire = new Empire(id, name, owner, 0, new Territory(new HashSet<Chunk>()), new HashSet<UUID>());

		empires.put(name, empire);

		save(empire);

		return empire;
	}

	public boolean remove(Empire empire, EmpirePlayer player) {
		if (!empires.containsKey(empire.getId())) {
			return false;
		}

		Config config = configManager.get(ConfigType.EMPIRES);
		config.set("empires." + empire.getId(), null);

		boolean removed = config.save();
		if (removed) {
			empires.remove(empire.getId());

			/*
			 * Making sure the player that is removing the Empire is the owner.
			 * This makes /em delete (name) possible
			 */
			if (player.getEmpire().equals(empire)) {
				player.setEmpire(null);
				plugin.getPlayerManager().save(player);
			}

			empire.broadcast("&a" + empire.getName() + " &ehas been disbanded by &a" + player.getName());

			for (UUID uuid : empire.getMembers()) {
				EmpirePlayer target = plugin.getPlayerManager().load(uuid, false);

				target.setEmpire(null);

				plugin.getPlayerManager().save(target);
			}
		}

		return removed;
	}

	public boolean save(Empire empire) {
		String id = empire.getId();

		Config config = configManager.get(ConfigType.EMPIRES);
		ConfigSection section = config.getSection("empires." + id);

		section.set("name", empire.getName());
		section.set("owner", Strings.serializeUUID(empire.getOwner()));
		section.set("balance", empire.getBalance());
		section.set("territory", empire.getTerritory().serialize());
		section.set("members", Strings.serializeUUIDSet(empire.getMembers()));

		return config.save();
	}

	public Empire getEmpireByName(String name) {
		return empires.get(name.toLowerCase());
	}

	public Empire getEmpireByChunk(final Chunk chunk) {
		return empires.values().stream().filter(empire -> empire.getTerritory().contains(chunk)).findAny().orElse(null);
	}
}
