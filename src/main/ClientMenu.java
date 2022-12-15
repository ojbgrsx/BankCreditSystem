package main;

import lombok.Getter;
import lombok.Setter;
import methods.ClientMethods;
import model.Client;
import model.Worker;
import services.*;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

@Getter
@Setter
public class ClientMenu {
    private static final Connection CONNECTION = ConnectionDBService.getConnection();

    public static void run(Client client) {
        Scanner scan = new Scanner(System.in);
        ChangeService changing = new ChangeService(client);
//        PrettyTable table = new PrettyTable("Client ID", "Credit ID", "First Name", "Last Name", "Credit Date");

        System.out.println("HELLO DEAR " + client.getFirstName().toUpperCase() + " " + client.getLastName().toUpperCase() + "\n");

        while (true) {
            System.out.print("""
                    1. Types of loans issued by the bank
                    2. Interest rates
                    3. Conditions for issuing a loan
                    4. Application form for obtaining a loan (for individuals)
                    5. Personal account:
                    6. Back to main menu
                    7. Exit
                    Choose the options (1-7):""");
            char menuOption = scan.next().charAt(0);
            if (menuOption == '1'){
                ClientMethods.firstOption();
            }else if(menuOption == '2'){
                ClientMethods.secondOption();
            } else if(menuOption == '3') {
                ClientMethods.thirdOption();
            }
            else if(menuOption == '4'){

            }
            else if (menuOption == '5') {
                ClientMethods.fifthOption(changing,client);
            } else if (menuOption == '6') {
                Main.run();
            } else if (menuOption == '7') {
                System.out.println("\nДавай пользователь, пока!");
                System.exit(0);
            }
        }
    }
}
