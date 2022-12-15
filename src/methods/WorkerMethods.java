package methods;

import model.Client;
import model.Form;
import services.ChangeService;
import services.PrettyTable;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WorkerMethods {
    public static void firstOption(List<Client> clientsList) {
        PrettyTable table = new PrettyTable("Client ID", "First Name", "Last Name", "Client Cash");
        for (Client i : clientsList) {
            table.addRow(String.valueOf(i.getId()), i.getFirstName(), i.getLastName(), String.valueOf((long) i.getCash()));
        }
        System.out.println(table);
    }

    public static void secondOption(List<Form> formsList, List<Client> clientsList) {
        List<Form> pending = formsList.stream().filter(i -> i.getFormState().equals("pending")).collect(Collectors.toList());

        PrettyTable pt = new PrettyTable("Form ID", "Client ID", "Full name", "Birth date", "Passport number", "Phone number", "Monthly salary", "Loan type", "Requested period", "Property", "Current loans", "Form state");
        for (Form i : pending) {
            Client client = clientsList.stream().filter(j -> j.getId() == i.getClietId()).findAny().get();
            pt.addRow(String.valueOf(i.getFormId()), String.valueOf(i.getClietId()), client.getFirstName() + " " + client.getLastName(), String.valueOf(i.getBirthDate()), i.getPassportNumber(), i.getPhoneNumber(), String.valueOf(i.getMonthlySalary()), String.valueOf(i.getLoanType()), String.valueOf(i.getRequestedPeriod()), i.getPersonalProperty(), String.valueOf(i.getCurrentLoans()), i.getFormState());
        }
        System.out.println(pt);

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
}
