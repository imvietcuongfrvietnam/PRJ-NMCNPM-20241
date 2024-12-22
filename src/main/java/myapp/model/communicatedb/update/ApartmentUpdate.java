package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Apartment;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ApartmentUpdate {
    public void update(Apartment apartment) {
        String query = "UPDATE phong SET Tang = ?, DienTich = ?, TinhTrang = ?, ThongTinBoSung = ? WHERE ID = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, apartment.getFloor());
            preparedStatement.setInt(2, apartment.getArea());
            preparedStatement.setString(3, apartment.getStatus());
            preparedStatement.setString(4, apartment.getNote());
            preparedStatement.setString(5, apartment.getApartmentID());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
