package fr.dailyreward.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.vexsoftware.votifier.model.VotifierEvent;

import fr.dailyreward.Main;

public class VoteListener implements Listener {

	private Main main;
	
	
	
	public VoteListener(Main main) {
		this.main = main;
	}
	
	
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.NORMAL)
	public void voteListener(VotifierEvent e) {
		//If vote enabled
		if(main.hasVote()) {
			
			//Create user if not exist
			if(!main.getUserManager().userExist(Bukkit.getOfflinePlayer(e.getVote().getUsername()).getUniqueId())) {
				main.getUserManager().createUser(Bukkit.getOfflinePlayer(e.getVote().getUsername()).getUniqueId());
			}
			
			//Add a vote to the user
			main.getUserManager().addVoteToUser(Bukkit.getOfflinePlayer(e.getVote().getUsername()).getUniqueId());
		}
	}
	
	
	
}
