package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import services.ConnectionDBService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
@Getter
@Setter
@ToString
public class Credit {

    private static Connection connection = Objects.requireNonNull(ConnectionDBService.getConnection());
    private static Statement stmt;

    private int creditId;
    private int clientId;
    private int creditType;
    private String creditState;
    private double creditAmount;
    private Date creditTakenTime;
    private Date creditPaymentTime;


    public static List<Credit> getCredits(){
        try {
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Credit> credits = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM credit ORDER BY creditId");
            while (rs.next()) {
                Credit credit = new Credit();
                credit.setCreditId(rs.getInt(1));
                credit.setClientId(rs.getInt(2));
                credit.setCreditType(rs.getInt(3));
                credit.setCreditAmount(rs.getDouble(4));
                credit.setCreditAmount(rs.getDouble(5));
                credit.setCreditTakenTime(rs.getDate(6));
                credit.setCreditPaymentTime(rs.getDate(7));
                credits.add(credit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return credits;
    }
    public static void insertCredit(Credit credit){

    }
}
