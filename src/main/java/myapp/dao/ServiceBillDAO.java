package myapp.dao;

import myapp.db.SQLConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class ServiceBillDAO {
    public static void insertServiceBill(int maHD, String maHoGiaDinh, double soTien, Date ngayHetHan, String trangThai, String thongTinBoSung) {

        String query = "INSERT INTO phidichvu (ID, MaHoGiaDinh, SoTien, NgayHetHan, TrangThai, ThongTinBoSung) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, maHD);
            preparedStatement.setString(2, maHoGiaDinh);
            preparedStatement.setDouble(3, soTien);
            preparedStatement.setDate(4, ngayHetHan);
            preparedStatement.setString(5, trangThai);
            preparedStatement.setString(6, thongTinBoSung);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Thêm Phí Gửi Xe", "Không thể thêm phí gửi xe", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Thêm Phí Gửi Xe", "Không thể thêm phí gửi xe", "Có lỗi xảy ra: " + e.getMessage());
        }

    }
}
