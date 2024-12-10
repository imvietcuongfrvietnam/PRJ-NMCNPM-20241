package myapp.dao;

import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.ParkingBill;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class ParkingBillDAO {
    public static void delete(String maHD) {
                String query = "DELETE FROM hoadonguixe WHERE MaHD = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maHD);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertParkingBill(String maHD, double soTien, String bienSo, Date ngayHetHan, String thongTinBoSung) {
        String query = "INSERT INTO hoadonguixe (MaHD, SoTien, BienSo, NgayHetHan, ThongTinBoSung) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maHD);
            preparedStatement.setDouble(2, soTien);
            preparedStatement.setString(3, bienSo);
            preparedStatement.setDate(4, ngayHetHan);
            preparedStatement.setString(5, thongTinBoSung);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateByMaHD(ParkingBill entity) {

        String query = "UPDATE hoadonguixe SET SoTien = ?, NgayHetHan = ?, ThongTinBoSung = ? WHERE MaHD = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBigDecimal(1, entity.getSoTien());
            preparedStatement.setDate(2, Date.valueOf(entity.getNgayHetHan()));
            preparedStatement.setString(3, entity.getThongTinBoSung());
            preparedStatement.setString(4, entity.getMaHD());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
