package myapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.Vehicle;

import java.sql.*;

import static myapp.db.SQLConnector.getConnection;

public class VehicleDAO {
    public static void insertVehicleManagement(Vehicle vehicle) {
        String query = "INSERT INTO quanlyxe (MaHo, LoaiPhuongTien, BienSo, NgayBatDau, NgayKetThuc, ThongTinBoSung) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Thiết lập các giá trị cho PreparedStatement từ đối tượng Vehicle
            preparedStatement.setString(1, vehicle.getHouseHoldID());
            preparedStatement.setString(2, vehicle.getVehicleType());
            preparedStatement.setString(3, vehicle.getLicensePlate());
            preparedStatement.setDate(4, new java.sql.Date(vehicle.getStartDate().getTime()));
            preparedStatement.setDate(5, new java.sql.Date(vehicle.getEndDate().getTime()));
            preparedStatement.setString(6, vehicle.getNote());

            // Thực thi câu lệnh SQL
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Không truy cập được danh sách xe", "Lỗi truy cập CSDL", "Không thể truy vấn CSDL lấy danh sách phương tiện");
        }
    }

    public static void updateVehicleManagement(Vehicle entity) {
        String query = "UPDATE quanlyxe SET LoaiPhuongTien = ?, NgayBatDau = ?, NgayKetThuc = ? WHERE BienSo = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getVehicleType());
            preparedStatement.setDate(2, new java.sql.Date(entity.getStartDate().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(entity.getEndDate().getTime()));
            preparedStatement.setString(4, entity.getLicensePlate());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Không truy cập được danh sách xe", "Lỗi truy cập CSDL", "Không thể truy vấn CSDL lấy danh sách phương tiện");
        }
    }

    public static void deleteVehicle(String bienSo) {
        String query = "DELETE FROM quanlyxe WHERE BienSo = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bienSo);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Vehicle> getVehicles() {
        ObservableList<Vehicle> vehiclesList = FXCollections.observableArrayList();
        String query = "SELECT * FROM quanlyxe";

        try (PreparedStatement statement = SQLConnector.getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Date startDate = resultSet.getDate("NgayBatDau");
                Date endDate = resultSet.getDate("NgayKetThuc");

                vehiclesList.add(new Vehicle(
                        resultSet.getString("MaHo"),
                        resultSet.getString("LoaiPhuongTien"),
                        resultSet.getString("BienSo"),
                        startDate, endDate,
                        resultSet.getString("ThongTinBoSung") // Thay thế GhiChu thành ThongTinBoSung
                ));
            }
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Không truy cập được danh sách xe", "Lỗi truy cập CSDL", "Không thể truy vấn CSDL lấy danh sách phương tiện");
        }
        return vehiclesList;
    }

    public static ObservableList<Vehicle> getVehiclesByHouseholdID(String householdID) {
        ObservableList<Vehicle> vehiclesList = FXCollections.observableArrayList();
        String query = "SELECT qlx.LoaiPhuongTien, qlx.BienSo, qlx.NgayBatDau, qlx.NgayKetThuc, hgd.MaHoGiaDinh, qlx.ThongTinBoSung " + // Cập nhật ở đây
                "FROM quanlyxe qlx JOIN hogiadinh hgd ON qlx.MaHo = hgd.MaHoGiaDinh " +
                "WHERE qlx.MaHo = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            if (connection == null) {
                throw new SQLException("Không thể kết nối tới cơ sở dữ liệu");
            }
            statement.setString(1, householdID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Date startDate = resultSet.getDate("NgayBatDau");
                    Date endDate = resultSet.getDate("NgayKetThuc");

                    vehiclesList.add(new Vehicle(
                            resultSet.getString("MaHoGiaDinh"),
                            resultSet.getString("LoaiPhuongTien"),
                            resultSet.getString("BienSo"),
                            startDate, endDate,
                            resultSet.getString("ThongTinBoSung") // Thay thế GhiChu thành ThongTinBoSung
                    ));
                }
            }
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Không truy cập được danh sách xe", "Lỗi truy cập CSDL", e.getMessage());
            e.printStackTrace();
        }
        return vehiclesList;
    }

    public static Vehicle selectVehicleByVehicleID(String vehicleID) {
        String query = "SELECT * FROM quanlyxe WHERE BienSo = ?";
        Vehicle vehicle = null;

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Thiết lập giá trị cho PreparedStatement
            statement.setString(1, vehicleID);

            try (ResultSet resultSet = statement.executeQuery()) {
                // Kiểm tra nếu có kết quả trả về
                if (resultSet.next()) {
                    // Chuyển đổi dữ liệu từ ResultSet thành đối tượng Vehicle
                    Date startDate = resultSet.getDate("NgayBatDau");
                    Date endDate = resultSet.getDate("NgayKetThuc");

                    vehicle = new Vehicle(
                            resultSet.getString("MaHo"),
                            resultSet.getString("LoaiPhuongTien"),
                            resultSet.getString("BienSo"),
                            startDate, endDate,
                            resultSet.getString("ThongTinBoSung") // Thay thế GhiChu thành ThongTinBoSung
                    );
                }
            }
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Không truy cập được thông tin xe", "Lỗi truy cập CSDL", "Không thể truy vấn CSDL lấy thông tin xe");
            e.printStackTrace();
        }

        return vehicle; // Nếu không tìm thấy, trả về null
    }
}
