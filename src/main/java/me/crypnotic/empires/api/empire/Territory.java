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
package me.crypnotic.empires.api.empire;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Territory {

	@Getter
	private final Set<Chunk> chunks;

	public boolean add(Chunk chunk) {
		return chunks.add(chunk);
	}

	public boolean remove(Chunk chunk) {
		return chunks.remove(chunk);
	}

	public boolean contains(Chunk chunk) {
		return chunks.contains(chunk);
	}

	public boolean isInside(Player player) {
		return contains(player.getLocation().getChunk());
	}

	public Stream<Chunk> stream() {
		return chunks.stream();
	}

	public int size() {
		return chunks.size();
	}

	public boolean isEmpty() {
		return chunks.isEmpty();
	}

	public String serialize() {
		StringBuilder builder = new StringBuilder();

		Iterator<Chunk> iterator = chunks.iterator();
		while (iterator.hasNext()) {
			Chunk chunk = iterator.next();
			builder.append(chunk.getWorld().getName() + "_" + chunk.getX() + "_" + chunk.getZ());
			if (iterator.hasNext()) {
				builder.append(";");
			}
		}
		return builder.toString();
	}

	public static Territory deserialize(String text) {
		Set<Chunk> chunks = new HashSet<Chunk>();

		String[] stack = text.split(";");
		for (String element : stack) {
			String[] data = element.split("_");
			if (data == null || data.length == 0) {
				continue;
			}

			World world = Bukkit.getServer().getWorld(data[0]);
			if (world == null) {
				continue;
			}

			chunks.add(world.getChunkAt(Integer.valueOf(data[1]), Integer.valueOf(data[2])));
		}
		return new Territory(chunks);
	}
}
