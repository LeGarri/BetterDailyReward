package fr.dailyreward;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Listeners implements Listener {
	
	private Main main;
	
	public Listeners(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onInteractInventory(InventoryClickEvent e) {
		if(e.getCurrentItem() != null) {
			Player player = (Player) e.getWhoClicked();
			
			//Interaction Reward Menu
			if(e.getView().getTitle().equals(main.getLangManager().getString("reward-menu-title"))) {
				e.setCancelled(true);
				
				if(e.getSlot() == 0) {
					//Create user if not exist
					if(!main.getUserManager().userExist(player.getUniqueId())) {
						main.getUserManager().createUser(player.getUniqueId());
					}
					
					int userDay = main.getUserManager().getUserDay(player.getUniqueId());
					
					
					
					//If reward already claimed
					if(main.getUserManager().userAlreadyClaim(player.getUniqueId())) {
						player.closeInventory();
						
						player.sendMessage(Main.PREFIX + main.getLangManager().getString("reward-already-claimed"));
						return;
					}
					
					
					
					//If day have condition(s)
					if(main.getConfigManager().dayHaveCondition(main.getUserManager().getUserDay(player.getUniqueId()))) {

						//If day have vote condition && Votifier enabled
						if(main.getConfigManager().dayHaveVoteCondition(userDay) && main.hasVote()) {
							
							//Check user vote
							if(main.getUserManager().getUserVote(player.getUniqueId()) < main.getConfigManager().getDayVoteCondition(userDay)) {
								player.closeInventory();
								
								player.sendMessage(Main.PREFIX + main.getLangManager().getString("vote-required").replaceAll("%vote%", Integer.toString(main.getConfigManager().getDayVoteCondition(userDay))));
								return;
							}
						}
						
						//If day have play time condition
						if(main.getConfigManager().dayHavePlayTimeCondition(userDay)) {
							
							//Check user play time
							if(main.getUserManager().getUserPlaytime(player.getUniqueId()) < main.getConfigManager().getDayPlayTimeCondition(userDay)) {
								player.closeInventory();
								
								player.sendMessage(Main.PREFIX + main.getLangManager().getString("time-required").replaceAll("%time%", Integer.toString(main.getConfigManager().getDayPlayTimeCondition(userDay))));
								return;
							}
						}
						
						//Send the reward
						sendRewardToPlayer(player);
					}
				}
			}
		}
	}
	
	
	private void sendRewardToPlayer(Player player) {
		//Execute all reward commands for the day
		for(String s : main.getConfigManager().getDayCommandsReward(main.getUserManager().getUserDay(player.getUniqueId()))) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replaceAll("%player%", player.getName()));
		}

		
		
		player.closeInventory();
		
		player.sendMessage(Main.PREFIX + main.getLangManager().getString("claim-reward"));
		
		
		
		//Set reward as claimed
		main.getUserManager().userClaimedReward(player.getUniqueId());
	}
	
	

}



















