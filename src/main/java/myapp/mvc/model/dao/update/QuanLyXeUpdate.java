package myapp.mvc.model.dao.update;

import myapp.mvc.model.connectdb.SQLConnector;
import myapp.mvc.model.entities.entitiesdb.QuanLyXe;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QuanLyXeUpdate implements Updater<QuanLyXe> {
    @Override
    public void update(QuanLyXe entity) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "UPDATE quanlyxe SET LoaiPhuongTien = ?, NgayBatDau = ?, NgayKetThuc = ? WHERE BienSo = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getLoaiPhuongTien());
            preparedStatement.setDate(2, java.sql.Date.valueOf(entity.getNgayBatDau()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(entity.getNgayKetThuc()));
            preparedStatement.setString(4, entity.getBienSo());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
