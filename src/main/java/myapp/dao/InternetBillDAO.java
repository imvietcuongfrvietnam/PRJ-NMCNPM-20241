package myapp.dao;

import myapp.db.SQLConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Lớp này thực hiện các thao tác với bảng hoadoninternet trong cơ sở dữ liệu,
 * bao gồm việc thêm và xóa hóa đơn.
 */
public class InternetBillDAO {

    /**
     * Thêm một hóa đơn internet mới vào cơ sở dữ liệu.
     *
     * @param maHD        Mã hóa đơn.
     * @param maKH        Mã khách hàng.
     * @param soTien      Số tiền của hóa đơn.
     * @param ngayHetHan  Ngày hết hạn của hóa đơn.
     * @param thongTinBoSung Thông tin bổ sung cho hóa đơn.
     */
    public static void insertBill(String maHD, String maKH, double soTien, Date ngayHetHan, String thongTinBoSung) {
        String query = "INSERT INTO hoadoninternet (MaHD, MaKH, SoTien, NgayHetHan, ThongTinBoSung) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maHD);
            preparedStatement.setString(2, maKH);
            preparedStatement.setDouble(3, soTien);
            preparedStatement.setDate(4, ngayHetHan);
            preparedStatement.setString(5, thongTinBoSung);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi SQL", "Lỗi truy vấn cơ sở dữ liệu", "Có lỗi xảy ra trong khi thực hiện truy vấn SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Không Xác Định", "Lỗi không xác định", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    /**
     * Xóa hóa đơn internet dựa trên mã hóa đơn.
     *
     * @param maHD Mã hóa đơn cần xóa.
     */
    public static void deleteByMaHD(String maHD) {
        String query = "DELETE FROM hoadoninternet WHERE MaHD = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maHD);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi SQL", "Lỗi truy vấn cơ sở dữ liệu", "Có lỗi xảy ra trong khi thực hiện truy vấn SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Không Xác Định", "Lỗi không xác định", "Có lỗi xảy ra: " + e.getMessage());
        }
    }



}
