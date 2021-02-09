package de.stan.Onlintime.mySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLOnline {

    public static boolean isUserExist(UUID uuid) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT time FROM Onlinetime WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static void update(UUID uuid, int amount, boolean remove, String playername) {
        int time = getOnlineTime(uuid);

        if(remove) {
            amount-=time;
        } else  {
            amount+=time;
        }

        if(isUserExist(uuid)) {
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE OnlineZeit SET onTimeIMin  = ? WHERE UUID = ?");
                ps.setInt(1, amount);
                ps.setString(2, uuid.toString());

                        ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }else {
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO OnlineZeit (UUID,Playername,onTimeIMin) VALUES (?,?,?)");
                ps.setString(1, uuid.toString());
                ps.setString(2, playername);
                ps.setInt(3, time);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
   

    public static void delete(UUID uuid) {
        if(isUserExist(uuid)) {
            PreparedStatement ps = null;
            try {
                ps = MySQL.getConnection().prepareStatement("DELETE * FROM OnlineZeit WHERE UUID = ?");
                ps.setString(1, uuid.toString());
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }else {
            System.err.println("[OnlineZeit] Der Spieler ist nicht in der Datenbank eingetragen!");

        }

    }

    public static Integer getOnlineTime(UUID uuid) {

        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT time FROM Onlinetime");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("time");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
    

    public static void update2(UUID uuid, double diff, boolean remove) {
    	System.out.println(diff);
        double time = getOnlineTime2(uuid);

        if(remove) {
            diff-=time;
        } else  {
            diff+=time;
        }

        if(isUserExist(uuid)) {
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE Onlinetime SET time  = ? WHERE UUID = ?");
                ps.setDouble(1, diff);
                ps.setString(2, uuid.toString());

                        ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }else {
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO Onlinetime (UUID,time) VALUES (?,?)");
                ps.setString(1, uuid.toString());
                ps.setDouble(2, time);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
    
    public static Long getOnlineTime2(UUID uuid) {

        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT time FROM Onlinetime where uuid = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getLong("time");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return (long) -1;
    }
    
    public static Double getJoinedTime(UUID uuid) {

        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT joined FROM Onlinetime where uuid = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getDouble("joined");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  (double) -1;
    }
    
    
    public static void setJoinedTime(UUID uuid, long time) {
    
    	
    	try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE Onlinetime SET joined = ? WHERE UUID = ?");
            ps.setLong(1, time);
            ps.setString(2, uuid.toString());
        	System.out.println("--->>>" + time);
                    ps.executeUpdate();
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    	
    }

    public static void resettime(UUID uuid) {
        PreparedStatement ps = null;
        try {
            ps = MySQL.getConnection().prepareStatement("UPDATE Onlinetime SET time = ? WHERE UUID = ?");
            ps.setLong(1, 0);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


}
