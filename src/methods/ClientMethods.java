package methods;

import model.Client;
import model.Credit;
import model.CreditTypes;
import model.Form;
import org.apache.commons.lang3.StringUtils;
import services.ChangeService;
import services.PrettyTable;

import java.util.List;
import java.util.Scanner;

public class ClientMethods {
    public static void firstOption() {
        List<CreditTypes> creditTypesList = CreditTypes.getCreditTypes();
        System.out.println();
        System.out.println(StringUtils.center("CREDIT TYPES", 170));
        for (int i = 0; i < 3; i++) {
            System.out.println(i + 1 + ") " + creditTypesList.get(i).getDescription());
            System.out.println();
        }
    }

    public static void secondOption() {
        System.out.println();
        List<CreditTypes> creditTypesList = CreditTypes.getCreditTypes();
        PrettyTable pt = new PrettyTable("#", "CREDIT NAME", "INTEREST RATE");
        for (int i = 0; i < 3; i++) {
            CreditTypes creditType = creditTypesList.get(i);
            pt.addRow(String.valueOf(i + 1), creditType.getName(), creditType.getInterestRate() + "%");
        }
        System.out.println(pt);
    }

    public static void thirdOption() {
        System.out.println();
        PrettyTable pt = new PrettyTable("#", "Conditions for issuing a loan".toUpperCase());
        pt.addRow("*", "A loan can be taken by a citizen of the country who has reached the age of 18 and has a passport at the time of application;");
        pt.addRow("*", "Applicant must have property for collateral;");
        pt.addRow("*", "The applicant must have a permanent job or other sources of income.");
        System.out.println(pt);
    }

    public static void fourthOption() {
        //############################################
        // FORM
    }

    public static void fifthOption(ChangeService changing, Client client) {
        Scanner scan;
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
                t.addRow(client.getFirstName(), client.getLastName(), String.valueOf((long) client.getCash()));
                System.out.println("\n" + t);
            } else if (optionMenu5 == '2') {
                while (true) {
                    try {
                        Credit credit = Credit.getCredits().stream().filter(i -> i.getClientId() == client.getId() && i.getCreditState().equals("open")).findAny().get();
                        Form form = Form.getForms().stream().filter(i -> i.getClientId() == client.getId() && i.getFormState().equals("accepted")).findAny().get();
                        Scanner scan1 = new Scanner(System.in);
                        System.out.println("""
                                \n1. Total loan amount
                                2. Remaining loan amount
                                3. Repaid loan amount
                                4. Full repayment period
                                5. To exit
                                Choose the option:""");
                        String option = scan1.nextLine();
                        if (option.equals("1")) {
                            System.out.println("\n\t Your total loan amount is " + WorkerMethods.calculatingTotalCash(form) + " soms!\n");
                        } else if (option.equals("2")) {
                            System.out.println("\n\tYour remaining loan amount is " + credit.getCreditAmount() + " soms!\n");
                        } else if (option.equals("3")) {
                            int cash = WorkerMethods.calculatingTotalCash(form) - credit.getCreditAmount();
                            System.out.println("\n\tYour repaid loan amount is " + cash + " soms!\n");
                        } else if (option.equals("4")) {
                            System.out.println("\n\t Full repayment period is " + credit.getCreditPaymentTime() + "\n");
                        } else if (option.equals("5")) {
                            break;
                        } else {
                            System.out.println("\nIncorrect option !");
                        }
                    } catch (Exception e) {
                        System.out.println("\n\tYou don't have credit history !");
                        break;
                    }
                }
            } else if (optionMenu5 == '3') {
                while (true) {
                    try {
                        Credit credit = Credit.getCredits().stream().filter(i -> i.getClientId() == client.getId() && i.getCreditState().equals("open")).findAny().get();
                        while (true) {
                            Scanner scan1 = new Scanner(System.in);
                            System.out.println("Amount of left months: " + credit.getLeftMonths());
                            System.out.print("Number of months, '0' to exit: ");
                            int month = scan1.nextInt();
                            if (month <= credit.getLeftMonths() && month > 0) {
                                int totalCash = credit.getMonthlyPayment() * month;
                                System.out.println(totalCash);
                                if (totalCash <= client.getCash()) {
                                    Credit.updateCreditCash(credit.getCreditId(), credit.getCreditAmount() - totalCash, credit.getLeftMonths() - month,"open");
                                    Client.updateCash(client.getId(), client.getCash() - totalCash);
                                    System.out.println("\n\tYou have payed your credit for " + month + " month(s) successfully!\n");
                                } else {
                                    System.out.println("\n\tYou have not enough money to proceed the payment !\n");
                                }
                                break;
                            } else if (month == 0) {
                                break;
                            } else {
                                System.out.println("\n\tMonth is out of range !\n");
                            }

                        }
                    } catch (Exception e) {
                        System.out.println("\n\tYou don't have credit history !");
                        break;
                    }
                    try {
                        Credit credit = Credit.getCredits().stream().filter(i -> i.getClientId() == client.getId() && i.getCreditState().equals("open")).findAny().get();
                        if (credit.getCreditAmount()<=0){
                            Credit.updateCreditCash(credit.getCreditId(),0,0,"closed");
                            System.out.println("You have successfully closed your loan !");
                        }
                        break;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
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
            } else if (optionMenu5 == '0') {
                break;

            }
        }
    }
}
