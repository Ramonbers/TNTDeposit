package me.OldSkoolDad.tnt;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.OldSkoolDad.tnt.Commands.TNTCommand;
import me.OldSkoolDad.tnt.Commands.TNTHandler;

public class Main extends JavaPlugin {
	
	public Plugin pl;
	
	public static File configf;
	public static FileConfiguration config;
	public static File factionDataf;
	public static FileConfiguration factionData;
	
	@Override
	public void onEnable() {
		pl = this;
		
		Logger logger = getLogger();
		
		logger.info("Plugin is starting..");
		
		// REGISTER ALL COMMANDS
		getCommand("tnt").setExecutor(new TNTCommand());
		
		// REGISTER ALL EVENTS
		// Bukkit.getPluginManager().registerEvents(new ServerJoin(), this);
		
		// SETUP CONFIG
		createFiles();
		
		// GET ALL FACTIONS FROM CONFIG
		logger.info("Getting all factions data...");
		for (String faction : factionData.getConfigurationSection("factions").getKeys(false)) {
			int factionTNT = factionData.getInt("factions." + faction + ".tntAmount");
			TNTHandler.FactionTNTList.put(faction, factionTNT);
		}
		logger.info("Faction data has been loaded!");
		
		Bukkit.getScheduler().runTaskTimer(pl, new Runnable() {
			public void run() {
				updateConfig();
			}
		}, 0L, 1200L);
		
		logger.info("Plugin has been enabled!");
	}
	
	@Override
	public void onDisable() {
		Logger logger = getLogger();
		logger.info("Plugin is disabling..");
		// THINGS BEFORE PLUGIN STOPS
		
		logger.info("Plugin has been disabled.");
	}
	
	private void createFiles() {
		
		configf = new File(getDataFolder(), "config.yml");
		factionDataf = new File(getDataFolder(), "factionData.yml");
		Logger logger = getLogger();
		
		if (!configf.exists()) {
			logger.info("Creating 'Config.yml'..");
			configf.getParentFile().mkdirs();
			saveResource("config.yml", false);
			logger.info("Created 'Config.yml'");
		}
		
		if (!factionDataf.exists()) {
			logger.info("Creating 'FactionData.yml'..");
			factionDataf.getParentFile().mkdirs();
			saveResource("factionData.yml", false);
			logger.info("Created 'FactionData.yml'");
		}
		
		config = new YamlConfiguration();
		factionData = new YamlConfiguration();
		try {
			config.load(configf);
			factionData.load(factionDataf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateConfig() {
		Logger logger = getLogger();
		logger.info("Running TNT config update...");
		for (Map.Entry<String, Integer> entry : TNTHandler.FactionTNTUpdateList.entrySet()) {
			String factionId = entry.getKey();
			int tnt = entry.getValue();
			factionData.set("factions." + factionId + ".tntAmount", tnt);
			try {
				factionData.save(factionDataf);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		TNTHandler.FactionTNTUpdateList.clear();
		logger.info("TNT config update finished.");
	}
}
