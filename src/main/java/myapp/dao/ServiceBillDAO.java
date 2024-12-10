package myapp.dao;

import myapp.db.SQLConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class ServiceBillDAO {
    public static void insertServiceBill(int maDichVu, String maHD, String maHoGiaDinh, double soTien, Date ngayHetHan, String thongTinBoSung) {

        String query = "INSERT INTO hoadondichvu (MaDichVu, MaHD, MaHoGiaDinh, SoTien, NgayHetHan, ThongTinBoSung) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, maDichVu);
            preparedStatement.setString(2, maHD);
            preparedStatement.setString(3, maHoGiaDinh);
            preparedStatement.setDouble(4, soTien);
            preparedStatement.setDate(5, ngayHetHan);
            preparedStatement.setString(6, thongTinBoSung);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
