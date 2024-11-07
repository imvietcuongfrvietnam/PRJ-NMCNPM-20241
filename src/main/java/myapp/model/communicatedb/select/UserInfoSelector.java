package myapp.model.communicatedb.select;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiessystem.UserInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserInfoSelector {

    public UserInfo select(String tenDangNhap) {
        String sql = "SELECT thongtinnguoidung.Ten AS name, thongtinnguoidung.NgaySinh AS birthday, thongtinnguoidung.SoCMND AS id, " +
                "thongtinnguoidung.DienThoai AS phone, thongtinnguoidung.Email AS email, thongtinnguoidung.QueQuan AS hometown, " +
                "thongtinnguoidung.DiaChi AS address, taikhoannguoidung.TenDangNhap AS username, taikhoannguoidung.MatKhau AS password, " +
                "CASE WHEN taikhoannguoidung.GioiTinh = 'M' THEN 'Male' ELSE 'Female' END AS gender " +
                "FROM thongtinnguoidung " +
                "JOIN taikhoannguoidung ON thongtinnguoidung.MaTaiKhoan = taikhoannguoidung.MaTaiKhoan " +
                "WHERE taikhoannguoidung.TenDangNhap = ?";

        SQLConnector connector = SQLConnector.getInstance();
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, tenDangNhap);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String gender = resultSet.getString("gender");
                    String birthday = resultSet.getString("birthday");
                    String id = resultSet.getString("id");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");
                    String hometown = resultSet.getString("hometown");
                    String address = resultSet.getString("address");

                    UserInfo userInfo = new UserInfo(name, gender, birthday, id, phone, email, hometown, address);
                    return userInfo;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Logging có thể được thêm để theo dõi lỗi
        }
        return null;
    }
}
