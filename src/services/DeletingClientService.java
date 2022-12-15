package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

public class DeletingClientService {
    private static final Connection CONNECTION = Objects.requireNonNull(ConnectionDBService.getConnection());

    public static void deleteClient(int id) {
        String delete;
        delete = String.format("DELETE FROM client WHERE clientId = %d", id);
        try {
            PreparedStatement ps = CONNECTION.prepareStatement(delete);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("We don't have user with this ID, please try again");
        }
    }
}
