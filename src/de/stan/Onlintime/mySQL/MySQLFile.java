package de.stan.Onlintime.mySQL;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MySQLFile {


    public void setStandard() {
        FileConfiguration cfg = getFileConfiguration();

        cfg.options().copyDefaults(true);

        cfg.addDefault("host"," ");
        cfg.addDefault("port", "");
        cfg.addDefault("database", "");
        cfg.addDefault("username", "");
        cfg.addDefault("password", "");

        try {
            cfg.save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFile() {
        return new File("plugins/onlinetime" , "mysql.yml");
    }

    private FileConfiguration getFileConfiguration() {
        return YamlConfiguration.loadConfiguration(getFile());
    }

    public void readData() {
        FileConfiguration cfg = getFileConfiguration();

        MySQL.host = cfg.getString("host");
        MySQL.port = cfg.getString("port");
        MySQL.database = cfg.getString("database");
        MySQL.username = cfg.getString("username");
        MySQL.password = cfg.getString("password");



    }
}
