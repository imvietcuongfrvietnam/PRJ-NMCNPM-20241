package myapp.dao;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ServicePriceDAO {
    public static void insertServicePrice(String tenDichVu, int id, double donGia, String thongTinBoSung) {
        String query = "INSERT INTO banggiadichvu (TenDichVu, ID, DonGia, ThongTinBoSung) VALUES (?, ?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, tenDichVu);
            preparedStatement.setInt(2, id);
            preparedStatement.setDouble(3, donGia);
            preparedStatement.setString(4, thongTinBoSung);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
