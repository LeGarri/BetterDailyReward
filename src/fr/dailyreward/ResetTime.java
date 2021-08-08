package fr.dailyreward;

import java.util.Calendar;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
		
		//If have user in saved data
		if(main.getUserManager().getAllUsers() != null) {
			
			//If next day
			if(hour == 0 && minute == 1) {
				for(String s : main.getUserManager().getAllUsers()) {
					
					//If reset inactive player after one day
					if(main.getConfigManager().InactiveDayResetCondition()) {
						if(!main.getUserManager().userAlreadyClaim(UUID.fromString(s))) {
							main.getUserManager().resetUserDay(UUID.fromString(s));
						}
					}
					
					//Set user to next day
					main.getUserManager().nextDayUser(UUID.fromString(s));
				}
			}
			
			//If player(s) connected
			if(!Bukkit.getOnlinePlayers().isEmpty()) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					main.getUserManager().addPlayTimeToUser(p.getUniqueId(), 80);
				}
			}
		}
	}
	
	
	
}
