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
package me.crypnotic.empires.command;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import me.crypnotic.empires.api.Strings;
import me.crypnotic.empires.api.command.CommandContext;
import me.crypnotic.empires.api.command.ICommand;
import me.crypnotic.empires.api.player.EmpirePlayer;

public class HelpCommand implements ICommand {

	private static final int pageLimit = 7;
	private static final Comparator<ICommand> sorter = (command1, command2) -> {
		return command1.getName().compareToIgnoreCase(command2.getName());
	};

	@Override
	public void execute(EmpirePlayer player, CommandContext context) {
		if (context.size() > 0) {
			Integer page = context.getInteger(0);
			if (page > 0) {
				print(player, getCommands(page - 1), page);
			} else {
				player.message("&cPage number must be greater than zero.");
			}
		} else {
			print(player, getCommands(0), 1);
		}
	}

	private void print(EmpirePlayer player, Collection<ICommand> commands, int page) {
		if (!commands.isEmpty()) {
			player.message("&eShowing commands for page: &a" + page);

			for (ICommand command : commands) {
				String message = "  &a/em " + command.getName() + " &e: &a" + command.getDescription();

				player.message(message.length() > 50 ? Strings.trim(message, 50) + "&e..." : message);
			}

			if (commands.size() < pageLimit) {
				player.message("&eType &a/em help " + (page + 1) + " &efor more commands.");
			}
		} else {
			player.message("&cThat page does not exist.");
		}
	}

	private Collection<ICommand> getCommands(int page) {
		return getCommandManager().stream().sorted(sorter).skip(page * pageLimit).limit(pageLimit)
				.collect(Collectors.toList());
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getDescription() {
		return "&eDisplay useful commands and how to use them";
	}
}
