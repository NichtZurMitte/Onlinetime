package de.stan.Onlintime.main;

import de.stan.Onlintime.commands.onlineTimeCommand;
import de.stan.Onlintime.event.PlayerListener;
import de.stan.Onlintime.mySQL.MySQL;
import de.stan.Onlintime.mySQL.MySQLFile;
import de.stan.Onlintime.mySQL.MySQLOnline;
import de.stan.Onlintime.utilities.ConfigHelper;
import de.stan.Onlintime.utilities.UpdateHelper;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Main extends JavaPlugin {

    public FileConfiguration config;
    public ConfigHelper ch = new ConfigHelper(this);
    public static final String PREFIX = "§7[§6§lTrySurvive§r§7] §r";
    public static final String NO_PLAYER = "§4Du bist kein Spieler!§r";
    public static final String NO_PERM = "§4Dazu hast du keine Rechte";
    private static Main plugin;
    private MySQL mySQL;

    public  void onEnable() {
        super.onEnable();
        System.out.println("[Onlinetime] wurde erfolgreich Aktiviert!");

        MySQLFile file = new MySQLFile();
        file.setStandard();
        file.readData();


        MySQL.connect();
        MySQL.createtabel();
        registerCommands();
        registerEvents();
        plugin = this;

        if(!ch.getFile("config").exists()) {
            this.getConfig().options().copyHeader(true);
            this.getConfig().options().copyDefaults(true);
            this.saveConfig();
        }

    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerListener(), this);
    }


    private void registerCommands() {
        getCommand("ot").setExecutor(new onlineTimeCommand(this));


    }


    public void onDisable() {
        System.out.println("[Onlinetime] wurde erfolgreich Deaktiviert!");

        config = ch.getConfig("config");
        new UpdateHelper(this.getName(), Double.parseDouble(this.getDescription().getVersion()), this);


    }

    public static Main getPlugin() {
        System.out.println(plugin);
        return plugin;
    }
}

