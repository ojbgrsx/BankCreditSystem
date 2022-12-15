package services;

import lombok.Getter;
import model.Client;
import model.Worker;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter

public class RegistrationService {
//    public static final ArrayList<String> CLIENTS_USERNAME_LIST = Client.getClients().stream()
//            .map(Client::getUsername).collect(Collectors.toCollection(ArrayList::new));
//    public static final ArrayList<String> WORKERS_USERNAME_LIST = Worker.getWorkers().stream()
//            .map(Worker::getUsername).collect(Collectors.toCollection(ArrayList::new));
    private static final Connection CONNECTION = Objects.requireNonNull(ConnectionDBService.getConnection());

    public static void signUp(char accountType) {
        Scanner scan = new Scanner(System.in);
        System.out.print("\nEnter your name >>> ");
        String name = scan.nextLine();
        System.out.print("Enter your surname >>> ");
        String surname = scan.nextLine();
        System.out.print("Enter username >>> ");
        String username = scan.nextLine().toLowerCase();
        System.out.print("Enter password >>> ");
        String password = scan.nextLine();
        double cash = 0.0;
        String clientInsertion = null, workerInsertion = null, values;
        if (accountType == 'c') {
            System.out.print("Enter your cash: ");
            cash = scan.nextDouble();
            clientInsertion = String.format("INSERT INTO %s (clientFName,clientLName,clientUsername,clientPassword,clientCash)", "client");
        } else {
            workerInsertion = String.format("INSERT INTO %s (workerFName,workerLName,workerUsername,workerPassword)", "worker");
        }
        try {
            if (username.length() < 9 || password.length() < 9) {
                System.out.println("\n\tUsername's and password's length should be more than 8 symbols! ");
                signUp(accountType);
            } else if(Pattern.matches("[a-zA-Z]+", name) && Pattern.matches("[a-zA-Z]+", surname)){
                PreparedStatement ps;
                if (accountType == 'c') {
                    values = "VALUES (?,?,?,?,?)";
                    ps = CONNECTION.prepareStatement(clientInsertion + values);
                } else {
                    values = "VALUES (?,?,?,?)";
                    ps = CONNECTION.prepareStatement(workerInsertion + values);
                }
                ps.setString(1, StringUtils.capitalize(name.toLowerCase()));
                ps.setString(2, StringUtils.capitalize(surname.toLowerCase()));
                ps.setString(3, username);
                ps.setString(4, password);
                if (accountType == 'c'){
                    ps.setDouble(5,cash);
                }
                ps.executeUpdate();
            }
            else{
                System.out.println("\n\tName or surname should be only latin alphabetical,try again !");
                signUp(accountType);
            }
            if (accountType == 'c') {
                System.out.printf("\nYou've successfully signed up as %s !!!", "client");
            } else {
                System.out.printf("\nYou've successfully signed up as %s !!!", "worker");
            }
        } catch (Exception e) {
            System.out.println("\n\tThis username already exists, try another username !!!");
//            e.printStackTrace();
            signUp(accountType);
        }

        System.out.println();
    }
}
