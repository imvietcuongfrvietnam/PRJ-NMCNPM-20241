package myapp.dao;

import javafx.scene.control.Alert;
import myapp.db.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Lớp này thực hiện các thao tác với bảng giadichvuguixe và phiguixe trong cơ sở dữ liệu,
 * bao gồm việc xóa và thêm phí gửi xe.
 */
public class ParkingFeeDAO {

    /**
     * Xóa phí gửi xe dựa trên loại xe.
     *
     * @param loaiXe Loại xe cần xóa.
     */
    public static void deleteByLoaiXe(String loaiXe) {
        String query = "DELETE FROM giadichvuguixe WHERE LoaiXe = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, loaiXe);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Bắt ngoại lệ SQLException
            BaseDAO.showErrorAlert("Lỗi Xóa Phí Gửi Xe", "Không thể xóa phí gửi xe", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            // Bắt các ngoại lệ chung khác
            BaseDAO.showErrorAlert("Lỗi Xóa Phí Gửi Xe", "Không thể xóa phí gửi xe", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    /**
     * Thêm phí gửi xe mới vào cơ sở dữ liệu.
     *
     * @param loaiXe         Loại xe.
     * @param giaGuiMotThang Giá gửi một tháng của xe.
     */
    public static void insertParkingFee(String loaiXe, int giaGuiMotThang) {
        String query = "INSERT INTO phiguixe (LoaiXe, GiaGuiMotThang) VALUES (?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, loaiXe);
            preparedStatement.setInt(2, giaGuiMotThang);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Bắt ngoại lệ SQLException
            BaseDAO.showErrorAlert("Lỗi Thêm Phí Gửi Xe", "Không thể thêm phí gửi xe", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            // Bắt các ngoại lệ chung khác
            BaseDAO.showErrorAlert("Lỗi Thêm Phí Gửi Xe", "Không thể thêm phí gửi xe", "Có lỗi xảy ra: " + e.getMessage());
        }
    }


}
