package fr.dailyreward;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static String PREFIX = "§6[§eBDailyReward§6] §f";
	
	private File configFile, userFile, langFile;
	private FileConfiguration configConfig, userConfig, langConfig;
	
	private int maxDay;
	
	private boolean useVote = false;
	
	@Override
	public void onEnable() {
		if(Bukkit.getPluginManager().isPluginEnabled("Votifier")) {
			useVote = true;
		}
		
		loadConfig();
		loadUser();
		loadLang();
		
		getCommand("dreward").setExecutor(new Commands(this));
		
		getServer().getPluginManager().registerEvents(new Listeners(this), this);
		
		setMaxDay();
		
		ResetTime rt = new ResetTime(this);
		rt.runTaskTimer(this, 0, 1600);
		
		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(this, 7610);
	}
	
	public int getMaxDay() { return maxDay; }
	
	public void setMaxDay() {
		maxDay = getConfig().getConfigurationSection("reward").getKeys(false).size();
	}
	
	public boolean hasVote() { return this.useVote; }
	
	
	
	
	
	
	
	
	
	
	
	
	public void loadConfig() {
		configFile = new File(getDataFolder(), "config.yml");
		if(!configFile.exists()) {
			saveResource("config.yml", false);
		}
		
		configConfig = new YamlConfiguration();
		try {
			configConfig.load(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getConfig() {
		return this.configConfig;
	}
	
	public File getConfigFile() {
		return this.configFile;
	}
	
	public void saveConfig() {
		try {
			getConfig().save(getConfigFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void loadUser() {
		userFile = new File(getDataFolder(), "user.yml");
		if(!userFile.exists()) {
			saveResource("user.yml", false);
		}
		
		userConfig = new YamlConfiguration();
		try {
			userConfig.load(userFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getUser() {
		return this.userConfig;
	}
	
	public File getUserFile() {
		return this.userFile;
	}
	
	public void saveUser() {
		try {
			getUser().save(getUserFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void loadLang() {
		File dir = new File(getDataFolder() + File.separator + "lang");
		
		if(!dir.exists()) {
			dir.mkdir();
		}
		
		langFile = new File(getDataFolder() + File.separator + "lang", getConfig().getString("lang") + ".yml");
		if(!langFile.exists()) {
			saveResource("lang" + File.separator + "en.yml", false);
		}
		
		langConfig = new YamlConfiguration();
		try {
			langConfig.load(langFile);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getLang() {
		return this.langConfig;
	}
	
	public File getLangFile() {
		return this.langFile;
	}
	
	public void saveLang() {
		try {
			getLang().save(getLangFile());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}



































