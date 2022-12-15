package services;

import lombok.Setter;
import model.Client;
import model.Worker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

@Setter
public class ChangeService {
    private static final Connection CONNECTION = Objects.requireNonNull(ConnectionDBService.getConnection());

    private Object[] user = new Object[1];

    public ChangeService(Client user) {
        this.user[0] = user;
    }
    public ChangeService(Worker user){
        this.user[0] = user;
    }
    public void changeUsername(String username) {
        String set, update;
        if (user[0].getClass() == Client.class) {
            Client client = (Client) user[0];
            set = String.format("UPDATE client SET clientUsername = '%s' ", username);
            update = String.format("WHERE clientId = %d", client.getId());
        } else {
            Worker worker = ((Worker) user[0]);
            set = String.format("UPDATE worker SET workerUsername = '%s' ", username);
            update = String.format("WHERE workerId = %d", worker.getId());
        }
        try {
            PreparedStatement ps = CONNECTION.prepareStatement(set + update);
            ps.executeUpdate();
            System.out.println("\n\tYou have successfully changed your username !\n");
        } catch (Exception e) {
            System.out.println("\n\tUsername has already exists ,TRY AGAIN !\n");
        }
    }

    public void changePassword(String pass) {
        String password;
        if (user[0].getClass() == Client.class) {
            Client client = (Client) user[0];
            password = String.format("UPDATE client SET clientPassword = '%s' WHERE clientId = %d", pass,client.getId());

        }else{
            Worker worker = (Worker) user[0];
            password = String.format("UPDATE worker SET workerPassword = '%s' WHERE workerId = %d", pass,worker.getId());
        }
        try {
            if(pass.length()>7){
                PreparedStatement ps = CONNECTION.prepareStatement(password);
                ps.executeUpdate();
            }
            else {
                throw new Exception();
            }
            System.out.println("\n\tYou have successfully changed your password !\n");

        } catch (Exception e) {
            System.out.println("\n\tPassword length should be more than 8 symbols.\n");
        }
    }

}
