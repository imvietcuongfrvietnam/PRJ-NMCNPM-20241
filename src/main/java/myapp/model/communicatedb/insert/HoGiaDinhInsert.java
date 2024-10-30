package myapp.model.communicatedb.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

<<<<<<< HEAD:src/main/java/myapp/model/update/PasswordUpdater.java
public class PasswordUpdater implements myapp.model.update.Updater {
    public void update(String userName, String newPassword) {
        // Câu lệnh SQL để cập nhật mật khẩu
        String sql = "UPDATE taikhoannguoidung SET MatKhau = ? WHERE MaTaiKhoan = ?";

        // Kết nối và PreparedStatement
=======
public class HoGiaDinhInsert {
    public void insert(String maHoGiaDinh, String maPhongThue, Date ngayChuyenVao, String soCMNDChuHo, String trangThai) throws SQLException {
>>>>>>> 858b4be41ce02798efd7541bd03fdde07fce0261:src/main/java/myapp/model/communicatedb/insert/HoGiaDinhInsert.java
        SQLConnector connector = SQLConnector.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

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
