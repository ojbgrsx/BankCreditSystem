package services;

import lombok.Getter;
import model.Client;
import model.Worker;

import java.util.Scanner;
@Getter


public class Authorization {
    private static Scanner scan = new Scanner(System.in);
    public static Client signInClient() {
        Client clients = new Client();
        int count = 0;
        System.out.print("Enter username: ");
        String username = scan.nextLine();
        System.out.print("Enter password: ");
        String password = scan.nextLine();
        for (Client i : clients.getClients()) {
            if (i.getUsername().equals(username) && i.getPassword().equals(password)) {
                return i;
            }
        }
        return null;

    }

    public static Worker signInWorker() {
        Worker workers = new Worker();
        System.out.print("Enter username: ");
        String username = scan.nextLine();
        System.out.print("Enter password: ");
        String password = scan.nextLine();
        for (Worker i : workers.getWorkers()) {
            if (i.getUsername().equals(username) && i.getPassword().equals(password)) {
                return i;
            }
        }
        return null;
    }
}
