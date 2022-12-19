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
    private int creditAmount;
    private Date creditTakenTime;
    private Date creditPaymentTime;
    private int monthlyPayment;
    private int leftMonths;


    public static List<Credit> getCredits() {
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
                credit.setCreditState(rs.getString(4));
                credit.setCreditAmount(rs.getInt(5));
                credit.setCreditTakenTime(rs.getDate(6));
                credit.setCreditPaymentTime(rs.getDate(7));
                credit.setMonthlyPayment(rs.getInt(8));
                credit.setLeftMonths(rs.getInt(9));
                credits.add(credit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return credits;
    }

    public static void insertCredit(Credit credit) {
        String insert = "INSERT INTO credit (clientId, creditType, creditState, creditAmount, creditPaymentTime, monthlyPayment, leftMonths)";
        String values = "VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(insert + values);
            ps.setInt(1, credit.getClientId());
            ps.setInt(2, credit.getCreditType());
            ps.setString(3, credit.getCreditState());
            ps.setInt(4, credit.getCreditAmount());
            ps.setDate(5, credit.getCreditPaymentTime());
            ps.setInt(6, credit.getMonthlyPayment());
            ps.setInt(7, credit.getLeftMonths());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateCreditCash(int id, int cash, int month, String state) {
        String u = String.format("Update credit set creditAmount = '%d',creditState = '%s',leftMonths = '%d' WHERE creditId = '%d'",cash,state,month,id);
//        String update = String.format("UPDATE credit SET creditAmount = '%d',", cash);
//        String update1 = String.format("leftMonths = '%d',", month);
//        String update2 = String.format("creditState = '%s'", state);
//        String set = String.format("WHERE creditId = '%d'", id);
        try {
            PreparedStatement ps = connection.prepareStatement(u);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
