package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.TaiKhoanNguoiDung;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TaiKhoanNguoiDungUpdate implements Updater<TaiKhoanNguoiDung> {
    @Override
    public void update(TaiKhoanNguoiDung entity) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "UPDATE taikhoannguoidung SET VaiTro = ?, TenDangNhap = ?, MatKhau = ? WHERE MaTaiKhoan = ?";

        try (Connection connection = connector.getConnection();
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
