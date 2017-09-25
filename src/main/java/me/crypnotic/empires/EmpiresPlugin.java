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
package me.crypnotic.empires;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import me.crypnotic.empires.api.config.ConfigType;
import me.crypnotic.empires.listener.MechanicsListener;
import me.crypnotic.empires.manager.CommandManager;
import me.crypnotic.empires.manager.ConfigManager;
import me.crypnotic.empires.manager.EmpireManager;
import me.crypnotic.empires.manager.PlayerManager;

public class EmpiresPlugin extends JavaPlugin {

	@Getter
	private static EmpiresPlugin plugin;

	@Getter
	private CommandManager commandManager;
	@Getter
	private ConfigManager configManager;
	@Getter
	private EmpireManager empireManager;
	@Getter
	private PlayerManager playerManager;

	@Override
	public void onLoad() {
		EmpiresPlugin.plugin = this;

		this.configManager = new ConfigManager(getDataFolder());
		this.empireManager = new EmpireManager(this, configManager);
		this.playerManager = new PlayerManager(configManager, empireManager);
		this.commandManager = new CommandManager(playerManager);
	}

	@Override
	public void onEnable() {
		if (configManager.init().size() != ConfigType.values().length) {
			getLogger().severe("Could not load configuration files!");
			setEnabled(false);
			return;
		}

		if (!empireManager.init()) {
			getLogger().severe("Could not load empires from file storage!");
			setEnabled(false);
			return;
		}

		playerManager.init();
		commandManager.init();

		getCommand("empire").setExecutor(commandManager);
		getServer().getPluginManager().registerEvents(new MechanicsListener(empireManager, playerManager), this);
	}

	@Override
	public void onDisable() {

	}
}
