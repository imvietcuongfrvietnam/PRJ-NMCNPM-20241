package myapp.dao;

import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.ElectricityBill;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ElectricityBillDAO {
    public static void deleteByMaHD(String maHD) {
        String query = "DELETE FROM hoadondien WHERE MaHD = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maHD);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateByMaHD(ElectricityBill entity) {
        String query = "UPDATE hoadondien SET ChiSoDienSuDung = ?, NgayHetHan = ?, TienDien = ?, ThongTinBoSung = ? WHERE MaHD = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBigDecimal(1, entity.getChiSoDienSuDung());
            preparedStatement.setDate(2, java.sql.Date.valueOf(entity.getNgayHetHan()));
            preparedStatement.setBigDecimal(3, entity.getTienDien());
            preparedStatement.setString(4, entity.getThongTinBoSung());
            preparedStatement.setString(5, entity.getMaHD());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
