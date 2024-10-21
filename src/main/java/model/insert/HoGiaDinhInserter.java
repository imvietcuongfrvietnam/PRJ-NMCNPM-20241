package model.insert;

import model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class HoGiaDinhInserter {
    public void insert(String maHoGiaDinh, String maPhongThue, Date ngayChuyenVao, String soCMNDChuHo, String trangThai) {
        SQLConnector connector = SQLConnector.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        // Tạo câu lệnh SQL sử dụng tham số
        String sql = "INSERT INTO hogiadinh (maHoGiaDinh, maPhongThue, ngayChuyenVao, soCMNDChuHo, trangThai) VALUES (?, ?, ?, ?, ?)";

        try {
            // Kết nối tới CSDL
            connection = connector.getConnection();

            // Tạo PreparedStatement từ câu lệnh SQL
            preparedStatement = connection.prepareStatement(sql);

            // Gán giá trị cho các tham số trong câu lệnh SQL
            preparedStatement.setString(1, maHoGiaDinh);
            preparedStatement.setString(2, maPhongThue);
            preparedStatement.setDate(3, new java.sql.Date(ngayChuyenVao.getTime())); // Chuyển đổi Date sang java.sql.Date
            preparedStatement.setString(4, soCMNDChuHo);
            preparedStatement.setString(5, trangThai);

            // Thực thi câu lệnh INSERT
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Số bản ghi đã thêm: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng PreparedStatement và kết nối
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
