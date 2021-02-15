package github.JulianNSH.SHOP.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
Class for connecting to database
 */
public class DatabaseConnector {

    private static final String url = "jdbc:postgresql://localhost:5432/shop";
    private static final String user = "postgres";
    private static final String password = "root";

    public DatabaseConnector(){}

    public static Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            //System.out.println("Connected to PG server");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

}