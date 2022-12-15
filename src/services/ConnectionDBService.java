package services;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDBService {

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/bankcreditsystem";
            String username = "root";
            String password = "root";
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
