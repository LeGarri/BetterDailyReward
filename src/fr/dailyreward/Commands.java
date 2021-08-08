package fr.dailyreward;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	
	private Main main;
	
	public Commands(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(cmd.getName().equalsIgnoreCase("dreward")) {
			//Reload the plugin
			if(args.length == 1 && args[0].equals("reload")) {
				if(sender.hasPermission("dreward.admin")) {
					reloadPlugin();
					
					sender.sendMessage(Main.PREFIX + main.getLangManager().getString("config-reload"));
				} else sender.sendMessage(Main.PREFIX + main.getLangManager().getString("permission-deny"));
			} else { //Open the menu
				if(sender instanceof Player) {
					Player player = (Player) sender;
					
					if(player.hasPermission("dreward.use")) {
						Menu.getMenu(main, player);
					} else player.sendMessage(Main.PREFIX + main.getLangManager().getString("permission-deny"));
				}
			}
			
			return true;
		}

		return false;
	}
	
	
	
	private void reloadPlugin() {
		main.getConfigManager().loadConfig();
		main.getUserManager().loadUser();
		main.getLangManager().loadLang();
	}
	
	
	
}
