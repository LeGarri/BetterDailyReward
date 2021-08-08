package fr.dailyreward.manager;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.dailyreward.Main;

public class ConfigManager {

	private Main main;
	
	private File configFile;
	private FileConfiguration config;
	
	
	
	public ConfigManager(Main main) {
		this.main = main;
		
		loadConfig();
	}

	

	public String getLang() {
		return config.getString("lang");
	}
	
	public List<String> getDayCommandsReward(int day) {
		return config.getStringList("reward.day" + Integer.toString(day) + ".commands");
	}
	
	public int getMaxDay() {
		return config.getConfigurationSection("reward").getKeys(false).size();
	}
	
	
	
	
	
	//////////////////////////
	//						//
	//		CONDITIONS		//
	//						//
	//////////////////////////
	
	public boolean dayHaveCondition(int day) {
		return config.contains("reward.day" + Integer.toString(day) + ".condition");
	}
	
	public boolean dayHaveVoteCondition(int day) {
		return config.contains("reward.day" + Integer.toString(day) + ".condition.vote");
	}
	
	public int getDayVoteCondition(int day) {
		return config.getInt("reward.day" + Integer.toString(day) + ".condition.vote");
	}
	
	public boolean dayHavePlayTimeCondition(int day) {
		return config.contains("reward.day" + Integer.toString(day) + ".condition.playTime");
	}
	
	public int getDayPlayTimeCondition(int day) {
		return config.getInt("reward.day" + Integer.toString(day) + ".condition.playTime");
	}
	
	public boolean InactiveDayResetCondition() {
		return config.getBoolean("reset-day");
	}
	
	
	
	
	
	
	
	public void loadConfig() {
		configFile = new File(main.getDataFolder(), "config.yml");
		
		if(!configFile.exists()) {
			main.saveResource("config.yml", false);
		}
		
		
		config = new YamlConfiguration();
		
		try {
			config.load(configFile);
			
			System.out.println(Main.CONSOLE_PREFIX + "§aConfig loaded !");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getConfig() {
		return this.config;
	}
	
	public void saveConfig() {
		try {
			getConfig().save(this.configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
