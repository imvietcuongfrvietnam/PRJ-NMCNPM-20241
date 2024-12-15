package myapp.dao;

import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import myapp.db.SQLConnector;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        String query = "INSERT INTO giadichvuguixe (LoaiXe, GiaGuiMotThang) VALUES (?, ?)";

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
    public static void updateParkingFee(String loaiXe, int giaGui) {
        String query = "UPDATE giadichvuguixe SET GiaGuiMotThang = ? WHERE LoaiXe = ?";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Gán giá trị cho các tham số truy vấn
            statement.setInt(1, giaGui);
            statement.setString(2, loaiXe);

            // Thực thi truy vấn
            int rowsAffected = statement.executeUpdate();

            // Kiểm tra xem có hàng nào bị cập nhật
            if (rowsAffected > 0) {
                System.out.println("Cập nhật phí gửi xe thành công!");
            } else {
                System.out.println("Không có loại xe nào được cập nhật.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật phí gửi xe: " + e.getMessage(), e);
        }
    }
    public static BigDecimal getFeeByType(String loaiXe) {
        String query = "SELECT GiaGuiMotThang FROM giadichvuguixe WHERE LoaiXe = ?";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Gán giá trị cho tham số truy vấn
            preparedStatement.setString(1, loaiXe);

            // Thực thi truy vấn
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Lấy giá trị GiaGuiMotThang và trả về dưới dạng BigDecimal
                    return resultSet.getBigDecimal("GiaGuiMotThang");
                }
            }
        } catch (SQLException e) {
            // Xử lý lỗi SQL
            throw new RuntimeException("Lỗi khi lấy phí gửi xe cho loại xe: " + loaiXe, e);
        }
        return BigDecimal.ZERO; // Trả về BigDecimal.ZERO nếu không tìm thấy giá trị
    }


}
