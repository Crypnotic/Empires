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

public class LeaveCommand implements ICommand {

	@Override
	public void execute(EmpirePlayer player, CommandContext context) {
		if (player.getEmpire() != null) {
			Empire empire = player.getEmpire();
			if (!empire.isOwner(player.getUuid())) {
				empire.removeMember(player.getUuid());
				empire.removeOnline(player);
				empire.broadcast("&a" + player.getName() + " &ehas left the empire.");

				player.setEmpire(null);

				player.message("&eSuccessfully left &a" + empire.getName());

				getEmpireManager().save(empire);
				getPlayerManager().save(player);
			} else {
				player.message("&cYou can't leave an empire that you own.");
			}
		} else {
			player.message("&cYou are not a part of an empire.");
		}
	}

	@Override
	public String getName() {
		return "leave";
	}

	@Override
	public String getDescription() {
		return "&eLeave your current Empire";
	}
}
