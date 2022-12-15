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

public class Worker {
    private static final Connection connection = Objects.requireNonNull(ConnectionDBService.getConnection());
    private static Statement stmt;
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;


    public static List<Worker> getWorkers(){
        try {
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Worker> userData = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM worker ORDER BY workerId");
            while (rs.next()) {
                Worker worker = new Worker();
                worker.setId(rs.getInt("workerId"));
                worker.setFirstName(rs.getString("workerFName"));
                worker.setLastName(rs.getString("workerLName"));
                worker.setUsername(rs.getString("workerUsername"));
                worker.setPassword(rs.getString("workerPassword"));
                userData.add(worker);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userData;
    }

}
