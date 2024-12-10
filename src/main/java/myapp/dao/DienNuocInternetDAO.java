package myapp.dao;

import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.DienNuocInternet;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DienNuocInternetDAO {
    public static void deleteByMaKHDien(String maKHDien) {
                String query = "DELETE FROM diennuocinternet WHERE MaKHDien = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maKHDien);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertDienNuocInternet(String maHoGiaDinh, String maKHDien, String maKHNuoc, String thongTinBoSung, String maKHInternet, String nccDien, String nccNuoc, String nccInternet) {
        String query = "INSERT INTO diennuocinternet (MaHoGiaDinh, MaKHDien, MaKHNuoc, ThongTinBoSung, MaKHInternet, NCCDien, NCCNuoc, NCCInternet) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
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

    public static void updateByMaHoGiaDinh(DienNuocInternet entity) {
                String query = "UPDATE diennuocinternet SET NCCDien = ?, NCCNuoc = ?, NCCInternet = ? WHERE MaHoGiaDinh = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getNCCDien());
            preparedStatement.setString(2, entity.getNCCNuoc());
            preparedStatement.setString(3, entity.getNCCInternet());
            preparedStatement.setString(4, entity.getMaHoGiaDinh());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
