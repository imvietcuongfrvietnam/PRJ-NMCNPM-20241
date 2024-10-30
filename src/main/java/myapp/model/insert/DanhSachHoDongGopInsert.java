package myapp.model.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;

public class DanhSachHoDongGopInsert {
    public void insert(String maHoDongGop, int maQuy, double soTienDongGop, String thongTinBoSung, String dotDongGop, Date ngayDongGop) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "INSERT INTO danhsachhodonggop (MaHoDongGop, MaQuy, SoTienDongGop, ThongTinBoSung, DotDongGop, NgayDongGop) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = connector.getConnection();
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
}
