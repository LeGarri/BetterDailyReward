package fr.dailyreward;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Menu {
	
	public static void getMenu(Main main, Player player) {
		int invSize = 9;
		Inventory inv = Bukkit.createInventory(null, invSize, main.getLangManager().getString("reward-menu-title"));
		
		int count = 0;
		int currentDay = main.getUserManager().getUserDay(player.getUniqueId());
		int maxDay = currentDay + 8;
		
		for(String s : main.getConfig().getConfigurationSection("reward").getKeys(false)) {
			if(main.getConfig().getInt("reward." + s + ".day") >= currentDay && main.getConfig().getInt("reward." + s + ".day") <= maxDay) {
				//Create the item from the config
				ItemStack item = new ItemStack(Material.getMaterial(main.getConfig().getString("reward." + s + ".item.material")), main.getConfig().getInt("reward." + s + ".item.amount"));
				ItemMeta itemMeta = item.getItemMeta();
				
				//Set display name
				itemMeta.setDisplayName("§f" + main.getConfig().getString("reward." + s + ".item.name").replaceAll("&", "§"));
				
				//Set Lore
				if(main.getConfig().contains("reward." + s + ".item.lore")) {
					List<String> lore = main.getConfig().getStringList("reward." + s + ".item.lore");
					List<String> coloredLore = new ArrayList<String>();
					for(String l : lore) {
						coloredLore.add(ChatColor.translateAlternateColorCodes('&', l));
					}
					itemMeta.setLore(coloredLore);
				}
				
				//Set custom model data
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
