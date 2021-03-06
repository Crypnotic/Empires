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
package me.crypnotic.empires.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import me.crypnotic.empires.api.config.Config;
import me.crypnotic.empires.api.config.ConfigType;

public class ConfigManager {

	private final File directory;
	private final Map<ConfigType, Config> configs;

	public ConfigManager(File directory) {
		this.directory = directory;
		this.configs = new HashMap<ConfigType, Config>();
	}

	public Collection<ConfigType> init() {
		if (!directory.exists()) {
			directory.mkdirs();
		}

		for (ConfigType type : ConfigType.values()) {
			String name = type.getFileName();
			File file = new File(directory, name);
			if (!file.exists()) {
				try {
					file.createNewFile();

					InputStream input = getClass().getResourceAsStream(name);
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
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
			try {
				configs.put(type, new Config(file, YamlConfiguration.loadConfiguration(file)));
			} catch (IllegalArgumentException exception) {
				exception.printStackTrace();
			}
		}
		return configs.keySet();
	}

	public Config get(ConfigType type) {
		return configs.get(type);
	}

	public void reloadAll() {
		configs.values().forEach(Config::reload);
	}

	public void saveAll() {
		configs.values().forEach(Config::save);
	}
}
