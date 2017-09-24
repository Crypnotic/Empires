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
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.crypnotic.empires.api.Strings;
import me.crypnotic.empires.api.config.Config;
import me.crypnotic.empires.api.config.ConfigSection;
import me.crypnotic.empires.api.config.ConfigType;
import me.crypnotic.empires.api.player.EmpirePlayer;

public class PlayerManager {

	private final ConfigManager configManager;
	private final EmpireManager empireManager;
	private final Map<UUID, EmpirePlayer> players;

	public PlayerManager(ConfigManager configManager, EmpireManager empireManager) {
		this.configManager = configManager;
		this.empireManager = empireManager;
		this.players = new HashMap<UUID, EmpirePlayer>();
	}

	public void init() {
		if (!players.isEmpty()) {
			players.clear();
		}

		for (Player target : Bukkit.getServer().getOnlinePlayers()) {
			load(target.getUniqueId());

			EmpirePlayer player = get(target.getUniqueId());
			if (player.getEmpire() != null) {
				player.getEmpire().addOnline(player);
			}
		}
	}

	public EmpirePlayer get(UUID uuid) {
		return players.get(uuid);
	}

	public EmpirePlayer load(UUID uuid) {
		if (players.containsKey(uuid)) {
			return get(uuid);
		}

		Config config = configManager.get(ConfigType.PLAYERS);
		ConfigSection section = config.getSection("players." + Strings.serializeUUID(uuid));

		EmpirePlayer player = new EmpirePlayer(uuid);

		if (section.contains("empire")) {
			player.setEmpire(empireManager.getEmpireByName(section.get("empire").asString()));
		}
		return player;
	}

	public boolean save(EmpirePlayer player) {

		return true;
	}
}
