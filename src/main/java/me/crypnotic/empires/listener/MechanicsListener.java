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
package me.crypnotic.empires.listener;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import lombok.RequiredArgsConstructor;
import me.crypnotic.empires.api.empire.Empire;
import me.crypnotic.empires.api.player.EmpirePlayer;
import me.crypnotic.empires.manager.EmpireManager;
import me.crypnotic.empires.manager.PlayerManager;

@RequiredArgsConstructor
public class MechanicsListener implements Listener {

	private final EmpireManager empireManager;
	private final PlayerManager playerManager;

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		EmpirePlayer player = playerManager.load(event.getPlayer().getUniqueId(), true);
		if (player.getEmpire() == null) {
			return;
		}

		Empire empire = player.getEmpire();

		empire.addOnline(player);
		empire.broadcast("&a" + player.getName() + " &elogged on.");
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		EmpirePlayer player = playerManager.get(event.getPlayer().getUniqueId());
		if (player.getEmpire() == null) {
			return;
		}

		Empire empire = player.getEmpire();

		empire.removeOnline(player);
		empire.broadcast("&a" + player.getName() + " &elogged off.");
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Action action = event.getAction();
		if (action == Action.RIGHT_CLICK_AIR || action == Action.LEFT_CLICK_AIR) {
			return;
		}

		EmpirePlayer player = playerManager.get(event.getPlayer().getUniqueId());
		Chunk chunk = player.getChunk();
		Empire empire = empireManager.getEmpireByChunk(chunk);
		if (empire == null) {
			return;
		}

		if (empire.isMember(player.getUuid())) {
			return;
		}

		event.setCancelled(true);
		player.warn("&c&l" + empire.getName() + " owns this territory!");
	}

	@EventHandler(ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		EmpirePlayer player = playerManager.get(event.getPlayer().getUniqueId());
		Chunk chunk = event.getBlock().getChunk();
		Empire empire = empireManager.getEmpireByChunk(chunk);
		if (empire == null) {
			return;
		}

		if (empire.isMember(player.getUuid())) {
			return;
		}

		event.setCancelled(true);
		player.warn("&c&l" + empire.getName() + " owns this territory!");
	}

	@EventHandler(ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		EmpirePlayer player = playerManager.get(event.getPlayer().getUniqueId());
		Chunk chunk = event.getBlock().getChunk();
		Empire empire = empireManager.getEmpireByChunk(chunk);
		if (empire == null) {
			return;
		}

		if (empire.isMember(player.getUuid())) {
			return;
		}

		event.setCancelled(true);
		player.warn("&c&l" + empire.getName() + " owns this territory!");
	}
}
