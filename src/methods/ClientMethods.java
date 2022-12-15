package methods;

import model.Client;
import model.CreditTypes;
import org.apache.commons.lang3.StringUtils;
import services.ChangeService;
import services.PrettyTable;

import java.util.List;
import java.util.Scanner;

public class ClientMethods {
    public static void firstOption(){
        List<CreditTypes> creditTypesList = CreditTypes.getCreditTypes();
        System.out.println();
        System.out.println(StringUtils.center("CREDIT TYPES",140));
        for (int i=0;i<3;i++){
            System.out.println(i+1+") "+creditTypesList.get(i).getDescription());
            System.out.println();
        }
    }
    public static void secondOption(){
        System.out.println();
        System.out.println(StringUtils.center("RATES",40));
        List<CreditTypes> creditTypesList = CreditTypes.getCreditTypes();
        PrettyTable pt = new PrettyTable("#","CREDIT NAME","INTEREST RATE");
        for(int i=0;i<3;i++){
            CreditTypes creditType = creditTypesList.get(i);
            pt.addRow(String.valueOf(i+1),creditType.getName(),creditType.getInterestRate()+"%");
        }
        System.out.println(pt);
    }
    public static void thirdOption(){
        System.out.println();
        System.out.println(StringUtils.center("Conditions for issuing a loan".toUpperCase(),130));
        PrettyTable pt = new PrettyTable("#","Condition".toUpperCase());
        pt.addRow("*","A loan can be taken by a citizen of the country who has reached the age of 18 and has a passport at the time of application;");
        pt.addRow("*","Applicant must have property for collateral;");
        pt.addRow("*","The applicant must have a permanent job or other sources of income.");
        System.out.println(pt);
    }
    public static void fourthOption(){
        //############################################
        // FORM
    }
    public static void fifthOption(ChangeService changing, Client client){
        Scanner scan = new Scanner(System.in);
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("""
                            \n1. My bank account:
                            2. History of current credit:
                            3. Payment:
                            4. Personal data:
                            0. To exit
                            Choose the option:""");
            char optionMenu5 = sc.next().charAt(0);
            if (optionMenu5 == '1') {
                PrettyTable t = new PrettyTable("Name", "Surname", "Cash");
                t.addRow(client.getFirstName(), client.getLastName(), String.valueOf((long)client.getCash()));
                System.out.println("\n" + t);
            } else if (optionMenu5 == '2') {
                continue; // Istoriya credita
            } else if (optionMenu5 == '3') {
                continue; // Oplata
            } else if (optionMenu5 == '4') {
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
                        if(username.length() > 7){
                            changing.changeUsername(username);
                        }else {
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
            } else if (optionMenu5 == '0') {
                break;

            }
        }
    }
}
