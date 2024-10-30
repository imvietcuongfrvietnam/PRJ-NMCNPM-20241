package myapp.model.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class HoaDonInternetInsert {
    public void insert(String maHD, String maKH, double soTien, Date ngayHetHan, String thongTinBoSung) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "INSERT INTO hoadoninternet (MaHD, MaKH, SoTien, NgayHetHan, ThongTinBoSung) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maHD);
            preparedStatement.setString(2, maKH);
            preparedStatement.setDouble(3, soTien);
            preparedStatement.setDate(4, ngayHetHan);
            preparedStatement.setString(5, thongTinBoSung);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
