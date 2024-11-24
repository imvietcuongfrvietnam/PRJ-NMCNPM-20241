package myapp.model.communicatedb.select;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiessystem.UserInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserInfoSelector {

    private static final String SQL_SELECT_USER_INFO =
            "SELECT thongtinnguoidung.Ten AS name, thongtinnguoidung.NgaySinh AS birthday, thongtinnguoidung.SoCMND AS id, " +
                    "thongtinnguoidung.DienThoai AS phone, thongtinnguoidung.Email AS email, thongtinnguoidung.QueQuan AS hometown, " +
                    "thongtinnguoidung.DiaChi AS address, taikhoannguoidung.TenDangNhap AS username, taikhoannguoidung.MatKhau AS password, " +
                    "CASE WHEN taikhoannguoidung.GioiTinh = 'M' THEN 'Male' ELSE 'Female' END AS gender " +
                    "FROM thongtinnguoidung " +
                    "JOIN taikhoannguoidung ON thongtinnguoidung.MaTaiKhoan = taikhoannguoidung.MaTaiKhoan " +
                    "WHERE taikhoannguoidung.TenDangNhap = ?";

    public UserInfo select(String tenDangNhap) {
        SQLConnector connector = SQLConnector.getInstance();
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_INFO)) {

            preparedStatement.setString(1, tenDangNhap);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUserInfo(resultSet); // Trả về đối tượng UserInfo nếu tìm thấy
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Cân nhắc thay bằng logging framework
        }

        return null; // Trả về null nếu không tìm thấy user
    }


    private UserInfo mapResultSetToUserInfo(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        String gender = resultSet.getString("gender");
        String birthday = resultSet.getString("birthday");
        String id = resultSet.getString("id");
        String phone = resultSet.getString("phone");
        String email = resultSet.getString("email");
        String hometown = resultSet.getString("hometown");
        String address = resultSet.getString("address");

        return new UserInfo(name, gender, birthday, id, phone, email, hometown, address);
    }
}
