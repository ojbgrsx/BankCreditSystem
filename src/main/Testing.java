package main;

import methods.ClientMethods;
import methods.WorkerMethods;
import model.Client;
import model.Form;
import model.Worker;
import org.apache.commons.lang3.StringUtils;

import java.math.RoundingMode;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

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

        ClientMenu.run(Client.getClients().get(0));
//        WorkerMenu.run(Worker.getWorkers().get(0));

//            ClientMenu.run(Client.getClients().get(0));

//        WorkerMethods.secondOption(Form.getForms(), Client.getClients());
//        Form form = Form.getForms().get(0);
//        Date currentDate = form.getInitialDate();
//        Date requestDate = form.getRequestedPeriod();
//        TimeUnit tu = TimeUnit.DAYS;
//        long time = requestDate.getTime()-currentDate.getTime();
//        int month = (int) (tu.convert(time,TimeUnit.MILLISECONDS)/30);
//        System.out.println(tu.convert(time,TimeUnit.MILLISECONDS)/30);
//        System.out.println(WorkerMethods.calculatingMonthCash(form));
// 66315.7895
//        long now = System.currentTimeMillis();
//        Date date = new Date(now);

//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//        c.add(Calendar.YEAR,2);
//        c.add(Calendar.MONTH,-5);
//
//        java.util.Date date1 = c.getTime();
//        Date date2 = new Date(date1.getTime());
////
////        System.out.println(date);
////        System.out.println(date2);
//        Form form = new Form();
//        form.setClietId(1);
//        form.setBirthDate(date);
//        form.setPassportNumber("id1234567".toUpperCase());
//        form.setCitizenship(StringUtils.capitalize("kyrgyz"));
//        form.setResidentalAddress("Baytik Baatyra 1/103");
//        form.setPhoneNumber("996776511560");
//        form.setFamilyStatus(StringUtils.capitalize("alone"));
//        form.setWorkPlace("Google");
//        form.setMonthlySalary(150000);
//        form.setLoanType(1);
//        form.setReceiveCash(1000000);
//        form.setRequestedPeriod(date2);
//        form.setPersonalProperty("House,Car");
//        form.setCurrentLoans(false);
//        Form.insertForm(form);
//        System.out.println(Form.getForms());
    }
}
