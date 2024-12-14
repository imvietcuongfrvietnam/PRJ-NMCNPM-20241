package myapp.dao;

import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.ContributionFund;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Lớp ContributionFundDAO cung cấp các phương thức để tương tác với cơ sở dữ liệu liên quan đến quỹ đóng góp.
 */
public class ContributionFundDAO extends BaseDAO {

    /**
     * Xóa một quỹ đóng góp dựa trên ID.
     * Nếu có lỗi xảy ra, phương thức sẽ gọi phương thức trong lớp cha để hiển thị thông báo lỗi.
     *
     * @param ID ID của quỹ đóng góp cần xóa.
     */
    public static void deleteByID(int ID) {
        String query = "DELETE FROM bangquydonggop WHERE ID = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, ID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gọi phương thức trong lớp cha để hiển thị thông báo lỗi
            showErrorAlert("Lỗi Xóa Quỹ", "Không thể xóa quỹ đóng góp", "Có lỗi xảy ra khi xóa quỹ đóng góp: " + e.getMessage());
        } catch (Exception e) {
            showErrorAlert("Lỗi Không Xác Định", "Lỗi không xác định", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    /**
     * Cập nhật thông tin quỹ đóng góp dựa trên mã quỹ.
     * Nếu có lỗi xảy ra, phương thức sẽ gọi phương thức trong lớp cha để hiển thị thông báo lỗi.
     *
     * @param entity Đối tượng ContributionFund chứa thông tin cập nhật.
     */
    public static void updateByMaQuy(ContributionFund entity) {
        String query = "UPDATE quydonggop SET TenQuy = ?, SoTien = ?, DotThuPhi = ? WHERE MaQuy = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getFundName());
            preparedStatement.setInt(2, entity.getFundID());
            preparedStatement.setString(3, entity.getAmount());
            preparedStatement.setDate(4, entity.getPeriodOfTime());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gọi phương thức trong lớp cha để hiển thị thông báo lỗi
            showErrorAlert("Lỗi Cập Nhật Quỹ", "Không thể cập nhật quỹ đóng góp", "Có lỗi xảy ra khi cập nhật quỹ đóng góp: " + e.getMessage());
        } catch (Exception e) {
            showErrorAlert("Lỗi Không Xác Định", "Lỗi không xác định", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    /**
     * Chèn một bản ghi quỹ đóng góp mới vào cơ sở dữ liệu.
     * Nếu có lỗi xảy ra, phương thức sẽ gọi phương thức trong lớp cha để hiển thị thông báo lỗi.
     *
     * @param id ID của quỹ đóng góp.
     * @param tenQuy Tên của quỹ đóng góp.
     * @param moTa Mô tả của quỹ đóng góp.
     */
    public static void insertContributionFund(int id, String tenQuy, String moTa, Date start, Date finish) {
        String query = "INSERT INTO bangquydonggop (ID, TenQuy, MoTa, ) VALUES (?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, tenQuy);
            preparedStatement.setString(3, moTa);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gọi phương thức trong lớp cha để hiển thị thông báo lỗi
            showErrorAlert("Lỗi Chèn Quỹ", "Không thể chèn quỹ đóng góp", "Có lỗi xảy ra khi chèn quỹ đóng góp: " + e.getMessage());
        } catch (Exception e) {
            showErrorAlert("Lỗi Không Xác Định", "Lỗi không xác định", "Có lỗi xảy ra: " + e.getMessage());
        }
    }
}
