package myapp.dao;

import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.DienNuocInternet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DienNuocInternetDAO extends BaseDAO {

    /**
     * Xóa bản ghi diennuocinternet dựa trên mã khách hàng điện.
     * Nếu có lỗi xảy ra, phương thức sẽ gọi phương thức trong lớp cha để hiển thị thông báo lỗi.
     *
     * @param maKHDien Mã khách hàng điện cần xóa.
     */
    public static void deleteByMaKHDien(String maKHDien) {
        String query = "DELETE FROM diennuocinternet WHERE MaKHDien = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maKHDien);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gọi phương thức trong lớp cha để hiển thị thông báo lỗi
            showErrorAlert("Lỗi Xóa Dữ Liệu", "Không thể xóa dữ liệu điện nước internet", "Có lỗi xảy ra khi xóa dữ liệu: " + e.getMessage());
        } catch (Exception e) {
            showErrorAlert("Lỗi Không Xác Định", "Lỗi không xác định", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    /**
     * Chèn một bản ghi dữ liệu diennuocinternet vào cơ sở dữ liệu.
     * Nếu có lỗi xảy ra, phương thức sẽ gọi phương thức trong lớp cha để hiển thị thông báo lỗi.
     *
     * @param maHoGiaDinh Mã hộ gia đình.
     * @param maKHDien Mã khách hàng điện.
     * @param maKHNuoc Mã khách hàng nước.
     * @param thongTinBoSung Thông tin bổ sung.
     * @param maKHInternet Mã khách hàng internet.
     * @param nccDien Nhà cung cấp điện.
     * @param nccNuoc Nhà cung cấp nước.
     * @param nccInternet Nhà cung cấp internet.
     */
    public static void insertDienNuocInternet(String maHoGiaDinh, String maKHDien, String maKHNuoc, String thongTinBoSung, String maKHInternet, String nccDien, String nccNuoc, String nccInternet) {
        String query = "INSERT INTO diennuocinternet (MaHoGiaDinh, MaKHDien, MaKHNuoc, ThongTinBoSung, MaKHInternet, NCCDien, NCCNuoc, NCCInternet) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maHoGiaDinh);
            preparedStatement.setString(2, maKHDien);
            preparedStatement.setString(3, maKHNuoc);
            preparedStatement.setString(4, thongTinBoSung);
            preparedStatement.setString(5, maKHInternet);
            preparedStatement.setString(6, nccDien);
            preparedStatement.setString(7, nccNuoc);
            preparedStatement.setString(8, nccInternet);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gọi phương thức trong lớp cha để hiển thị thông báo lỗi
            showErrorAlert("Lỗi Chèn Dữ Liệu", "Không thể chèn dữ liệu điện nước internet", "Có lỗi xảy ra khi chèn dữ liệu: " + e.getMessage());
        } catch (Exception e) {
            showErrorAlert("Lỗi Không Xác Định", "Lỗi không xác định", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    /**
     * Cập nhật thông tin điện, nước, internet dựa trên mã hộ gia đình.
     * Nếu có lỗi xảy ra, phương thức sẽ gọi phương thức trong lớp cha để hiển thị thông báo lỗi.
     *
     * @param entity Đối tượng DienNuocInternet chứa thông tin cập nhật.
     */
    public static void updateByMaHoGiaDinh(DienNuocInternet entity) {
        String query = "UPDATE diennuocinternet SET NCCDien = ?, NCCNuoc = ?, NCCInternet = ? WHERE MaHoGiaDinh = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getNCCDien());
            preparedStatement.setString(2, entity.getNCCNuoc());
            preparedStatement.setString(3, entity.getNCCInternet());
            preparedStatement.setString(4, entity.getMaHoGiaDinh());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gọi phương thức trong lớp cha để hiển thị thông báo lỗi
            showErrorAlert("Lỗi Cập Nhật Dữ Liệu", "Không thể cập nhật dữ liệu điện nước internet", "Có lỗi xảy ra khi cập nhật dữ liệu: " + e.getMessage());
        } catch (Exception e) {
            showErrorAlert("Lỗi Không Xác Định", "Lỗi không xác định", "Có lỗi xảy ra: " + e.getMessage());
        }
    }
}
