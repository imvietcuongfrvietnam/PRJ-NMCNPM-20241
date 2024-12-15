package myapp.dao;

import myapp.db.SQLConnector;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UnitPriceDAO {
    public static BigDecimal getByType(String type) {
        String query = "SELECT DonGia FROM dongiakhac WHERE LoaiDichVu = ?";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Gán giá trị cho tham số truy vấn
            preparedStatement.setString(1, type);

            // Thực thi truy vấn
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Lấy giá trị DonGia và trả về dưới dạng BigDecimal
                    return resultSet.getBigDecimal("DonGia");
                }
            }
        } catch (SQLException e) {
            // Xử lý lỗi SQL
            throw new RuntimeException("Lỗi khi lấy DonGia cho loại dịch vụ: " + type, e);
        }
        return BigDecimal.ZERO; // Trả về BigDecimal.ZERO nếu không tìm thấy giá trị
    }

}
