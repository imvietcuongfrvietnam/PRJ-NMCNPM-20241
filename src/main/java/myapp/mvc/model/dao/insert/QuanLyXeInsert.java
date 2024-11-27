package myapp.mvc.model.dao.insert;

import myapp.mvc.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QuanLyXeInsert {
    public void insert(String bienSo, String loaiXe, String tenChuXe, String trangThai) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "INSERT INTO quanlyxe (BienSo, LoaiXe, TenChuXe, TrangThai) VALUES (?, ?, ?, ?)";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bienSo);
            preparedStatement.setString(2, loaiXe);
            preparedStatement.setString(3, tenChuXe);
            preparedStatement.setString(4, trangThai);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
