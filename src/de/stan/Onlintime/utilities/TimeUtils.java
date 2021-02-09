package de.stan.Onlintime.utilities;

import org.bukkit.entity.Player;

import de.stan.Onlintime.mySQL.MySQLOnline;

public class TimeUtils {

	
    public double timecalc(Player p, long current) {
        
        double joined = MySQLOnline.getJoinedTime(p.getUniqueId());
        
        double diff = current - joined;
        
        return (diff);
        
    	
    }
    
    public String getTimeString(long Year, long Month, long Day, long Hour, long Minute, long Second) {
    	
 
    	String Years = "", Months = "", Days = "", Hours = "", Minutes = "", Seconds = "";
    	
    	
    	if(Year > 0) {
    		Years = Year + " Jahr";
    		if(Year > 1)
    			Years = Years +  "e";	
    		Years = Years +  ", ";
    	}
    	if(Month > 0) {
    		Months = Month + " Monat";
    		if(Month > 1)
    			Months = Months +  "e";	
    		Months = Months +  ", ";
    	}
    	if(Day > 0) {
    	Days = Day + " Tag";
    		if(Day > 1)
    			Days = Days +  "e";
    		Days = Days +  ", ";
    	}
    	if(Hour > 0) {
    		Hours = Hour + " Stunde";
    		if(Hour > 1)
    			Hours = Hours +  "n";
    		Hours = Hours +  ", ";
    	}
    	if(Minute > 0) {
    	Minutes = Minute + " Minute";
    		if(Minute > 1)
    			Minutes = Minutes +  "n";
    		Minutes = Minutes +  ", ";
    	}
    	if(Second > 0) {
    		Seconds = Second + " Sekunde";
    		if(Second > 1)
    			Seconds = Seconds +  "n";
    	}
    	
    	String endString = Years + Months + Days + Hours + Minutes + Seconds;
    	
    	
    	return endString;
    }
	
}
