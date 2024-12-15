package myapp.dao;

import javafx.scene.control.Alert;
import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.PaymentHistory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp Data Access Object (DAO) để quản lý các thực thể PaymentHistory.
 */
public class PaymentHistoryDAO {

    /**
     * Hiển thị thông báo lỗi khi gặp ngoại lệ.
     *
     * @param title   Tiêu đề của thông báo.
     * @param header  Tiêu đề nhỏ (có thể là null).
     * @param content Nội dung thông báo.
     */
    private static void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Tính tổng số tiền đã đóng cho tất cả các hồ sơ thanh toán trong cơ sở dữ liệu.
     *
     * @return Tổng số tiền đã đóng dưới dạng BigDecimal,
     *         trả về BigDecimal.ZERO nếu không có hồ sơ nào hoặc xảy ra lỗi.
     */
    public static BigDecimal getTotalFee() {
        String query = "SELECT SUM(SoTienDaDong) AS TotalFee FROM lichsuthuphi";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getBigDecimal("TotalFee");
            }
        } catch (SQLException e) {
            showErrorAlert("Database Error", "Cannot Retrieve Total Fee",
                    "An error occurred while calculating the total fee.\n" + e.getMessage());
        }
        return BigDecimal.ZERO;
    }

    /**
     * Tính tổng số tiền đã đóng cho một loại phí cụ thể.
     *
     * @param feeType Loại phí cần tính tổng (ví dụ: "Phí Quản Lý").
     * @return Tổng số tiền đã đóng cho loại phí chỉ định dưới dạng BigDecimal,
     *         trả về BigDecimal.ZERO nếu không có hồ sơ phù hợp hoặc xảy ra lỗi.
     */
    public static BigDecimal getTotalFeeByType(String feeType) {
        String query = "SELECT SUM(SoTienDaDong) AS TotalFee FROM lichsuthuphi WHERE LoaiPhiThanhToan = ?";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, feeType);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal("TotalFee");
                }
            }
        } catch (SQLException e) {
            showErrorAlert("Database Error", "Cannot Retrieve Total Fee by Type",
                    "An error occurred while calculating the total fee for the specified type.\n" + e.getMessage());
        }
        return BigDecimal.ZERO;
    }

    /**
     * Lấy danh sách tất cả các hồ sơ thanh toán từ cơ sở dữ liệu.
     *
     * @return Danh sách các đối tượng PaymentHistory, hoặc danh sách rỗng nếu không có dữ liệu hoặc xảy ra lỗi.
     */
    public static List<PaymentHistory> getAllPaymentRecords() {
        List<PaymentHistory> paymentHistories = new ArrayList<>();
        String query = "SELECT * FROM lichsuthuphi";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                PaymentHistory history = new PaymentHistory(
                        resultSet.getString("MaHoGiaDinh"),
                        resultSet.getBigDecimal("SoTienDaDong"),
                        resultSet.getString("LoaiPhiThanhToan"),
                        resultSet.getDate("NgayDong")
                );
                paymentHistories.add(history);
            }
        } catch (SQLException e) {
            showErrorAlert("Database Error", "Cannot Retrieve Payment Records",
                    "An error occurred while retrieving payment records.\n" + e.getMessage());
        }
        return paymentHistories;
    }

    public static Number getTotalFeeByTypeAndQuarter(String phíDịchVụ, int quarter, int year) {
        String query = "SELECT COUNT(*) FROM lichsuthuphi WHERE LoaiPhiThanhToan = ? AND YEAR(NgayDong) = ? AND QUARTER(NgayDong) = ?";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Gán giá trị cho các tham số truy vấn
            statement.setString(1, phíDịchVụ);
            statement.setInt(2, year);
            statement.setInt(3, quarter);

            // Thực thi truy vấn
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

}
