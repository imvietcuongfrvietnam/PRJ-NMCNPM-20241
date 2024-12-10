package myapp.dao;

import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.HouseHold;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class HouseholdDAO {
    public static void delete(String maHoGiaDinh) {
        String query = "DELETE FROM hogiadinh WHERE MaHoGiaDinh = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, maHoGiaDinh);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức lấy thông tin hộ gia đình theo MaCanHo
    public static HouseHold getHouseHoldByApartmentID(String apartmentID) {
        HouseHold houseHold = null;
        String query = "SELECT MaHoGiaDinh, NgayChuyenVao, NgayChuyenRa FROM hogiadinh WHERE MaCanHo = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, apartmentID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String houseHoldID = resultSet.getString("MaHoGiaDinh");
                String moveInDate = resultSet.getString("NgayChuyenVao");
                String moveOutDate = resultSet.getString("NgayChuyenRa");

                // Tạo đối tượng houseHold và gán giá trị cho nó
                houseHold = new HouseHold(houseHoldID, moveInDate, moveOutDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return houseHold;
    }

    public static void insertHousehold(String maHoGiaDinh, String maPhongThue, Date ngayChuyenVao, String soCMNDChuHo, String trangThai) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = "INSERT INTO hogiadinh (maHoGiaDinh, maPhongThue, ngayChuyenVao, soCMNDChuHo, trangThai) VALUES (?, ?, ?, ?, ?)";

        try {
            // Kết nối tới CSDL
            connection = SQLConnector.getConnection();

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
                SQLConnector.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateByMaHoGiaDinh(HouseHold entity) {
                String query = "UPDATE hogiadinh SET MaCanHo = ?, NgayChuyenVao = ?, NgayChuyenRa = ?, SoCMNDChuHo = ?, TrangThai = ? WHERE MaHoGiaDinh = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Sử dụng các phương thức getter từ entity HoGiaDinh
            preparedStatement.setString(1, entity.getApartmentID()); // Cập nhật MaCanHo
            preparedStatement.setDate(2, java.sql.Date.valueOf(entity.getMoveInDate())); // Cập nhật NgayChuyenVao
            preparedStatement.setDate(3, java.sql.Date.valueOf(entity.getMoveOutDate())); // Cập nhật NgayChuyenRa
            preparedStatement.setString(4, entity.getResidentID()); // Cập nhật SoCMNDChuHo
            preparedStatement.setString(5, entity.getStatus()); // Cập nhật TrangThai
            preparedStatement.setString(6, entity.getHouseHoldID()); // Khóa chính để tìm bản ghi

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
