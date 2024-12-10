package myapp.dao;

import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.Apartment;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ApartmentDAO {
    public static void updateApartment(Apartment entity) {
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

    public static void insertApartment(String MaCanHo, int Tang, int DienTich, String TinhTrang, String ThongTinBoSung) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO canho (MaCanHo, Tang, DienTich, TinhTrang, ThongTinBoSung) VALUES (?, ?, ?, ?, ?)";

        try {
            connection = SQLConnector.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, MaCanHo);
            preparedStatement.setInt(2, Tang);
            preparedStatement.setInt(3, DienTich);
            preparedStatement.setString(4, TinhTrang);
            preparedStatement.setString(5, ThongTinBoSung);

            preparedStatement.executeUpdate(); // Execute the insert statement
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception stack trace for debugging
        } finally {
            // Close resources to avoid memory leaks
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
