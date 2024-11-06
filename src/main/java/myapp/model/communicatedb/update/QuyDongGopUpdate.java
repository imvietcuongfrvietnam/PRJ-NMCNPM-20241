package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.QuyDongGop;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QuyDongGopUpdate implements Updater<QuyDongGop> {
    @Override
    public void update(QuyDongGop entity) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "UPDATE bangquydonggop SET TenQuy = ?, MoTa = ? WHERE ID = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getTenQuy());
            preparedStatement.setString(2, entity.getMoTa());
            preparedStatement.setInt(3, entity.getId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
