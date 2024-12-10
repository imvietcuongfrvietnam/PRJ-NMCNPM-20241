package myapp.dao;

import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.HouseholdsContribute;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class HouseholdContributeDAO {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
