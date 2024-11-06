package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Phong;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PhongUpdate implements Updater<Phong> {
    @Override
    public void update(Phong entity) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "UPDATE phong SET Tang = ?, DienTich = ?, TinhTrang = ?, ThongTinBoSung = ? WHERE ID = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, entity.getTang());
            preparedStatement.setInt(2, entity.getDienTich());
            preparedStatement.setString(3, entity.getTinhTrang());
            preparedStatement.setString(4, entity.getThongTinBoSung());
            preparedStatement.setString(5, entity.getID());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
