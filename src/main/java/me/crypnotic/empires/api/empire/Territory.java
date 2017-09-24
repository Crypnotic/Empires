package me.crypnotic.empires.api.empire;

import java.util.Set;

import org.bukkit.Chunk;
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
}
