package myapp.model.communicatedb.select;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordSelector {
    public String select(String userName) {
        String sql = "SELECT MatKhau FROM taikhoannguoidung WHERE TenDangNhap = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String password = null;

        try {
            connection = SQLConnector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                password = resultSet.getString("MatKhau");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    SQLConnector.closeConnection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return password;
    }
}
