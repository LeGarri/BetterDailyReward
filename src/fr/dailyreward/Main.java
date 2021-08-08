package fr.dailyreward;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.dailyreward.manager.ConfigManager;
import fr.dailyreward.manager.LangManager;
import fr.dailyreward.manager.UserManager;

public class Main extends JavaPlugin {
	
	public static final String CONSOLE_PREFIX = "§7[§6BDR§7] §f";
	public static final String PREFIX = "§6[§eBDailyReward§6] §f";
	
	
	private ConfigManager configManager;
	private UserManager userManager;
	private LangManager langManager;
	
	
	private boolean useVote = false;
	
	
	
	
	
	@Override
	public void onEnable() {
		//Enable vote condition
		if(Bukkit.getPluginManager().isPluginEnabled("Votifier")) {
			this.useVote = true;
		}
		
		
		//Initialize Manager
		this.configManager = new ConfigManager(this);
		this.userManager = new UserManager(this);
		this.langManager = new LangManager(this);
		
		
		//Initialize Command
		getCommand("dreward").setExecutor(new Commands(this));
		
		
		//Initialize Listener
		getServer().getPluginManager().registerEvents(new Listeners(this), this);
		if(hasVote()) getServer().getPluginManager().registerEvents(new Listeners(this), this);
		
		
		//Initialize Time while for Play time and next day
		ResetTime rt = new ResetTime(this);
		rt.runTaskTimer(this, 0, 1600);
		
		
		//Initialize Metrics
		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(this, 7610);
	}
	
	
	
	
	
	public ConfigManager getConfigManager() {
		return this.configManager;
	}
	
	public UserManager getUserManager() {
		return this.userManager;
	}
	
	public LangManager getLangManager() {
		return this.langManager;
	}
	
	
	
	
	
	public boolean hasVote() { return this.useVote; }
	
	
	
}



































