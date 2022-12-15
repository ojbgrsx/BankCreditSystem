package model;

import lombok.Getter;
import lombok.Setter;
import services.ConnectionDBService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter

public class Client {
    private static Connection connection = Objects.requireNonNull(ConnectionDBService.getConnection());
    private static Statement stmt;
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private double cash;

    public static List<Client> getClients() {
        try {
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Client> userData = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM client ORDER BY clientId");
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("clientId"));
                client.setFirstName(rs.getString("clientFName"));
                client.setLastName(rs.getString("clientLName"));
                client.setUsername(rs.getString("clientUsername"));
                client.setPassword(rs.getString("clientPassword"));
                client.setCash(rs.getDouble("clientCash"));
                userData.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userData;
    }

}

