package myapp.model.dao.select;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.UserInformation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoSelector {
    public UserInformation select(String userName){
        String sql = "SELECT * FROM thongtinnguoidung WHERE MaTaiKhoan = " +
                "SELECT MaTaiKhoan FROM taikhoannguoidung WHERE TenDangNhap = ?";
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
                return new UserInformation(
                        resultSet.getString("SoCMND"),
                        resultSet.getString("Ten"),
                        resultSet.getString("Email"),
                        resultSet.getString("QueQuan"),
                        resultSet.getString("DienThoai"),
                        resultSet.getString("NgaySinh")
                );

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
        return null;
    }
    }
