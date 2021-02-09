package de.stan.Onlintime.event;

import de.stan.Onlintime.mySQL.MySQLOnline;
import de.stan.Onlintime.utilities.TimeUtils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;

public class PlayerListener implements Listener {

   private TimeUtils TimeUtils = new TimeUtils();
   
   
        @EventHandler
        public void onJoin2(PlayerJoinEvent e){
            Player p = e.getPlayer();
  
            MySQLOnline.setJoinedTime(p.getUniqueId(), new Date().getTime());
        }

        
        
        @EventHandler
        public void onQuit2(PlayerQuitEvent e) {
            Player p = e.getPlayer();
       
           double diff = TimeUtils.timecalc(p,  new Date().getTime());
           MySQLOnline.update2(p.getUniqueId(), diff, false);
            
        }
        

}
