package main;

import methods.ClientMethods;
import methods.WorkerMethods;
import model.Client;
import model.Form;
import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Testing {
    public static void main(String[] args) {
//        Date date;
//        ArrayList<Date> datelist = new ArrayList<>();
//        try{
//            Statement st = ConnectionDBService.getConnection().createStatement();
//            ResultSet resultSet = st.executeQuery("select * from testing");
//            while (resultSet.next()){
//                date = resultSet.getDate(1);
//                datelist.add(date);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println(datelist);

//        Object[] obj = new Object[1];
//        Client client = new Client();
//        obj[0] = client;
//        Client client1 = (Client) obj[0];
//        System.out.println(client1.getClass().getSimpleName());

//        ClientMenu.run(Client.getClients().get(0));
//        WorkerMenu.run(Worker.getWorkers().get(0));

            ClientMethods.firstOption();

        WorkerMethods.secondOption(Form.getForms(), Client.getClients());

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR,2);
        c.add(Calendar.MONTH,-5);

        java.util.Date date1 = c.getTime();
        Date date2 = new Date(date1.getTime());
//
//        System.out.println(date);
//        System.out.println(date2);
        Form form = new Form();
        form.setClietId(1);
        form.setBirthDate(date);
        form.setPassportNumber("id1234567".toUpperCase());
        form.setCitizenship(StringUtils.capitalize("kyrgyz"));
        form.setResidentalAddress("Baytik Baatyra 1/103");
        form.setPhoneNumber("996776511560");
        form.setFamilyStatus(StringUtils.capitalize("alone"));
        form.setWorkPlace("Google");
        form.setMonthlySalary(150000);
        form.setLoanType(1);
        form.setReceiveCash(1000000);
        form.setRequestedPeriod(date2);
        form.setPersonalProperty("House,Car");
        form.setCurrentLoans(false);
//        Form.insertForm(form);
//        System.out.println(Form.getForms());
    }
}
