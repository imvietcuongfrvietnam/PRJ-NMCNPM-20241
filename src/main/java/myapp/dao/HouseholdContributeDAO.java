package myapp.dao;

import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.HouseholdsContribute;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HouseholdContributeDAO extends BaseDAO {

    /**
     * Chèn một bản ghi vào bảng đóng góp hộ gia đình.
     * Nếu có lỗi xảy ra, phương thức sẽ gọi phương thức trong lớp cha để hiển thị thông báo lỗi.
     *
     * @param maHoDongGop Mã hộ đóng góp.
     * @param maQuy Mã quý.
     * @param soTienDongGop Số tiền đóng góp.
     * @param thongTinBoSung Thông tin bổ sung.
     * @param dotDongGop Đợt đóng góp.
     * @param ngayDongGop Ngày đóng góp.
     */
    public static void insertHouseholdContribute(String maHoDongGop, int maQuy, double soTienDongGop, String thongTinBoSung, String dotDongGop, Date ngayDongGop) {
        String query = "INSERT INTO danhsachhodonggop (MaHoDongGop, MaQuy, SoTienDongGop, ThongTinBoSung, DotDongGop, NgayDongGop) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maHoDongGop);
            preparedStatement.setInt(2, maQuy);
            preparedStatement.setDouble(3, soTienDongGop);
            preparedStatement.setString(4, thongTinBoSung);
            preparedStatement.setString(5, dotDongGop);
            preparedStatement.setDate(6, ngayDongGop);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gọi phương thức trong lớp cha để hiển thị thông báo lỗi
            showErrorAlert("Lỗi Chèn Dữ Liệu", "Không thể chèn đóng góp hộ gia đình", "Có lỗi xảy ra khi chèn dữ liệu: " + e.getMessage());
        } catch (Exception e) {
            showErrorAlert("Lỗi Không Xác Định", "Lỗi không xác định", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    /**
     * Cập nhật thông tin đóng góp hộ gia đình dựa trên mã hộ và mã quý.
     * Nếu có lỗi xảy ra, phương thức sẽ gọi phương thức trong lớp cha để hiển thị thông báo lỗi.
     *
     * @param entity Đối tượng HouseholdsContribute chứa thông tin cập nhật.
     */
    public static void updateByMaHoDongGopAndMaQuy(HouseholdsContribute entity) {
        String query = "UPDATE danhsachhodonggop SET SoTienDongGop = ?, ThongTinBoSung = ?, DotDongGop = ?, NgayDongGop = ? WHERE MaHoDongGop = ? AND MaQuy = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBigDecimal(1, entity.getSoTienDongGop());
            preparedStatement.setString(2, entity.getThongTinBoSung());
            preparedStatement.setString(3, entity.getDotDongGop());
            preparedStatement.setDate(4, Date.valueOf(entity.getNgayDongGop()));
            preparedStatement.setString(5, entity.getMaHoDongGop());
            preparedStatement.setInt(6, entity.getMaQuy());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gọi phương thức trong lớp cha để hiển thị thông báo lỗi
            showErrorAlert("Lỗi Cập Nhật Dữ Liệu", "Không thể cập nhật đóng góp hộ gia đình", "Có lỗi xảy ra khi cập nhật dữ liệu: " + e.getMessage());
        } catch (Exception e) {
            showErrorAlert("Lỗi Không Xác Định", "Lỗi không xác định", "Có lỗi xảy ra: " + e.getMessage());
        }
    }
}
