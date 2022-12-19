package model;

import lombok.Getter;
import lombok.Setter;
import services.ConnectionDBService;

import java.math.RoundingMode;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
    private int cash;

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
                client.setCash(rs.getInt("clientCash"));
                userData.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userData;
    }
    public static void updateCash(int id,int cash){

        String update = String.format("UPDATE client SET clientCash = '%d'",cash);
        String set = String.format("WHERE clientId = '%d'",id);
        try {
            PreparedStatement ps = connection.prepareStatement(update+set);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

