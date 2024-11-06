package myapp.model.communicatedb.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ThongTinNguoiDungInsert {
    public void insert(String hoTen, String CMND, String ngaySinh, String email, String queQuan, String soDienThoai, int maTaiKhoan){
        SQLConnector connector = SQLConnector.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO thongtinnguoidung (MaTaiKhoan, Ten, SoCMND, NgaySinh, Email, QueQuan, DienThoai) VALUES (?, ?,?,?, ?, ?, ?)";

        try {
            connection = connector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, maTaiKhoan);
            preparedStatement.setString(2, hoTen);
            preparedStatement.setString(3, CMND);

            // Chuyển đổi ngaySinh từ String sang java.sql.Date
            Date sqlDate = Date.valueOf(ngaySinh); // Định dạng ngaySinh: "yyyy-MM-dd"
            preparedStatement.setDate(4, sqlDate);

            preparedStatement.setString(5, email);
            preparedStatement.setString(6, queQuan);
            preparedStatement.setString(7, soDienThoai);

            int rowsAffected = preparedStatement.executeUpdate();
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
