package com.artcomp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
        		"jdbc:mysql://localhost:3306/art_competition", 
            "root",
            "shubhdip@07"
        );
    }
}
