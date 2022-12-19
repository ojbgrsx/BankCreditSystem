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
import java.util.List;
import java.util.Objects;
@Getter
@Setter
@ToString
public class CreditTypes {

    private static Connection connection = Objects.requireNonNull(ConnectionDBService.getConnection());
    private static Statement stmt;

    private int creditTypeId;
    private String name;
    private String description;
    private int interestRate;
    private int interestRatePerMonth;

    public static List<CreditTypes> getCreditTypes(){
            try {
                stmt = connection.createStatement();
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<CreditTypes> creditTypes = new ArrayList<>();
            try {
                ResultSet rs = stmt.executeQuery("SELECT * FROM credittype ORDER BY creditTypeId");
                while (rs.next()) {
                    CreditTypes creditType = new CreditTypes();
                    creditType.setCreditTypeId(rs.getInt(1));
                    creditType.setName(rs.getString(2));
                    creditType.setDescription(rs.getString(3));
                    creditType.setInterestRate(rs.getInt(4));
                    creditTypes.add(creditType);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return creditTypes;

    }
}
