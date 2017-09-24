package me.crypnotic.empires.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Chunk;

import me.crypnotic.empires.api.empire.Empire;

public class EmpireManager {

	private final Map<String, Empire> empires;

	public EmpireManager() {
		this.empires = new HashMap<String, Empire>();
	}

	public boolean init() {

		return true;
	}

	public Empire getEmpireByChunk(Chunk chunk) {
		for (Empire empire : empires.values()) {
			if (empire.getTerritory().contains(chunk)) {
				return empire;
			}
		}
		return null;
	}
}
