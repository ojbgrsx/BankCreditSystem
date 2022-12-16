package main;

import lombok.Getter;
import lombok.Setter;
import methods.WorkerMethods;
import model.Client;
import model.Form;
import model.Worker;
import services.ChangeService;
import services.ConnectionDBService;
import services.PrettyTable;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

@Getter
@Setter

public class WorkerMenu {
    private static final Connection CONNECTION = ConnectionDBService.getConnection();

    public static void run(Worker worker) {
        Client clients = new Client();
        final List<Client> clientsList = Client.getClients();
        ChangeService changing = new ChangeService(worker);
        Scanner scan = new Scanner(System.in);
        System.out.println("HELLO DEAR ".toUpperCase() + worker.getFirstName().toUpperCase() + " " + worker.getLastName().toUpperCase() + "\n");

        while (true) {
            System.out.print("""
                    1. Show clients
                    2. Viewing and searching applications
                    3. View the list of borrowers
                    4. View the list of repaid loans
                    5. Personal account:
                    6. Back to main menu
                    7. Exit
                    Choose the options (1-7):""");
            char menuOption = scan.next().charAt(0);
            if (menuOption == '1') {
                WorkerMethods.firstOption(clientsList);
            } else if (menuOption == '2') {
                WorkerMethods.secondOption(Form.getForms(),clientsList);
            } else if (menuOption == '3') {
                
            } else if (menuOption == '4') {
                
            } else if (menuOption == '5') {
                WorkerMethods.fifthOption(changing);
            } else if (menuOption == '6') {
                Main.run();
            } else if (menuOption == '7') {
                System.out.println("\nДавай пользователь, пока!");
                System.exit(0);
            }
        }
    }
}
