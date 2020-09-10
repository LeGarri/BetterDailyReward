package fr.dailyreward;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Commands implements CommandExecutor {
	
	private Main main;
	
	public Commands(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("dreward")) {
				if(args.length == 1 && args[0].equals("reload")) {
					if(player.hasPermission("dreward.admin")) {
						main.loadConfig();
						main.loadUser();
						main.loadLang();
						main.setMaxDay();
						player.sendMessage(Main.PREFIX + main.getLang().getString("config-reload").replaceAll("&", "§"));
					} else player.sendMessage(Main.PREFIX + main.getLang().getString("permission-deny").replaceAll("&", "§"));
				}else if (args.length == 0) {
					if(player.hasPermission("dreward.use")) {
						menu(player);
					} else player.sendMessage(Main.PREFIX + main.getLang().getString("permission-deny").replaceAll("&", "§"));
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	private void menu(Player player) {
		int invSize = 9;
		Inventory inv = Bukkit.createInventory(null, invSize, main.getLang().getString("reward-menu-title").replaceAll("&", "§"));
		
		int count = 0;
		int currentDay = main.getUser().getInt("players." + player.getUniqueId() + ".day");
		int maxDay = currentDay + 8;
		
		for(String s : main.getConfig().getConfigurationSection("reward").getKeys(false)) {
			if(main.getConfig().getInt("reward." + s + ".day") >= currentDay && main.getConfig().getInt("reward." + s + ".day") <= maxDay) {
				ItemStack item = new ItemStack(Material.getMaterial(main.getConfig().getString("reward." + s + ".item.material")), main.getConfig().getInt("reward." + s + ".item.amount"));
				ItemMeta itemMeta = item.getItemMeta();
				itemMeta.setDisplayName("§f" + main.getConfig().getString("reward." + s + ".item.name").replaceAll("&", "§"));
				if(main.getConfig().contains("reward." + s + ".item.lore")) {
					List<String> lore = main.getConfig().getStringList("reward." + s + ".item.lore");
					List<String> coloredLore = new ArrayList<String>();
					for(String l : lore) {
						coloredLore.add(ChatColor.translateAlternateColorCodes('&', l));
					}
					itemMeta.setLore(coloredLore);
				}
				if(main.getConfig().contains("reward." + s + ".item.customModelData")) {
					itemMeta.setCustomModelData(main.getConfig().getInt("reward." + s + ".item.customModelData"));
				}
				item.setItemMeta(itemMeta);
				
				inv.setItem(count, item);
				
				count++;
			}
		}
		
		player.openInventory(inv);
	}
	
}
