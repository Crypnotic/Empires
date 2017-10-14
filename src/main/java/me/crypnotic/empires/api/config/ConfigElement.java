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
package me.crypnotic.empires.api.config;

import java.util.Set;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import me.crypnotic.empires.api.Strings;

@RequiredArgsConstructor
public class ConfigElement {

	private final Object value;

	public Object raw() {
		return value;
	}

	public Integer asInteger() {
		return !exists() ? null : (Integer) value;
	}

	public Double asDouble() {
		return !exists() ? null : (Double) value;
	}

	public Long asLong() {
		return !exists() ? null : (Long) value;
	}

	public String asString() {
		return !exists() ? null : value.toString();
	}

	public UUID asUUID() {
		return !exists() ? null : Strings.parseUUID(asString());
	}

	public Set<UUID> asUUIDSet() {
		return !exists() ? null : Strings.parseUUIDSet(asString());
	}

	public boolean exists() {
		return value != null;
	}
}
