package myapp.model.communicatedb.delete;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Resident;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ResidentDelete {
    public static void delete(String IDcard) {
                String query = "DELETE FROM cudan WHERE CCCD = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, IDcard);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void deleteResident(Resident resident) {
        ResidentDelete.delete(resident.getIDcard());
    }
}
