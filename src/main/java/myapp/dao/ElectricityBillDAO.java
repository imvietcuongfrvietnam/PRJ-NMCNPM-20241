package myapp.dao;

import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.ElectricityBill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ElectricityBillDAO extends BaseDAO {

    /**
     * Xóa bản ghi hóa đơn điện dựa trên mã hóa đơn.
     * Nếu có lỗi xảy ra, phương thức sẽ gọi phương thức trong lớp cha để hiển thị thông báo lỗi.
     *
     * @param maHD Mã hóa đơn điện cần xóa.
     */
    public static void deleteByMaHD(String maHD) {
        String query = "DELETE FROM hoadondien WHERE MaHD = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maHD);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gọi phương thức trong lớp cha để hiển thị thông báo lỗi
            showErrorAlert("Lỗi Xóa Dữ Liệu", "Không thể xóa hóa đơn điện", "Có lỗi xảy ra khi xóa dữ liệu: " + e.getMessage());
        } catch (Exception e) {
            showErrorAlert("Lỗi Không Xác Định", "Lỗi không xác định", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    /**
     * Cập nhật thông tin hóa đơn điện dựa trên mã hóa đơn.
     * Nếu có lỗi xảy ra, phương thức sẽ gọi phương thức trong lớp cha để hiển thị thông báo lỗi.
     *
     * @param entity Đối tượng ElectricityBill chứa thông tin cập nhật.
     */
    public static void updateByMaHD(ElectricityBill entity) {
        String query = "UPDATE hoadondien SET ChiSoDienSuDung = ?, NgayHetHan = ?, TienDien = ?, ThongTinBoSung = ? WHERE MaHD = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBigDecimal(1, entity.getChiSoDienSuDung());
            preparedStatement.setDate(2, java.sql.Date.valueOf(entity.getNgayHetHan()));
            preparedStatement.setBigDecimal(3, entity.getTienDien());
            preparedStatement.setString(4, entity.getThongTinBoSung());
            preparedStatement.setString(5, entity.getMaHD());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gọi phương thức trong lớp cha để hiển thị thông báo lỗi
            showErrorAlert("Lỗi Cập Nhật Dữ Liệu", "Không thể cập nhật hóa đơn điện", "Có lỗi xảy ra khi cập nhật dữ liệu: " + e.getMessage());
        } catch (Exception e) {
            showErrorAlert("Lỗi Không Xác Định", "Lỗi không xác định", "Có lỗi xảy ra: " + e.getMessage());
        }
    }
}
