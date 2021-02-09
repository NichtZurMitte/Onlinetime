package de.stan.Onlintime.utilities;

import java.io.File;


import de.stan.Onlintime.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * This is just a basic ConfigHelper...
 * @author Maze
 *
 */
public class ConfigHelper {

	Main plugin;
	public ConfigHelper(Main main){
		this.plugin = main;
	}
	
	//Generates and returns a specific Config (e.g. user-config)
	public FileConfiguration CreateNewConfig(String subDirection) {
		if(!getFile(subDirection).exists()){
			FileConfiguration cfg = getConfig(subDirection);
			try {
				cfg.save(getFile(subDirection));
				return cfg;
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Beim generieren der Config " + subDirection + " trat ein Fehler auf!");
			}
		}
		
		return getConfig(subDirection);
		
	}

	
	//Returns the specific File
	public File getFile(String subDirection){
		return new File("plugins/"+ plugin.getName() + "/" + subDirection + ".yml");
	}
	
	//Returns the specific Config
	public FileConfiguration getConfig(String subDirection){
		return YamlConfiguration.loadConfiguration(getFile(subDirection));	
	}
	
	
}
