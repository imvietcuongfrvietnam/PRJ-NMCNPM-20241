package myapp.dao;

import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.HouseHold;
import javafx.scene.control.Alert;

import java.sql.*;

/**
 * Lớp này cung cấp các phương thức để thao tác với bảng "hogiadinh" trong cơ sở dữ liệu.
 * Các phương thức bao gồm thêm, xóa, cập nhật và lấy thông tin hộ gia đình theo mã căn hộ.
 *
 * @author [Your Name]
 */
public class HouseholdDAO extends BaseDAO {

    /**
     * Xóa hộ gia đình theo mã hộ gia đình.
     *
     * @param maHoGiaDinh Mã hộ gia đình cần xóa.
     */
    public static void delete(String maHoGiaDinh) {
        String query = "DELETE FROM hogiadinh WHERE MaHoGiaDinh = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maHoGiaDinh);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            showErrorAlert("Lỗi khi xóa hộ gia đình", "Không thể xóa hộ gia đình", e.getMessage());
        }
    }

    /**
     * Lấy thông tin hộ gia đình theo mã căn hộ.
     *
     * @param apartmentID Mã căn hộ.
     * @return Đối tượng HouseHold chứa thông tin hộ gia đình.
     */
    public static HouseHold getHouseHoldByApartmentID(String apartmentID) {
        HouseHold houseHold = null;
        String query = "SELECT MaHoGiaDinh, NgayChuyenVao, NgayChuyenRa FROM hogiadinh WHERE MaCanHo = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, apartmentID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String houseHoldID = resultSet.getString("MaHoGiaDinh");
                Date moveInDate = resultSet.getDate("NgayChuyenVao");
                Date moveOutDate = resultSet.getDate("NgayChuyenRa");

                // Tạo đối tượng houseHold và gán giá trị cho nó
                houseHold = new HouseHold(houseHoldID, moveInDate, moveOutDate);
            }
        } catch (SQLException e) {
            showErrorAlert("Lỗi khi lấy thông tin hộ gia đình", "Không thể lấy thông tin hộ gia đình", e.getMessage());
        }

        return houseHold;
    }

    /**
     * Thêm hộ gia đình vào cơ sở dữ liệu.
     *
     * @param maHoGiaDinh Mã hộ gia đình.
     * @param maPhongThue Mã phòng thuê.
     * @param ngayChuyenVao Ngày chuyển vào.
     * @param soCMNDChuHo Số CMND của chủ hộ.
     * @param trangThai Trạng thái hộ gia đình.
     */
    public static void insertHousehold(String maHoGiaDinh, String maPhongThue, Date ngayChuyenVao, String soCMNDChuHo, String trangThai) {
        String sql = "INSERT INTO hogiadinh (maHoGiaDinh, maPhongThue, ngayChuyenVao, soCMNDChuHo, trangThai) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, maHoGiaDinh);
            preparedStatement.setString(2, maPhongThue);
            preparedStatement.setDate(3, new java.sql.Date(ngayChuyenVao.getTime()));
            preparedStatement.setString(4, soCMNDChuHo);
            preparedStatement.setString(5, trangThai);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            showErrorAlert("Lỗi khi thêm hộ gia đình", "Không thể thêm hộ gia đình", e.getMessage());
        }
    }

    /**
     * Cập nhật thông tin hộ gia đình theo mã hộ gia đình.
     *
     * @param entity Đối tượng HouseHold chứa thông tin mới.
     */
    public static void updateByMaHoGiaDinh(HouseHold entity) {
        String query = "UPDATE hogiadinh SET MaCanHo = ?, NgayChuyenVao = ?, NgayChuyenRa = ?, SoCMNDChuHo = ?, TrangThai = ? WHERE MaHoGiaDinh = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getApartmentID());
            preparedStatement.setDate(2, entity.getMoveInDate());
            preparedStatement.setDate(3, entity.getMoveOutDate());
            preparedStatement.setString(4, entity.getResidentID());
            preparedStatement.setString(5, entity.getStatus());
            preparedStatement.setString(6, entity.getHouseHoldID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            showErrorAlert("Lỗi khi cập nhật thông tin hộ gia đình", "Không thể cập nhật thông tin hộ gia đình", e.getMessage());
        }
    }
}
