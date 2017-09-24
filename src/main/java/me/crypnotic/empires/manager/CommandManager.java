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

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.crypnotic.empires.api.command.CommandContext;
import me.crypnotic.empires.api.command.ICommand;
import me.crypnotic.empires.api.player.EmpirePlayer;
import me.crypnotic.empires.command.HelpCommand;
import me.crypnotic.empires.command.InviteCommand;
import me.crypnotic.empires.command.JoinCommand;

public class CommandManager implements CommandExecutor {

	private final PlayerManager playerManager;
	private final Map<String, ICommand> commands;

	public CommandManager(PlayerManager playerManager) {
		this.playerManager = playerManager;
		this.commands = new HashMap<String, ICommand>();
	}

	public void init() {
		commands.put("help", new HelpCommand());
		commands.put("invite", new InviteCommand());
		commands.put("join", new JoinCommand());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			EmpirePlayer player = playerManager.get(((Player) sender).getUniqueId());

			if (args.length > 0) {
				String subcommand = args[0].toLowerCase();
				if (commands.containsKey(subcommand)) {
					String[] arguments = new String[args.length - 1];
					System.arraycopy(args, 1, arguments, 0, arguments.length);
					commands.get(subcommand).execute(player, new CommandContext(arguments));
				} else {
					commands.get("help").execute(player, null);
				}
			} else {
				commands.get("help").execute(player, null);
			}
		} else {
			sender.sendMessage("Only players can use this command.");
		}
		return true;
	}
}
