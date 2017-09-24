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
package me.crypnotic.empires.api.player;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.crypnotic.empires.api.Strings;
import me.crypnotic.empires.api.empire.Empire;

@RequiredArgsConstructor
public class EmpirePlayer {

	@Getter
	private final UUID uuid;
	@Getter
	@Setter
	private Empire empire;
	private Long lastWarning;

	public Player getHandle() {
		return Bukkit.getServer().getPlayer(uuid);
	}

	public String getName() {
		return getHandle().getName();
	}

	public Location getLocation() {
		return getHandle().getLocation();
	}

	public Chunk getChunk() {
		return getLocation().getChunk();
	}

	public void message(String message) {
		getHandle().sendMessage(Strings.color(message));
	}

	public void warn(String message) {
		long current = System.currentTimeMillis();
		if (current - lastWarning >= 2000) {
			message(message);
			this.lastWarning = current;
		}
	}
}
