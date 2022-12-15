package main;

import main.ClientMenu;
import main.WorkerMenu;
import model.Client;
import model.Worker;
import services.Authorization;
import services.RegistrationService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("WELCOME TO OUR APPLICATION !");
        run();
    }

    public static void run() {
        Scanner scan = new Scanner(System.in);
        System.out.println("To sign in or sign up choose options below:\n1) Client\n2) Worker\n3) Sign up\n4) Exit");
        System.out.print(":");
        String accountType = scan.nextLine();
        System.out.println();
        if (accountType.equals("1")) {
            Client client = Authorization.signInClient();
            if(client != null){
                ClientMenu.run(client);
            }else {
                System.out.println("\nIncorrect username or password, try again\n");
                run();
            }

        } else if (accountType.equals("2")) {
            Worker worker = Authorization.signInWorker();
            if(worker != null){
                WorkerMenu.run(worker);
            }
            else {
                System.out.println("\nIncorrect username or password, try again\n");
                run();
            }
        } else if (accountType.equals("3")) {
            System.out.print("Enter 'w' to sign up as worker, 'c' as client or 'e' exit from this menu: ");
            char ac = scan.next().charAt(0);
            while (true) {
                if (ac == 'c' || ac == 'w') {
                    RegistrationService.signUp(ac);
                    break;
                } else if (ac == 'e') {
                    run();
                } else {
                    System.out.println("Incorrect option, try again !");
                }
            }
            run();
        } else if (accountType.equals("4")) {
            System.out.println("Корушкончо урматтуу кардар !");
            System.exit(0);
        } else {
            System.out.println("Incorrect option !");
            run();
        }
    }
}
