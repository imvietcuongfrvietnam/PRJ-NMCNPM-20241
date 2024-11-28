package myapp.mvc.model.dao.select;

import myapp.mvc.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaTaiKhoanSelector {
    public int select() {
        String sql = "SELECT MAX(MaTaiKhoan) FROM taikhoannguoidung";

        SQLConnector connector = SQLConnector.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int password = 0;

        try {
            connection = connector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            // Kiểm tra nếu có kết quả trả về và lấy giá trị MAX
            if (resultSet.next()) {
                password = resultSet.getInt(1);  // Lấy giá trị MAX(MaTaiKhoan)
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
                    connector.closeDB();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return password;
    }
}
