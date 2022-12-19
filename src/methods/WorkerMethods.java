package methods;

import model.*;
import org.apache.commons.lang3.StringUtils;
import services.ChangeService;
import services.ConnectionDBService;
import services.PrettyTable;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
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
        while (true) {
            Scanner scan = new Scanner(System.in);
            PrettyTable pt = new PrettyTable("Form ID", "Client ID", "Full name", "Birth date", "Passport number", "Monthly salary", "Loan type", "Requested period", "Property", "Current loans", "Form state");
            Client client = null;
            System.out.print("\n1) Pending\n2) Accepted\n3) Declined\n0) To exit\nChoose the form state: ");
            String formState = scan.nextLine();
            if (formState.equals("1")) {
                while (true) {
                    List<Form> pending = formsList.stream().filter(i -> i.getFormState().equals("pending")).collect(Collectors.toList());
                    if (pending.size() > 0) {
                        for (Form i : pending) {
                            client = clientsList.stream().filter(j -> j.getId() == i.getClientId()).findAny().get();
                            pt.addRow(String.valueOf(i.getFormId()), String.valueOf(i.getClientId()), client.getFirstName() + " " + client.getLastName(), String.valueOf(i.getBirthDate()), i.getPassportNumber(), String.valueOf(i.getMonthlySalary()), String.valueOf(i.getLoanType()), String.valueOf(i.getRequestedPeriod()), i.getPersonalProperty(), String.valueOf(i.getCurrentLoans()), i.getFormState());
                        }
                        System.out.println(pt);
                    } else {
                        System.out.println("\n\tThere is no pendind forms !");
                        break;
                    }
                    System.out.print("Choose the form id, '0' to exit: ");
                    Form form = new Form();
                    String formId = scan.nextLine();
                    boolean exist = false;
                    if (!formId.equals("0")) {
                        for (Form i : pending) {
                            if (String.valueOf(i.getFormId()).equals(formId)) {
                                exist = true;
                                form = i;
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                    while (exist) {
                        Scanner scan1 = new Scanner(System.in);
                        System.out.print("1) To accept\n2) To decline\n3) To exit: ");
                        char ch = scan1.next().charAt(0);
                        if (ch == '1') {
                            String set = "UPDATE forms SET formState = 'accepted' ";
                            String update = String.format("WHERE formId = %d", Integer.valueOf(formId));
                            try {
                                PreparedStatement ps = CONNECTION.prepareStatement(set + update);
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
                            credit.setMonthlyPayment(calculatingMonthCash(form));
                            credit.setLeftMonths(gettingLeftMonths(form));
                            Credit.insertCredit(credit);
                            exist = false;
                            Client.updateCash(client.getId(), client.getCash()+ credit.getCreditAmount());
                            System.out.println("\n\tYou have successfully accepted the request! \n");
                            return;
                        } else if (ch == '2') {
                            String set = "UPDATE forms SET formState = 'declined' ";
                            String update = String.format("WHERE formId = %d", Integer.valueOf(formId));
                            try {
                                PreparedStatement ps = CONNECTION.prepareStatement(set + update);
                                ps.executeUpdate();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            exist = false;
                            System.out.println("\n\tYou have successfully declined the request! \n");
                            return;
                        } else if (ch == '3') {
                            exist = false;
                            break;
                        } else {
                            System.out.println("Incorrect option !");
                            continue;
                        }
                    }
                }
            } else if (formState.equals("2")) {
                List<Form> accepted = formsList.stream().filter(i -> i.getFormState().equals("accepted")).collect(Collectors.toList());
                if (accepted.size() > 0) {
                    for (Form i : accepted) {
                        client = clientsList.stream().filter(j -> j.getId() == i.getClientId()).findAny().get();
                        pt.addRow(String.valueOf(i.getFormId()), String.valueOf(i.getClientId()), client.getFirstName() + " " + client.getLastName(), String.valueOf(i.getBirthDate()), i.getPassportNumber(), String.valueOf(i.getMonthlySalary()), String.valueOf(i.getLoanType()), String.valueOf(i.getRequestedPeriod()), i.getPersonalProperty(), String.valueOf(i.getCurrentLoans()), i.getFormState());
                    }
                    System.out.print(pt);
                } else {
                    System.out.println("\n\tThere is no accepted forms !");
                }
            } else if (formState.equals("3")) {
                List<Form> declined = formsList.stream().filter(i -> i.getFormState().equals("declined")).collect(Collectors.toList());
                if (declined.size() > 0) {
                    for (Form i : declined) {
                        client = clientsList.stream().filter(j -> j.getId() == i.getClientId()).findAny().get();
                        pt.addRow(String.valueOf(i.getFormId()), String.valueOf(i.getClientId()), client.getFirstName() + " " + client.getLastName(), String.valueOf(i.getBirthDate()), i.getPassportNumber(), String.valueOf(i.getMonthlySalary()), String.valueOf(i.getLoanType()), String.valueOf(i.getRequestedPeriod()), i.getPersonalProperty(), String.valueOf(i.getCurrentLoans()), i.getFormState());
                    }
                    System.out.print(pt);
                } else {
                    System.out.println("\n\tThere is no declined forms !");

                }
            } else if (formState.equals("0")) {
                return;
            } else {
                System.out.println("Incorrect option! ");
            }
        }

    }

    public static void thirdOption(List<Credit> credits, List<Client> clientList) {
        List<Credit> openCredits = credits.stream().filter(i -> i.getCreditState().equals("open")).collect(Collectors.toList());
        PrettyTable pt = new PrettyTable("ID", "Client Name", "Client Surname", "Type", "Amount", "Taken Time", "Payment Time", "Month Payment", "Month Left");
        Client client = null;
        if (openCredits.size() > 0) {
            for (Credit i : openCredits) {
                client = clientList.stream().filter(j -> j.getId() == i.getClientId()).findAny().get();
                pt.addRow(String.valueOf(i.getCreditId()), client.getFirstName(), client.getLastName(), String.valueOf(i.getCreditType()), String.valueOf(i.getCreditAmount()), String.valueOf(i.getCreditTakenTime()), String.valueOf(i.getCreditPaymentTime()), String.valueOf(i.getMonthlyPayment()), String.valueOf(i.getLeftMonths()));
            }
            System.out.println(pt);
        } else {
            System.out.println("\n\tThere is no open credits !");
            return;
        }
    }
    public static void fourthOption(List<Credit> credits, List<Client> clientList) {
        List<Credit> closedCredits = credits.stream().filter(i -> i.getCreditState().equals("closed")).collect(Collectors.toList());
        PrettyTable pt = new PrettyTable("ID", "Client Name", "Client Surname", "Type", "Amount", "Taken Time", "Payment Time", "Month Payment", "Month Left");
        Client client = null;
        if (closedCredits.size() > 0) {
            for (Credit i : closedCredits) {
                client = clientList.stream().filter(j -> j.getId() == i.getClientId()).findAny().get();
                pt.addRow(String.valueOf(i.getCreditId()), client.getFirstName(), client.getLastName(), String.valueOf(i.getCreditType()), String.valueOf(i.getCreditAmount()), String.valueOf(i.getCreditTakenTime()), String.valueOf(i.getCreditPaymentTime()), String.valueOf(i.getMonthlyPayment()), String.valueOf(i.getLeftMonths()));
            }
            System.out.println(pt);
        } else {
            System.out.println("\n\tThere is no closed credits !");
            return;
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

    public static int calculatingTotalCash(Form form) {
        int creditRate = CreditTypes.getCreditTypes().get(form.getLoanType() - 1).getInterestRate();
        int rateAmount = (int)(form.getReceiveCash() * creditRate) / 100;
        return form.getReceiveCash() + rateAmount;
    }

    public static int gettingLeftMonths(Form form) {
        Date currentDate = form.getInitialDate();
        Date requestDate = form.getRequestedPeriod();
        TimeUnit tu = TimeUnit.DAYS;
        long time = requestDate.getTime() - currentDate.getTime();
        int month = (int) (tu.convert(time, TimeUnit.MILLISECONDS) / 30);
        return month;
    }

    public static int calculatingMonthCash(Form form) {
        int month = gettingLeftMonths(form);
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.##", symbols);
        df.setRoundingMode(RoundingMode.UP);
        int cash = Integer.parseInt(df.format(calculatingTotalCash(form) / month)) + 1;
        return cash;
    }
}
