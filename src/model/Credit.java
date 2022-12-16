package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import services.ConnectionDBService;

import java.sql.*;
import java.util.ArrayList;
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
    private Date currentDate;
    private Date creditPaymentTime;
    private double monthlyPayment;


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
                credit.setCurrentDate(rs.getDate(7));
                credit.setCreditPaymentTime(rs.getDate(8));
                credit.setMonthlyPayment(rs.getDouble(9));
                credits.add(credit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return credits;
    }
    public static void insertCredit(Credit credit){
        String insert = "INSERT INTO credit (clientId, creditType, creditState, creditAmount, creditPaymentTime, monthlyPayment)";
        String values = "VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(insert + values);
            ps.setInt(1,credit.getCreditId());
            ps.setInt(2,credit.getCreditType());
            ps.setString(3,credit.getCreditState());
            ps.setDouble(4,credit.getCreditAmount());
            ps.setDate(5,credit.getCreditPaymentTime());

            ps.setDouble(6,credit.getMonthlyPayment());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
