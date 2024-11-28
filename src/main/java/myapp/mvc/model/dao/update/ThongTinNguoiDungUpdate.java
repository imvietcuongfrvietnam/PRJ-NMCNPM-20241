package myapp.mvc.model.dao.update;

import myapp.mvc.model.connectdb.SQLConnector;
import myapp.mvc.model.entities.entitiesdb.ThongTinNguoiDung;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ThongTinNguoiDungUpdate implements Updater<ThongTinNguoiDung> {
    @Override
    public void update(ThongTinNguoiDung entity) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "UPDATE thongtinnguoidung SET Ten = ?, Email = ?, QueQuan = ?, DienThoai = ? WHERE SoCMND = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getTen());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getQueQuan());
            preparedStatement.setString(4, entity.getDienThoai());
            preparedStatement.setString(5, entity.getSoCMND());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
