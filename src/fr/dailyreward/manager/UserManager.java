package fr.dailyreward.manager;

import java.io.File;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.dailyreward.Main;

public class UserManager {

	private Main main;
	
	private File userFile;
	private FileConfiguration user;
	
	
	
	public UserManager(Main main) {
		this.main = main;
		
		loadUser();
	}
	
	
	
	
	
	public boolean userExist(UUID uuid) {
		return user.contains("players." + uuid.toString());
	}
	
	public boolean userAlreadyClaim(UUID uuid) {
		return user.contains("players." + uuid.toString() + ".get");
	}
	
	
	
	
	
	public int getUserDay(UUID uuid) {
		return user.getInt("players." + uuid.toString() + ".day");
	}
	
	public int getUserVote(UUID uuid) {
		return user.getInt("players." + uuid.toString() + ".vote");
	}
	
	public int getUserPlaytime(UUID uuid) {
		return user.getInt("players." + uuid.toString() + ".time");
	}
	
	
	
	public Set<String> getAllUsers() {
		if(user.contains("players")) {
			return user.getConfigurationSection("players").getKeys(false);
		}
		
		return null;
	}
	
	
	
	
	
	public void createUser(UUID uuid) {
		user.set("players." + uuid.toString() + ".day", 1);
		user.set("players." + uuid.toString() + ".get", false);
		user.set("players." + uuid.toString() + ".vote", 0);
		user.set("players." + uuid.toString() + ".time", 0);
		
		saveUser();
	}
	
	public void resetUserDay(UUID uuid) {
		user.set("players." + uuid.toString() + ".day", 1);
		
		saveUser();
	}
	
	public void userClaimedReward(UUID uuid) {
		//If user reached max day
		if(main.getUserManager().getUserDay(uuid) >= main.getConfigManager().getMaxDay()) {
			resetUserDay(uuid);
		}
		else {
			user.set("players." + uuid.toString() + ".day", main.getUserManager().getUserDay(uuid) + 1);
		}
		
		//Set reward claimed
		user.set("players." + uuid.toString() + ".get", true);
		
		saveUser();
	}
	
	public void addVoteToUser(UUID uuid) {
		user.set("players." + uuid.toString() + ".vote", getUserVote(uuid) + 1);
		
		saveUser();
	}
	
	public void addPlayTimeToUser(UUID uuid, int time) {
		user.set("players." + uuid.toString() + ".time", getUserPlaytime(uuid) + time);
		
		saveUser();
	}
	
	public void nextDayUser(UUID uuid) {
		user.set("players." + uuid.toString() + ".get", false);
		user.set("players." + uuid.toString() + ".vote", 0);
		user.set("players." + uuid.toString() + ".time", 0);
		
		saveUser();
	}
	
	
	
	

	
	
	public void loadUser() {
		userFile = new File(main.getDataFolder(), "user.yml");
		
		if(!userFile.exists()) {
			main.saveResource("user.yml", false);
		}
		
		
		user = new YamlConfiguration();
		
		try {
			user.load(userFile);
			
			System.out.println(Main.CONSOLE_PREFIX + "§aUser loaded !");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getUser() {
		return this.user;
	}
	
	public void saveUser() {
		try {
			getUser().save(userFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
