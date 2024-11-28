package myapp.mvc.model.dao.insert;

import myapp.mvc.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DienNuocInternetInsert {
    public void insert(String maHoGiaDinh, String maKHDien, String maKHNuoc, String thongTinBoSung, String maKHInternet, String nccDien, String nccNuoc, String nccInternet) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "INSERT INTO diennuocinternet (MaHoGiaDinh, MaKHDien, MaKHNuoc, ThongTinBoSung, MaKHInternet, NCCDien, NCCNuoc, NCCInternet) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = connector.getConnection();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
