package methods;

import model.Client;
import model.Credit;
import model.CreditTypes;
import model.Form;
import org.apache.commons.lang3.StringUtils;
import services.ChangeService;
import services.PrettyTable;

import java.sql.Date;
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

    public static void fourthOption(Client client) {
        Form form = new Form();
        form.setClientId(client.getId());
        while (true) {
            Scanner scan = new Scanner(System.in);
            System.out.println("\nEnter your birth date\n");
            long now = System.currentTimeMillis();
            Date curDate = new Date(now);
            System.out.print("Day: ");
            String day = scan.nextLine();
            if (Integer.parseInt(day) <= 31) {
                System.out.print("Month: ");
                String month = scan.nextLine();
                if (Integer.parseInt(month) <= 12) {
                    System.out.print("Year: ");
                    String year = scan.nextLine();
                    Date birthDate = Date.valueOf(year + "-" + month + "-" + day);
                    if (birthDate.getTime() <= curDate.getTime()) {
                        form.setBirthDate(birthDate);
                        break;
                    } else {
                        System.out.println("There is no these date in calendar !");
                    }
                } else {
                    System.out.println("Incorrect month !");
                }
            } else {
                System.out.println("Incorrect day !");
            }

        }
        while (true) {
            Scanner scan = new Scanner(System.in);
            System.out.print("\nEnter passport number: ");
            String passport = scan.nextLine();
            if (passport.length() == 9) {
                form.setPassportNumber(passport);
                break;
            } else {
                System.out.println("Incorrect passport number!");
            }
        }
        System.out.print("\nEnter your citizenship: ");
        Scanner scanner = new Scanner(System.in);
        form.setCitizenship(StringUtils.capitalize(scanner.nextLine().toLowerCase()));
        System.out.print("\nEnter residential address: ");
        form.setResidentialAddress(StringUtils.capitalize(scanner.nextLine().toLowerCase()));
        while (true) {
            Scanner scan = new Scanner(System.in);
            System.out.print("\nEnter phone number: ");
            String phone = scan.nextLine();
            if (phone.length() == 12) {
                form.setPhoneNumber(phone);
                break;
            } else {
                System.out.println("Incorrect phone number!");
            }
        }
        System.out.print("\nEnter family status: ");
        form.setFamilyStatus(StringUtils.capitalize(scanner.nextLine().toLowerCase()));
        System.out.print("\nEnter work place: ");
        form.setWorkPlace(StringUtils.capitalize(scanner.nextLine().toLowerCase()));
        System.out.print("\nEnter monthly salary: ");
        form.setMonthlySalary(Integer.parseInt(scanner.nextLine()));
        System.out.print("\nEnter loan type(1, 2 or 3): ");
        form.setLoanType(Integer.parseInt(scanner.nextLine()));
        System.out.print("\nEnter your receiving cash: ");
        form.setReceiveCash(Integer.parseInt(scanner.nextLine()));
        while (true) {
            Scanner scan = new Scanner(System.in);
            System.out.println("\nEnter your requested date\n");
            long now = System.currentTimeMillis();
            Date curDate = new Date(now);
            System.out.print("Day: ");
            String day = scan.nextLine();
            if (Integer.parseInt(day) <= 31) {
                System.out.print("Month: ");
                String month = scan.nextLine();
                if (Integer.parseInt(month) <= 12) {
                    System.out.print("Year: ");
                    String year = scan.nextLine();
                    Date requestedDate = Date.valueOf(year + "-" + month + "-" + day);
                    if (requestedDate.getTime() > curDate.getTime()) {
                        form.setRequestedPeriod(requestedDate);
                        break;
                    } else {
                        System.out.println("There is no these date in calendar !");
                    }
                } else {
                    System.out.println("Incorrect month !");
                }
            } else {
                System.out.println("Incorrect day !");
            }
        }
        System.out.print("\nEnter your personal property: ");
        form.setPersonalProperty(StringUtils.capitalize(scanner.nextLine().toLowerCase()));
        System.out.print("\nEnter current loan('1' if you have, '0' if you don't): ");
        while (true){
            Scanner scan = new Scanner(System.in);
            String currentLoan = scan.nextLine();
            if(currentLoan.equals("1")){
                form.setCurrentLoans(true);
                break;
            } else if (currentLoan.equals("0")) {
                form.setCurrentLoans(false);
                break;
            }
            else {
                System.out.println("Incorrect option !");
            }
        }
        Form.insertForm(form);
        System.out.println("You have successfully send your request, please wait !");
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
                t.addRow(client.getFirstName(), client.getLastName(), String.valueOf(client.getCash()));
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
                                    Credit.updateCreditCash(credit.getCreditId(), credit.getCreditAmount() - totalCash, credit.getLeftMonths() - month, "open");
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
                        if (credit.getCreditAmount() <= 0) {
                            Credit.updateCreditCash(credit.getCreditId(), 0, 0, "closed");
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
