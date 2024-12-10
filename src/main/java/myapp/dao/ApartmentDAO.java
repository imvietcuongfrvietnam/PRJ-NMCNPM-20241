package myapp.dao;

import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.Apartment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Lớp ApartmentDAO cung cấp các phương thức để tương tác với cơ sở dữ liệu liên quan đến căn hộ.
 */
public class ApartmentDAO extends BaseDAO {

    /**
     * Cập nhật thông tin của một căn hộ trong cơ sở dữ liệu.
     * Nếu có lỗi xảy ra, phương thức sẽ gọi phương thức trong lớp cha để hiển thị thông báo lỗi.
     *
     * @param entity Đối tượng Apartment chứa thông tin cập nhật.
     */
    public static void updateApartment(Apartment entity) {
        String query = "UPDATE phong SET Tang = ?, DienTich = ?, TinhTrang = ?, ThongTinBoSung = ? WHERE ID = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, entity.getFloor());
            preparedStatement.setInt(2, entity.getArea());
            preparedStatement.setString(3, entity.getStatus());
            preparedStatement.setString(4, entity.getNote());
            preparedStatement.setString(5, entity.getApartmentID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gọi phương thức trong lớp cha để hiển thị thông báo lỗi
            showErrorAlert("Lỗi Cập Nhật", "Cập nhật căn hộ thất bại", "Có lỗi xảy ra khi cập nhật căn hộ: " + e.getMessage());
        } catch (Exception e) {
            showErrorAlert("Lỗi Không Xác Định", "Lỗi không xác định", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    /**
     * Chèn một bản ghi căn hộ mới vào cơ sở dữ liệu.
     * Nếu có lỗi xảy ra, phương thức sẽ gọi phương thức trong lớp cha để hiển thị thông báo lỗi.
     *
     * @param MaCanHo Mã căn hộ (ID) của căn hộ.
     * @param Tang Tầng của căn hộ.
     * @param DienTich Diện tích của căn hộ.
     * @param TinhTrang Tình trạng của căn hộ.
     * @param ThongTinBoSung Thông tin bổ sung về căn hộ.
     */
    public static void insertApartment(String MaCanHo, int Tang, int DienTich, String TinhTrang, String ThongTinBoSung) {
        String query = "INSERT INTO canho (MaCanHo, Tang, DienTich, TinhTrang, ThongTinBoSung) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, MaCanHo);
            preparedStatement.setInt(2, Tang);
            preparedStatement.setInt(3, DienTich);
            preparedStatement.setString(4, TinhTrang);
            preparedStatement.setString(5, ThongTinBoSung);

            preparedStatement.executeUpdate(); // Thực thi câu lệnh chèn
        } catch (SQLException e) {
            // Gọi phương thức trong lớp cha để hiển thị thông báo lỗi
            showErrorAlert("Lỗi Chèn Dữ Liệu", "Chèn căn hộ thất bại", "Có lỗi xảy ra khi chèn căn hộ: " + e.getMessage());
        } catch (Exception e) {
            showErrorAlert("Lỗi Không Xác Định", "Lỗi không xác định", "Có lỗi xảy ra: " + e.getMessage());
        }
    }
}
