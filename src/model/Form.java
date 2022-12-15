package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import services.ConnectionDBService;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class Form {
    private static Connection connection = Objects.requireNonNull(ConnectionDBService.getConnection());
    private static Statement stmt;
    private int formId;
    private int clietId;
    private Date initialDate;
    private Date birthDate;
    private String passportNumber;
    private String citizenship;
    private String residentalAddress;
    private String phoneNumber;
    private String familyStatus;
    private String workPlace;
    private double monthlySalary;
    private int loanType;
    private double receiveCash;
    private Date requestedPeriod;
    private String personalProperty;
    private Boolean currentLoans;
    private String formState;


    public static List<Form> getForms(){
        try{
            stmt = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Form> forms = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM forms ORDER BY formId");
            while (rs.next()){
                Form form = new Form();
                form.setFormId(rs.getInt(1));
                form.setClietId(rs.getInt(2));
                form.setInitialDate(rs.getDate(3));
                form.setBirthDate(rs.getDate(4));
                form.setPassportNumber(rs.getString(5));
                form.setCitizenship(rs.getString(6));
                form.setResidentalAddress(rs.getString(7));
                form.setPhoneNumber(rs.getString(8));
                form.setFamilyStatus(rs.getString(9));
                form.setWorkPlace(rs.getString(10));
                form.setMonthlySalary(rs.getDouble(11));
                form.setLoanType(rs.getInt(12));
                form.setReceiveCash(rs.getDouble(13));
                form.setRequestedPeriod(rs.getDate(14));
                form.setPersonalProperty(rs.getString(15));
                form.setCurrentLoans(rs.getBoolean(16));
                form.setFormState(rs.getString(17));
                forms.add(form);
            }
        }catch (Exception e){
            throw new RuntimeException();
        }
        return forms;
    }
    public static void insertForm(Form form){
        String insert = "INSERT INTO forms (clientId, birthDate, passportNumber, citizenship, residentialAddress, phoneNumber, familyStatus, workPlace, monthlySalary, loanType, receiveCash, requestedPeriod, personalProperty, currentLoans)";
        String values = "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(insert + values);
            ps.setInt(1,form.getClietId());
            ps.setDate(2,form.getBirthDate());
            ps.setString(3,form.getPassportNumber());
            ps.setString(4,form.getCitizenship());
            ps.setString(5,form.getResidentalAddress());
            ps.setString(6,form.getPhoneNumber());
            ps.setString(7,form.getFamilyStatus());
            ps.setString(8,form.getWorkPlace());
            ps.setDouble(9,form.getMonthlySalary());
            ps.setInt(10,form.getLoanType());
            ps.setDouble(11,form.getReceiveCash());
            ps.setDate(12,form.getRequestedPeriod());
            ps.setString(13,form.getPersonalProperty());
            ps.setBoolean(14,form.getCurrentLoans());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
