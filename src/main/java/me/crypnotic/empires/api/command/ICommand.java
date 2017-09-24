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
package me.crypnotic.empires.api.command;

import java.util.logging.Level;

import org.bukkit.entity.Player;

import me.crypnotic.empires.EmpiresPlugin;
import me.crypnotic.empires.api.player.EmpirePlayer;
import me.crypnotic.empires.manager.ConfigManager;
import me.crypnotic.empires.manager.EmpireManager;
import me.crypnotic.empires.manager.PlayerManager;

public interface ICommand {

	void execute(EmpirePlayer player, CommandContext context);

	default EmpiresPlugin getPlugin() {
		return EmpiresPlugin.getPlugin();
	}

	default ConfigManager getConfigManager() {
		return getPlugin().getConfigManager();
	}

	default EmpireManager getEmpireManager() {
		return getPlugin().getEmpireManager();
	}

	default PlayerManager getPlayerManager() {
		return getPlugin().getPlayerManager();
	}

	default void log(Level level, String message) {
		getPlugin().getLogger().log(level, message);
	}

	default Player getPlayer(String name) {
		return getPlugin().getServer().getPlayer(name);
	}
}
