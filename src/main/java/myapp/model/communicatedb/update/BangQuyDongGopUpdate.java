package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.BangQuyDongGop;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BangQuyDongGopUpdate implements Updater<BangQuyDongGop> {
    @Override
    public void update(BangQuyDongGop entity) {
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
