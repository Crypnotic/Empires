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

public class DisbandCommand implements ICommand {

	@Override
	public void execute(EmpirePlayer player, CommandContext context) {
		if (player.getEmpire() != null) {
			Empire empire = player.getEmpire();
			if (empire.isOwner(player.getUuid())) {
				if (context.size() > 0 && context.get(0).equalsIgnoreCase("confirm")) {
					if (!getEmpireManager().remove(empire, player)) {
						/*
						 * TODO Notify owner that Empire balance has been placed
						 * into their account along with other relevant
						 * information
						 */
					} else {
						player.message("&cYour empire could not be disbanded, please try again later.");
					}
				} else {
					player.message("&eDisbanding your empire is irreversible.");
					player.message("&eType &a/em disband confirm &eto confirm.");
				}
			} else {
				player.message("&cYou must own your empire to disband it.");
			}
		} else {
			player.message("&cYou must own an empire to invite players.");
		}
	}

	@Override
	public String getName() {
		return "disband";
	}

	@Override
	public String getDescription() {
		return "&eDisband your Empire; this removes all territory and kicks all members";
	}
}
