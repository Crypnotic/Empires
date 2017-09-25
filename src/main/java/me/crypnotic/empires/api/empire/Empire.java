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
import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import me.crypnotic.empires.api.player.EmpirePlayer;

public class Empire {

	@Getter
	private final String id;
	@Getter
	private final String name;
	@Getter
	private final UUID owner;
	@Getter
	private final Territory territory;
	@Getter
	private final Set<UUID> members;
	@Getter
	private Set<EmpirePlayer> online;
	@Getter
	private Set<EmpirePlayer> invites;

	public Empire(String id, String name, UUID owner, Territory territory, Set<UUID> members) {
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.territory = territory;
		this.members = members;
		this.online = new HashSet<EmpirePlayer>();
		this.invites = new HashSet<EmpirePlayer>();
	}

	public boolean isOwner(UUID uuid) {
		return owner.equals(uuid);
	}

	public boolean isMember(UUID uuid) {
		return isOwner(uuid) || members.contains(uuid);
	}

	public void broadcast(String message) {
		online.forEach(player -> player.message(message));
	}

	public void addMember(UUID uuid) {
		members.add(uuid);
	}

	public void addOnline(EmpirePlayer player) {
		online.add(player);
	}

	public void addInvite(EmpirePlayer player) {
		invites.add(player);
	}

	public void removeMember(UUID uuid) {
		members.remove(uuid);
	}

	public void removeOnline(EmpirePlayer player) {
		online.remove(player);
	}

	public void removeInvite(EmpirePlayer player) {
		invites.remove(player);
	}
}
