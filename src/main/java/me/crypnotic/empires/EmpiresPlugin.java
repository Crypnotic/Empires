package me.crypnotic.empires;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import me.crypnotic.empires.api.config.ConfigType;
import me.crypnotic.empires.manager.ConfigManager;
import me.crypnotic.empires.manager.EmpireManager;

public class EmpiresPlugin extends JavaPlugin {

	@Getter
	private ConfigManager configManager;
	@Getter
	private EmpireManager empireManager;

	@Override
	public void onLoad() {
		this.configManager = new ConfigManager(this, getDataFolder());
		this.empireManager = new EmpireManager();
	}

	@Override
	public void onEnable() {
		if (configManager.init().size() != ConfigType.values().length) {
			// TODO Halt plugin init and log issue
		}
		if (!empireManager.init()) {
			// TODO Halt plugin init and log issue
		}
	}

	@Override
	public void onDisable() {

	}
}
