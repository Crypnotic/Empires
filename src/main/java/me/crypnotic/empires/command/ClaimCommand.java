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

import org.bukkit.Chunk;

import me.crypnotic.empires.api.command.CommandContext;
import me.crypnotic.empires.api.command.ICommand;
import me.crypnotic.empires.api.empire.Empire;
import me.crypnotic.empires.api.player.EmpirePlayer;

public class ClaimCommand implements ICommand {

	@Override
	public void execute(EmpirePlayer player, CommandContext context) {
		if (player.getEmpire() != null) {
			Empire empire = player.getEmpire();
			if (empire.isOwner(player.getUuid())) {
				Chunk chunk = player.getChunk();
				Empire holder = getEmpireManager().getEmpireByChunk(chunk);
				if (holder == null) {
					// TODO check WorldGuard regions and other applicable
					// systems
					// TODO add some sort of restriction on territory claiming
					empire.getTerritory().add(chunk);
					empire.broadcast("&a" + player.getName() + " has claimed a new territory.");

					getEmpireManager().save(empire);
				} else {
					if (holder.equals(empire)) {
						player.message("&cYour empire has already claimed this territory.");
					} else {
						player.message("&c" + empire.getName() + " owns this territory!");
					}
				}
			} else {
				player.message("&cYou must own your empire to claim territories.");
			}
		} else {
			player.message("&cYou must own an empire to claim territories.");
		}
	}

	@Override
	public String getName() {
		return "claim";
	}

	@Override
	public String getDescription() {
		return "&eClaim more territory for your Empire";
	}
}
