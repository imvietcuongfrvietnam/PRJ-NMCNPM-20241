package myapp.dao;

import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.ParkingBill;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Lớp này thực hiện các thao tác với bảng hoadonguixe trong cơ sở dữ liệu,
 * bao gồm việc thêm, xóa và cập nhật thông tin hóa đơn đỗ xe.
 */
public class ParkingBillDAO {

    /**
     * Xóa hóa đơn đỗ xe dựa trên mã hóa đơn.
     *
     * @param maHD Mã hóa đơn cần xóa.
     */
    public static void delete(String maHD) {
        String query = "DELETE FROM hoadonguixe WHERE MaHD = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maHD);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gọi phương thức trong lớp cha để hiển thị thông báo lỗi
            BaseDAO.showErrorAlert("Lỗi Chèn Quỹ", "Không thể chèn quỹ đóng góp", "Có lỗi xảy ra khi chèn quỹ đóng góp: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Không Xác Định", "Lỗi không xác định", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    /**
     * Thêm một hóa đơn đỗ xe mới vào cơ sở dữ liệu.
     *
     * @param maHD        Mã hóa đơn.
     * @param soTien      Số tiền của hóa đơn.
     * @param bienSo      Biển số xe.
     * @param ngayHetHan  Ngày hết hạn của hóa đơn.
     * @param thongTinBoSung Thông tin bổ sung cho hóa đơn.
     */
    public static void insertParkingBill(String maHD, double soTien, String bienSo, Date ngayHetHan, String thongTinBoSung) {
        String query = "INSERT INTO hoadonguixe (MaHD, SoTien, BienSo, NgayHetHan, ThongTinBoSung) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maHD);
            preparedStatement.setDouble(2, soTien);
            preparedStatement.setString(3, bienSo);
            preparedStatement.setDate(4, ngayHetHan);
            preparedStatement.setString(5, thongTinBoSung);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi thêm hóa đơn: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Lỗi không xác định: " + e.getMessage());
        }
    }

    /**
     * Cập nhật thông tin hóa đơn đỗ xe dựa trên mã hóa đơn.
     *
     * @param entity Đối tượng chứa thông tin hóa đơn cần cập nhật.
     */
    public static void updateByMaHD(ParkingBill entity) {
        String query = "UPDATE hoadonguixe SET SoTien = ?, NgayHetHan = ?, ThongTinBoSung = ? WHERE MaHD = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBigDecimal(1, entity.getSoTien());
            preparedStatement.setDate(2, Date.valueOf(entity.getNgayHetHan()));
            preparedStatement.setString(3, entity.getThongTinBoSung());
            preparedStatement.setString(4, entity.getMaHD());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gọi phương thức trong lớp cha để hiển thị thông báo lỗi
            BaseDAO.showErrorAlert("Lỗi Chèn Quỹ", "Không thể chèn quỹ đóng góp", "Có lỗi xảy ra khi chèn quỹ đóng góp: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Không Xác Định", "Lỗi không xác định", "Có lỗi xảy ra: " + e.getMessage());
        }
    }
}
