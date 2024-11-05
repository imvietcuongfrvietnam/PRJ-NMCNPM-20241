package myapp.model.communicatedb.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ThongTinNguoiDungInsert {
    public void insert(String hoTen, String CMND, String ngaySinh, String email, String queQuan, String soDienThoai){
        SQLConnector connector = SQLConnector.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO thongtinnguoidung (Ten, SoCMND, NgaySinh, Email, QueQuan, DienThoai) VALUES (?,?,?, ?, ?, ?)";
        try {
            connection = connector.getConnection();

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, hoTen);
            preparedStatement.setString(2, CMND);
            preparedStatement.setString(3, new java.sql.Date(ngaySinh.getTime()));
            preparedStatement.setString(4, email);
            preparedStatement.setDate(5, queQuan);

            int rowsAffected = preparedStatement.executeUpdate(); // Sử dụng executeUpdate() cho câu lệnh INSERT
            System.out.println("Số bản ghi đã thêm: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                connector.closeDB();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
