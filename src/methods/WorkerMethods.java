package methods;

import model.Client;
import model.Credit;
import model.CreditTypes;
import model.Form;
import services.ChangeService;
import services.ConnectionDBService;
import services.PrettyTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WorkerMethods {

    private static final Connection CONNECTION = Objects.requireNonNull(ConnectionDBService.getConnection());

    public static void firstOption(List<Client> clientsList) {
        PrettyTable table = new PrettyTable("Client ID", "First Name", "Last Name", "Client Cash");
        for (Client i : clientsList) {
            table.addRow(String.valueOf(i.getId()), i.getFirstName(), i.getLastName(), String.valueOf((long) i.getCash()));
        }
        System.out.println(table);
    }

    public static void secondOption(List<Form> formsList, List<Client> clientsList) {
        Scanner scan = new Scanner(System.in);
        PrettyTable pt = new PrettyTable("Form ID", "Client ID", "Full name", "Birth date", "Passport number", "Monthly salary", "Loan type", "Requested period", "Property", "Current loans", "Form state");
        Client client = null;
        for (Form i : formsList) {
            client = clientsList.stream().filter(j -> j.getId() == i.getClietId()).findAny().get();
            pt.addRow(String.valueOf(i.getFormId()), String.valueOf(i.getClietId()), client.getFirstName() + " " + client.getLastName(), String.valueOf(i.getBirthDate()), i.getPassportNumber(), String.valueOf(i.getMonthlySalary()), String.valueOf(i.getLoanType()), String.valueOf(i.getRequestedPeriod()), i.getPersonalProperty(), String.valueOf(i.getCurrentLoans()), i.getFormState());
        }
        System.out.println("Choose the form state\n1) Pending\n2) Accepted\n3) Declined\n0) To exit");
        String formState = scan.nextLine();
        if(formState.equals("1")){
            List<Form> pending = formsList.stream().filter(i -> i.getFormState().equals("pending")).collect(Collectors.toList());
            if(pending.size()>0){
                System.out.println(pt);
            }
            else {
                System.out.println("\n\tThere is no pendind forms !");
                return;
            }
            while(true){
                System.out.print("Choose the form id, '0' to exit: ");
                Form form = new Form();
                String formId  = scan.nextLine();
                boolean exist = false;
                if(!formId.equals("0")){
                    for(Form i : pending){
                        if(String.valueOf(i.getFormId()).equals(formId)){
                            exist = true;
                            form = i;
                            break;
                        }
                    }
                }
                while(exist){
                    System.out.print("1) To accept\n2) To decline\n3) To exit: ");
                    char ch = scan.next().charAt(0);
                    if(ch == '1'){
                        String set = "UPDATE forms SET formState = 'accepted' ";
                        String update = String.format("WHERE formId = %d",Integer.valueOf(formId));
                        try {
                            PreparedStatement ps = CONNECTION.prepareStatement(set+update);
                            ps.executeUpdate();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        Credit credit = new Credit();
                        credit.setClientId(client.getId());
                        credit.setCreditType(form.getLoanType());
                        credit.setCreditState("open");
                        credit.setCreditAmount(calculatingTotalCash(form));
                        credit.setCreditPaymentTime(form.getRequestedPeriod());

                        exist = false;
                    } else if (ch == '2') {
                        System.out.print("Write the reason: ");
                        String reason = scan.nextLine();
                        String set = "UPDATE forms SET formState = 'declined' ";
                        String update = String.format("WHERE formId = %d",Integer.valueOf(formId));
                        try {
                            PreparedStatement ps = CONNECTION.prepareStatement(set+update);
                            ps.executeUpdate();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        exist = false;
                    }else if(ch == '3'){
                        exist = false;
                    }
                    else {
                        System.out.println("Incorrect option !");
                        continue;
                    }
                }
            }
        }

    }

    public static void fifthOption(ChangeService changing) {
        Scanner scan = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("""
                    1. Change username
                    2. Change password
                    0. To exit
                    :""");
            char q = sc.next().charAt(0);
            scan = new Scanner(System.in);
            if (q == '1') {
                System.out.print("Enter username >>> ");
                String username = scan.nextLine();
                if (username.length() > 7) {
                    changing.changeUsername(username);
                } else {
                    System.out.println("Username length should be more than 8 symbols.");
                }
                break;
            } else if (q == '2') {
                System.out.print("Enter password >>>");
                String password = scan.nextLine();
                changing.changePassword(password);
                break;
            } else if (q == '0') {
                break;
            } else {
                System.out.println("Wrong option !");
            }
        }

    }
    public static double calculatingTotalCash(Form form){
        int creditRate = CreditTypes.getCreditTypes().get(form.getLoanType()-1).getInterestRate();
        double rateAmount = (form.getReceiveCash() * creditRate ) / 100;
        return form.getReceiveCash() + rateAmount;
    }
    public static double calculatingMonthCash(Form form){

        return 0.0;
    }
}
