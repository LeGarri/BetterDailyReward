package fr.dailyreward.manager;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.dailyreward.Main;

public class LangManager {

	private Main main;
	
	private File langFile;
	private FileConfiguration lang;
	
	
	
	public LangManager(Main main) {
		this.main = main;
		
		loadLang();
	}
	
	
	
	public String getString(String s) {
		return lang.getString(s).replaceAll("&", "§");
	}

	
	

	
	
	
	public void loadLang() {
		File dir = new File(main.getDataFolder() + File.separator + "lang");
		
		if(!dir.exists()) {
			dir.mkdir();
		}
		
		
		langFile = new File(main.getDataFolder() + File.separator + "lang", main.getConfigManager().getLang() + ".yml");
		
		if(!langFile.exists()) {
			main.saveResource("lang" + File.separator + "en.yml", false);
		}
		
		
		lang = new YamlConfiguration();
		
		try {
			lang.load(langFile);
			
			System.out.println(Main.CONSOLE_PREFIX + "§aLang loaded !");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getLang() {
		return this.lang;
	}
	
	public void saveLang() {
		try {
			getLang().save(langFile);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}



































