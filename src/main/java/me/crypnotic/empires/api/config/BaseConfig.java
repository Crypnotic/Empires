package me.crypnotic.empires.api.config;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BaseConfig {

	@Getter
	private final File file;
	@Getter
	private YamlConfiguration config;

	public String getString(String path) {
		return config.getString(path);
	}

	public List<String> getStringList(String path) {
		return config.getStringList(path);
	}

	public Double getDouble(String path) {
		return config.getDouble(path);
	}

	public UUID getUUID(String path) {
		return null;
	}

	public List<UUID> getUUIDList(String path) {
		return null;
	}

	public boolean save() {
		try {
			config.save(file);
			return true;
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	public boolean reload() {
		try {
			this.config = YamlConfiguration.loadConfiguration(file);
			return true;
		} catch (IllegalArgumentException exception) {
			exception.printStackTrace();
		}
		return false;
	}
}
