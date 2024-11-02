package myapp.model.communicatedb.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class HoaDonNuocInsert {
    public void insert(String maKH, String maHD, Date ngayHetHan, String thongTinBoSung, double tienNuoc) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "INSERT INTO hoadonnuoc (MaKH, MaHD, NgayHetHan, ThongTinBoSung, TienNuoc) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maKH);
            preparedStatement.setString(2, maHD);
            preparedStatement.setDate(3, ngayHetHan);
            preparedStatement.setString(4, thongTinBoSung);
            preparedStatement.setDouble(5, tienNuoc);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
