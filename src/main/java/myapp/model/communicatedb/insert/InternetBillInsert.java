package myapp.model.communicatedb.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class InternetBillInsert {
    public void insert(String maHD, String maKH, double soTien, Date ngayHetHan, String thongTinBoSung) {
        String query = "INSERT INTO hoadoninternet (MaHD, MaKH, SoTien, NgayHetHan, ThongTinBoSung) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
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
