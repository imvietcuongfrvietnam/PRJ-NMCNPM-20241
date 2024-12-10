package myapp.dao;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.WaterBill;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class WaterBillDAO {
    public static void insertWaterBill(String maKH, String maHD, Date ngayHetHan, String thongTinBoSung, double tienNuoc) {
        String query = "INSERT INTO hoadonnuoc (MaKH, MaHD, NgayHetHan, ThongTinBoSung, TienNuoc) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maKH);
            preparedStatement.setString(2, maHD);
            preparedStatement.setDate(3, ngayHetHan);
            preparedStatement.setString(4, thongTinBoSung);
            preparedStatement.setDouble(5, tienNuoc);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteByMaHD(String maHD) {
        String query = "DELETE FROM hoadonnuoc WHERE MaHD = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maHD);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateWaterBill(WaterBill entity) {
        String query = "UPDATE hoadonnuoc SET NgayHetHan = ?, TienNuoc = ? WHERE MaHD = ? AND MaKH = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDate(1, Date.valueOf(entity.getNgayHetHan()));
            preparedStatement.setBigDecimal(2, entity.getTienNuoc());
            preparedStatement.setString(3, entity.getMaHD());
            preparedStatement.setString(4, entity.getMaKH());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
