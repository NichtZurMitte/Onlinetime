package de.stan.Onlintime.mySQL;

import de.stan.Onlintime.main.Main;

import java.sql.*;

public class MySQL {

    public static String host;
    public static String port;
    public static String database;
    public static String username;
    public static String password;
    public static Connection con;
    private Main main;

    public MySQL(Main main) {
        this.main = main;
    }


    public static void connect() {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                System.out.println("[Onlinetime] Verbindung aufgebaut!");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


    }
    public static void createtabel() {
        try {
            con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS Onlinetime (UUID VARCHAR(100), Playername VARCHAR(100), time DOUBLE, joined DOUBLE)");
            System.out.println("createtable1 2");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void disconnect() {
        if(isConnected()) {
            try {
                con.close();
                System.out.println("[Onlinetime] Verbindung geschlossen!");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static boolean isConnected() {
        return(con == null ? false : true);
    }

    public static Connection getConnection() {
        return con;
    }
}

