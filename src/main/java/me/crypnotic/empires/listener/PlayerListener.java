package me.crypnotic.empires.listener;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import lombok.RequiredArgsConstructor;
import me.crypnotic.empires.EmpiresPlugin;
import me.crypnotic.empires.api.empire.Empire;

@RequiredArgsConstructor
public class PlayerListener implements Listener {

	private final EmpiresPlugin plugin;

	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Chunk chunk = player.getLocation().getChunk();
		Empire empire = plugin.getEmpireManager().getEmpireByChunk(chunk);
		if (empire == null) {
			return;
		}

	}

	@EventHandler(ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Chunk chunk = event.getBlock().getChunk();
		Empire empire = plugin.getEmpireManager().getEmpireByChunk(chunk);
		if (empire == null) {
			return;
		}

	}

	@EventHandler(ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Chunk chunk = event.getBlock().getChunk();
		Empire empire = plugin.getEmpireManager().getEmpireByChunk(chunk);
		if (empire == null) {
			return;
		}

	}
}
