package me.crypnotic.empires.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import me.crypnotic.empires.EmpiresPlugin;
import me.crypnotic.empires.api.config.BaseConfig;
import me.crypnotic.empires.api.config.ConfigType;

public class ConfigManager {

	private final EmpiresPlugin plugin;
	private final File directory;
	private final Map<ConfigType, BaseConfig> configs;

	public ConfigManager(EmpiresPlugin plugin, File directory) {
		this.plugin = plugin;
		this.directory = directory;
		this.configs = new HashMap<ConfigType, BaseConfig>();
	}

	public Collection<BaseConfig> init() {
		if (!directory.exists()) {
			directory.mkdirs();
		}

		for (ConfigType type : ConfigType.values()) {
			String name = type.getFileName();
			File file = new File(directory, name);
			if (!file.exists()) {
				try {
					file.createNewFile();

					InputStream input = plugin.getResource(name);
					if (input != null) {
						FileOutputStream output = new FileOutputStream(file);

						int count;
						byte[] buffer = new byte[1024];
						while ((count = input.read(buffer)) > 0) {
							output.write(buffer, 0, count);
						}

						input.close();
						output.close();
					}

					YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

					configs.put(type, new BaseConfig(file, configuration));
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}
		return configs.values();
	}

	public BaseConfig get(ConfigType type) {
		return configs.get(type);
	}

	public void reloadAll() {
		configs.values().forEach(BaseConfig::reload);
	}

	public void saveAll() {
		configs.values().forEach(BaseConfig::save);
	}
}
