package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Apartment;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ApartmentUpdate implements Updater<Apartment> {
    @Override
    public void update(Apartment entity) {
        String query = "UPDATE phong SET Tang = ?, DienTich = ?, TinhTrang = ?, ThongTinBoSung = ? WHERE ID = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, entity.getFloor());
            preparedStatement.setInt(2, entity.getArea());
            preparedStatement.setString(3, entity.getStatus());
            preparedStatement.setString(4, entity.getNote());
            preparedStatement.setString(5, entity.getApartmentID());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
