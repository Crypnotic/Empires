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

import me.crypnotic.empires.api.command.CommandContext;
import me.crypnotic.empires.api.command.ICommand;
import me.crypnotic.empires.api.empire.Empire;
import me.crypnotic.empires.api.player.EmpirePlayer;

public class CreateCommand implements ICommand {

	@Override
	public void execute(EmpirePlayer player, CommandContext context) {
		if (context.size() > 0) {
			if (player.getEmpire() == null) {
				String name = context.get(0);
				Empire holder = getEmpireManager().getEmpireByName(name);
				if (holder == null) {
					Empire empire = getEmpireManager().add(name, player.getUuid());
					if (empire != null) {
						empire.addOnline(player);

						player.setEmpire(empire);
						player.message("&eSuccessfully created empire: &a" + empire.getName());

						getPlayerManager().save(player);
					} else {
						player.message("&cYour empire could not be created, please try again later.");
					}
				} else {
					player.message("&cAn empire already exists with that name.");
				}
			} else {
				player.message("&cYou're already a part of another empire.");
			}
		} else {
			player.message("&cUsage: /em create (name)");
		}
	}

	@Override
	public String getName() {
		return "create";
	}

	@Override
	public String getDescription() {
		return "&eCreate a new Empire with the name of your choice";
	}
}
