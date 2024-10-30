package myapp.model.communicatedb.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class HoaDonGuiXeInsert {
    public void insert(String maHD, double soTien, String bienSo, Date ngayHetHan, String thongTinBoSung) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "INSERT INTO hoadonguixe (MaHD, SoTien, BienSo, NgayHetHan, ThongTinBoSung) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maHD);
            preparedStatement.setDouble(2, soTien);
            preparedStatement.setString(3, bienSo);
            preparedStatement.setDate(4, ngayHetHan);
            preparedStatement.setString(5, thongTinBoSung);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
