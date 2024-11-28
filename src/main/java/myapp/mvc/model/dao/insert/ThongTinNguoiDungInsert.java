package myapp.mvc.model.dao.insert;

import myapp.mvc.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class ThongTinNguoiDungInsert {

    public void insert(String hoTen, String CMND, String ngaySinh, String email, String queQuan, String soDienThoai, int maTaiKhoan) {
        SQLConnector connector = SQLConnector.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO thongtinnguoidung (MaTaiKhoan, Ten, SoCMND, NgaySinh, Email, QueQuan, DienThoai) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = connector.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, maTaiKhoan);
            preparedStatement.setString(2, hoTen);
            preparedStatement.setString(3, CMND);

            // Chuyển đổi ngày sinh từ String sang java.sql.Date với định dạng dd-MM-yyyy
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date parsedDate = sdf.parse(ngaySinh);
                preparedStatement.setDate(4, new Date(parsedDate.getTime()));
            } catch (ParseException e) {
                // Nếu ngày sinh không hợp lệ, gán giá trị mặc định theo chuẩn "yyyy-MM-dd"
                System.out.println("Ngày sinh không hợp lệ, sử dụng giá trị mặc định.");
                preparedStatement.setDate(4, Date.valueOf("1970-01-01")); // Sử dụng định dạng "yyyy-MM-dd"
            }

            preparedStatement.setString(5, email);
            preparedStatement.setString(6, queQuan);
            preparedStatement.setString(7, soDienThoai);

            // Thực hiện truy vấn và kiểm tra số dòng đã ảnh hưởng
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Số bản ghi đã thêm: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo tài nguyên được đóng đúng cách
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {

            }
        }
    }
}
