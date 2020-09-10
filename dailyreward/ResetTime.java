package fr.dailyreward;

import java.util.Calendar;

import org.bukkit.scheduler.BukkitRunnable;

public class ResetTime extends BukkitRunnable {

	private Main main;
	
	public ResetTime(Main main) {
		this.main = main;
	}
	
	@Override
	public void run() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		
		if(hour == 0 && minute == 1) {
			for(String s : main.getUser().getConfigurationSection("players").getKeys(false)) {
				if(main.getConfig().getBoolean("reset-day")) {
					if(!main.getUser().getBoolean("players." + s + ".get")) main.getUser().set("players." + s + ".day", 1);
				}
				main.getUser().set("players." + s + ".get", false);
				main.getUser().set("players." + s + ".vote", 0);
				main.getUser().set("players." + s + ".time", 0);
			}
			
			main.saveUser();
		}
		
		if(main.getUser().getConfigurationSection("players") != null) {
			for(String s : main.getUser().getConfigurationSection("players").getKeys(false)) {
				main.getUser().set("players." + s + ".time", main.getUser().getInt("players." + s + ".time") + 80);
			}
			
			main.saveUser();
		}
	}
	
}
