package de.stan.Onlintime.commands;

import de.stan.Onlintime.main.Main;
import de.stan.Onlintime.mySQL.MySQLOnline;
import de.stan.Onlintime.utilities.TimeUtils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;


public class onlineTimeCommand implements CommandExecutor {

    private Main main;
    private TimeUtils TimeUtils = new TimeUtils();
    
    public onlineTimeCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdlable, String[] args) {
        if(!(sender instanceof Player)) {
            System.out.println(Main.NO_PLAYER);
            return true;
        }
        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();
        if(!(p.hasPermission("onlineTime.player"))) {
            p.sendMessage(Main.NO_PERM);
            return true;
        }
        Player t;
        if(args.length >= 1) {
            t = Bukkit.getPlayer(args[0]);
           if(t == null) {
                p.sendMessage(Main.PREFIX + "§cDer Spieler: §6" + args[0] + " §cist nicht Online!");
                return true;
            }
        }else {
            long diffInMillis = (long) (MySQLOnline.getOnlineTime2(uuid) + TimeUtils.timecalc(p, new Date().getTime()));
            long Years = diffInMillis / 31536000000L % 1;
            long Months = diffInMillis / 2628002880L % 12;
            long Days = diffInMillis / 86400000L % 30;
            long Hours = diffInMillis / 3600000L % 60;
            long Minutes = diffInMillis / 60000L % 60;
            long Seconds = diffInMillis / 1000L % 60;
            p.sendMessage(main.PREFIX + "Deine Zeit: " + TimeUtils.getTimeString(Years, Months, Days, Hours, Minutes, Seconds));
            
         
            double  diff = TimeUtils.timecalc(p, new Date().getTime());
            MySQLOnline.update2(p.getUniqueId(), diff, false);
            MySQLOnline.setJoinedTime(p.getUniqueId(), new Date().getTime());
         
            
            return true;
        }
        if(args.length == 1) {
            p.sendMessage(String.valueOf(MySQLOnline.getOnlineTime2(t.getUniqueId())));


            long diffInMillis = (long) (MySQLOnline.getOnlineTime2(t.getUniqueId()) + TimeUtils.timecalc(t, new Date().getTime()));

            long Years = diffInMillis / 31536000000L % 1;
            long Months = diffInMillis / 2628002880L % 12;
            long Days = diffInMillis / 86400000L % 30;
            long Hours = diffInMillis / 3600000L % 60;
            long Minutes = diffInMillis / 60000L % 60;
            long Seconds = diffInMillis / 1000L % 60;

            p.sendMessage(Main.PREFIX + t.getName() + "'s Zeit: " + TimeUtils.getTimeString(Years, Months, Days, Hours, Minutes, Seconds));
            
            double  diff = TimeUtils.timecalc(t, new Date().getTime());
            MySQLOnline.update2(t.getUniqueId(), diff, false);
            MySQLOnline.setJoinedTime(t.getUniqueId(), new Date().getTime());
            
            return true;
        }



        if(args.length == 3 ) {
        	
        	if(args[1].equalsIgnoreCase("add")) {
        		
        	   	
                if(!(p.hasPermission("onlineTime.add"))) {
                    p.sendMessage(Main.PREFIX + Main.NO_PERM);
                    return true;
                }
                if(isNumeric(args[2])) {
                    int min = Integer.parseInt(args[2]);
                    double inMillies = min * 60 * 1000;
                    MySQLOnline.update2(t.getUniqueId(), inMillies, false);
                    p.sendMessage(Main.PREFIX + "§aDem Spieler: §6" + t.getName() + " §awurde Zeit hinzugefügt!" );
            
                } else {
                    p.sendMessage(Main.PREFIX + "§cMuss eine Zahle sein!");
            
                }

                return true;
        	}else if(!(args[1].equalsIgnoreCase("remove")) && !(args[1].equalsIgnoreCase("reset")) ) {
            	p.sendMessage(Main.PREFIX + "§cBitte benutze /onlinetime player add <time>!");
            	return true;
            
        	}
        	
        	if(args[1].equalsIgnoreCase("remove")) {
        	    if(!(p.hasPermission("onlineTime.remove"))) {
                    p.sendMessage(Main.PREFIX + Main.NO_PERM);
                    return true;
                }
                if(isNumeric(args[2])) {
                    int min = Integer.parseInt(args[2]);
                    double inMillies = min * 60 * 1000;
                    MySQLOnline.update2(t.getUniqueId(), inMillies, true);
                    p.sendMessage(Main.PREFIX + "§cDem Spieler: §6" + t.getName() + "§c wurde Zeit entfernt!");
                } else {
                    p.sendMessage(Main.PREFIX + "§cMuss eine Zahle sein!");
                }
                
                return true;
        	}else if(!(args[1].equalsIgnoreCase("add")) && !(args[1].equalsIgnoreCase("reset")) ) {
            	p.sendMessage(Main.PREFIX + "§cBitte benutze /onlinetime player remove <time>!");
            	return true;
            
        	}
     
        }
        
        if(args.length == 2) {
        	if(args[1].equalsIgnoreCase("reset")) {
        		
     		   if(!(p.hasPermission("onlineTime.reset"))) {
                    System.out.println(Main.PREFIX + Main.NO_PERM);
                return true;
                }
                MySQLOnline.resettime(t.getUniqueId());
     		   MySQLOnline.setJoinedTime(t.getUniqueId(), new Date().getTime());
     		   p.sendMessage(Main.PREFIX + "§4Die Zeit für: §6" + t.getName() + " §4wurde zurückgesetzt");
     		   return true;
     	}else {
         	p.sendMessage(Main.PREFIX + "Â§cBitte benutze /onlinetime player reset!");
         	return true;
         
     	}
        }
return false;
}


public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

}
