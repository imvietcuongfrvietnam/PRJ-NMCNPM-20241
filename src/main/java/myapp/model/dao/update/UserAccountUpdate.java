package myapp.model.dao.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserAccountUpdate implements Updater<UserAccount> {
    @Override
    public void update(UserAccount entity) {
        String query = "UPDATE taikhoannguoidung SET VaiTro = ?, TenDangNhap = ?, MatKhau = ? WHERE MaTaiKhoan = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getVaiTro());
            preparedStatement.setString(2, entity.getTenDangNhap());
            preparedStatement.setString(3, entity.getMatKhau());
            preparedStatement.setInt(4, entity.getMaTaiKhoan());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
