package me.crypnotic.empires.api.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ConfigType {
	GLOBAL("config.yml"), PLAYERS("players.yml"), EMPIRES("empires.yml");

	@Getter
	private String fileName;
}
