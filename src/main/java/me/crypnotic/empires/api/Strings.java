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
package me.crypnotic.empires.api;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import net.md_5.bungee.api.ChatColor;

public class Strings {

	private static final Pattern UUID_PATTERN = Pattern.compile("^(\\w{8})-?(\\w{4})-?(\\w{4})-?(\\w{4})-?(\\w{12})$");

	public static String color(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	public static boolean isUUID(String text) {
		return UUID_PATTERN.matcher(text).matches();
	}

	public static UUID parseUUID(String text) {
		text = UUID_PATTERN.matcher(text).replaceFirst("$1-$2-$3-$4-$5");
		return UUID.fromString(text);
	}

	public static Set<UUID> parseUUIDSet(String text) {
		Set<UUID> result = new HashSet<UUID>();
		if (text == null || text.isEmpty()) {
			return result;
		}
		String[] elements = text.split(";");
		for (String element : elements) {
			result.add(parseUUID(element));
		}
		return result;
	}

	public static String serializeUUID(UUID uuid) {
		return uuid.toString().replace("-", "");
	}

	public static String serializeUUIDSet(Set<UUID> set) {
		if (set.isEmpty()) {
			return "";
		}
		return set.stream().map(Strings::serializeUUID).collect(Collectors.joining(";"));
	}

	public static String trim(String text, int length) {
		return text.substring(0, length);
	}
}
