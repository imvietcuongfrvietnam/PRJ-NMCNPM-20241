package myapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.ContributionFund;
import myapp.model.entities.entitiesdb.Vehicle;

import java.math.BigDecimal;
import java.sql.*;

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
    public static void deleteByID(String ID) {
        String query = "DELETE FROM quydonggop WHERE MaQuy = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, ID);
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
            preparedStatement.setString(2, entity.getFundID());
            preparedStatement.setBigDecimal(3, entity.getAmount());
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
    public static void insertContributionFund(String id, String tenQuy, String moTa, Date start, Date finish, BigDecimal amout) {
        String query = "INSERT INTO quydonggop (MaQuy, TenQuy, MoTa,SoTien, NgayBatDau, NgayKetThuc) VALUES (?, ?, ?,?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, tenQuy);
            preparedStatement.setString(3, moTa);
            preparedStatement.setBigDecimal(4, amout);
            preparedStatement.setDate(5, start);
            preparedStatement.setDate(6, finish);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gọi phương thức trong lớp cha để hiển thị thông báo lỗi
            showErrorAlert("Lỗi Chèn Quỹ", "Không thể chèn quỹ đóng góp", "Có lỗi xảy ra khi chèn quỹ đóng góp: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            showErrorAlert("Lỗi Không Xác Định", "Lỗi không xác định", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    public static ObservableList<ContributionFund> getContributionFund() {
        ObservableList<ContributionFund> contributionFundsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM quydonggop";

        try (PreparedStatement statement = SQLConnector.getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // Lấy dữ liệu từ ResultSet
                String maQuy = resultSet.getString("MaQuy");
                String tenQuy = resultSet.getString("TenQuy");
                String moTa = resultSet.getString("MoTa");
                BigDecimal soTien = resultSet.getBigDecimal("SoTien");
                Date ngayBatDau = resultSet.getDate("NgayBatDau");
                Date ngayKetThuc = resultSet.getDate("NgayKetThuc");

                // Thêm vào danh sách ContributionFund
                contributionFundsList.add(new ContributionFund(tenQuy, maQuy, soTien, ngayBatDau, ngayKetThuc));
            }
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Không truy cập được danh sách quỹ", "Lỗi truy cập CSDL", "Không thể truy vấn CSDL lấy danh sách quỹ đóng góp");
        }
        return contributionFundsList;
    }

}
