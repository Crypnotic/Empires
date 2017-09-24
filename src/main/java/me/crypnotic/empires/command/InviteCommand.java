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

import org.bukkit.entity.Player;

import me.crypnotic.empires.api.command.CommandContext;
import me.crypnotic.empires.api.command.ICommand;
import me.crypnotic.empires.api.empire.Empire;
import me.crypnotic.empires.api.player.EmpirePlayer;

public class InviteCommand implements ICommand {

	@Override
	public void execute(EmpirePlayer player, CommandContext context) {
		if (context.size() > 0) {
			if (player.getEmpire() != null) {
				Empire empire = player.getEmpire();
				if (empire.isOwner(player.getUuid())) {
					Player targetPlayer = getPlayer(context.get(0));
					if (targetPlayer != null) {
						EmpirePlayer target = getPlayerManager().get(targetPlayer.getUniqueId());
						if (player.equals(target)) {
							if (target.getEmpire() == null) {
								if (!empire.equals(target.getEmpire())) {
									empire.addInvite(target);

									target.message(
											"&a" + player.getName() + " &einvited you to join &c" + empire.getName());
									target.message("&eType: &a/empire join " + empire.getName()
											+ " &e in the next 60 seconds to join.");
									player.message("&eSuccessfully invited &a" + target.getName());

									getPlugin().getServer().getScheduler().runTaskLater(getPlugin(), () -> {
										if (empire.getInvites().contains(target)) {
											empire.removeInvite(target);
										}
									}, 1200);
								} else {
									player.message("&c" + target.getName() + " is already a part of your empire.");
								}
							} else {
								player.message("&c" + target.getName() + " is already a part of an empire.");
							}
						} else {
							player.message("&cYou can't invite yourself to an empire.");
						}
					} else {
						player.message("&cUnknown player: " + context.get(0));
					}
				} else {
					player.message("&cYou must own your empire to invite players.");
				}
			} else {
				player.message("&cYou must own an empire to invite players.");
			}
		} else {
			player.message("&cUsage: /empire invite (player)");
		}
	}
}
