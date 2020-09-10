package fr.dailyreward;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.vexsoftware.votifier.model.VotifierEvent;

public class Listeners implements Listener {
	
	private Main main;
	
	public Listeners(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onInteractInventory(InventoryClickEvent e) {
		if(e.getCurrentItem() != null) {
			Player player = (Player)e.getWhoClicked();
			
			if(e.getView().getTitle().equals(main.getLang().getString("reward-menu-title").replaceAll("&", "§"))) {
				e.setCancelled(true);
				if(e.getSlot() == 0) {
					if(!main.getUser().contains("players." + player.getUniqueId().toString())) {
						main.getUser().set("players." + player.getUniqueId().toString() + ".day", 1);
						main.getUser().set("players." + player.getUniqueId().toString() + ".get", false);
						main.getUser().set("players." + player.getUniqueId().toString() + ".vote", 0);
						main.getUser().set("players." + player.getUniqueId().toString() + ".time", 0);
						main.saveUser();
					}
					
					if(main.getConfig().contains("reward.day" + Integer.toString(main.getUser().getInt("players." + player.getUniqueId() + ".day")) + ".condition")) {
						if(main.getConfig().contains("reward.day" + Integer.toString(main.getUser().getInt("players." + player.getUniqueId() + ".day")) + ".condition.vote")) {
							if(main.getUser().getInt("players." + player.getUniqueId() + ".vote") < main.getConfig().getInt("reward.day" + Integer.toString(main.getUser().getInt("players." + player.getUniqueId() + ".day")) + ".condition.vote")) {
								player.closeInventory();
								player.sendMessage(Main.PREFIX + main.getLang().getString("vote-required").replaceAll("&", "§").replaceAll("%vote%", Integer.toString(main.getConfig().getInt("reward.day" + Integer.toString(main.getUser().getInt("players." + player.getUniqueId() + ".day")) + ".condition.vote"))));
								return;
							}
						}
						
						if(main.getConfig().contains("reward.day" + Integer.toString(main.getUser().getInt("players." + player.getUniqueId() + ".day")) + ".condition.playTime")) {
							if(main.getUser().getInt("players." + player.getUniqueId() + ".time") < main.getConfig().getInt("reward.day" + Integer.toString(main.getUser().getInt("players." + player.getUniqueId() + ".day")) + ".condition.playTime")) {
								player.closeInventory();
								player.sendMessage(Main.PREFIX + main.getLang().getString("time-required").replaceAll("&", "§").replaceAll("%time%", Integer.toString(main.getConfig().getInt("reward.day" + Integer.toString(main.getUser().getInt("players." + player.getUniqueId() + ".day")) + ".condition.playTime"))));
								return;
							}
						}
					}
					
					if(main.getUser().getBoolean("players." + player.getUniqueId().toString() + ".get")) {
						player.closeInventory();
						player.sendMessage(Main.PREFIX + main.getLang().getString("reward-already-claimed").replaceAll("&", "§"));
						return;
					}
					
					for(String s : main.getConfig().getStringList("reward.day" + Integer.toString(main.getUser().getInt("players." + player.getUniqueId() + ".day")) + ".commands")) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replaceAll("%player%", player.getName()));
					}
					
					player.closeInventory();
					
					player.sendMessage(Main.PREFIX + main.getLang().getString("claim-reward").replaceAll("&", "§"));
					
					if(main.getUser().getInt("players." + player.getUniqueId() + ".day") == main.getMaxDay()) main.getUser().set("players." + player.getUniqueId() + ".day", 1);
					else main.getUser().set("players." + player.getUniqueId() + ".day", main.getUser().getInt("players." + player.getUniqueId() + ".day") + 1);
					main.getUser().set("players." + player.getUniqueId() + ".get", true);
					main.saveUser();
				}
			}
		}
	}
	
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void voteListener(VotifierEvent e) {
		if(main.hasVote()) {
			if(Bukkit.getPlayer(e.getVote().getUsername()) != null) {
				if(Bukkit.getPlayer(e.getVote().getUsername()).getUniqueId().toString() != null && main.getUser().contains("players." + Bukkit.getPlayer(e.getVote().getUsername()).getUniqueId().toString())) {
					main.getUser().set("players." + Bukkit.getPlayer(e.getVote().getUsername()).getUniqueId().toString() + ".vote", main.getUser().getInt("players." + Bukkit.getPlayer(e.getVote().getUsername()).getUniqueId().toString() + ".vote") + 1);
					main.saveUser();
				}
			}
		}
	}
	
	
}
