package myapp.mvc.model.dao.select;

import myapp.mvc.model.connectdb.SQLConnector;
import myapp.mvc.model.entities.entitiessystem.UserInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoSelector {

    private static final String SQL_SELECT_USER_INFO =
            "SELECT thongtinnguoidung.Ten AS name, thongtinnguoidung.NgaySinh AS birthday, " +
                    "thongtinnguoidung.SoCMND AS id, thongtinnguoidung.DienThoai AS phone, " +
                    "thongtinnguoidung.Email AS email, thongtinnguoidung.QueQuan AS hometown " +
                    "FROM thongtinnguoidung, taikhoannguoidung " +
                    "WHERE thongtinnguoidung.maTaiKhoan = taikhoannguoidung.maTaiKhoan and taikhoannguoidung.TenDangNhap= ?";

    public UserInfo select(String maTaiKhoan) {
        SQLConnector connector = SQLConnector.getInstance();
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_INFO)) {

            preparedStatement.setString(1, maTaiKhoan);

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
        String birthday = resultSet.getString("birthday");
        String id = resultSet.getString("id");
        String phone = resultSet.getString("phone");
        String email = resultSet.getString("email");
        String hometown = resultSet.getString("hometown");

        // Lớp UserInfo sẽ không còn thuộc tính 'address' hay 'gender'
        return new UserInfo(name, birthday, id, phone, email, hometown);
    }
}
