package myapp.model.communicatedb.select;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.HouseHold;
import myapp.model.entities.entitiesdb.Resident;
import myapp.model.entities.entitiesdb.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HouseHoldSelect {
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
    // Phương thức lấy danh sách thành viên trong hộ gia đình theo MaHoGiaDinh
    public static ObservableList<Resident> getMembersByHouseHoldID(String houseHoldID) {
        ObservableList<Resident> members = FXCollections.observableArrayList();
        String query = "SELECT HoTen, GioiTinh, NgaySinh, CCCD FROM cudan WHERE MaHoGiaDinh = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, houseHoldID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("HoTen");
                String gender = resultSet.getString("GioiTinh");
                String birthday = resultSet.getString("NgaySinh");
                String IDcard = resultSet.getString("CCCD");

                // Tạo đối tượng Resident và thêm vào danh sách
                Resident member = new Resident(name, gender, birthday, IDcard);
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
    // Phương thức lấy thông tin mã căn hộ theo MaHoGiaDinh
    public static String getApartmentIDByHouseHoldID(String houseHoldID) {
        String apartmentID = null;
        String query = "SELECT MaCanHo FROM hogiadinh WHERE MaHoGiaDinh = ?";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, houseHoldID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                apartmentID = resultSet.getString("MaCanHo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apartmentID;
    }

    // Phương thức lấy danh sách phương tiện trong hộ gia đình theo MaHoGiaDinh
    public static ObservableList<Vehicle> getVehiclesByHouseHoldID(String houseHoldID) {
        ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
        String query = "SELECT LoaiPhuongTien, BienSo, NgayBatDau, NgayKetThuc FROM quanlyphuongtien WHERE MaHoGiaDinh = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, houseHoldID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String vehicleType = resultSet.getString("LoaiPhuongTien");
                String licensePlate = resultSet.getString("BienSo");
                String startDate = resultSet.getString("NgayBatDau");
                String endDate = resultSet.getString("NgayKetThuc");

                // Tạo đối tượng Resident và thêm vào danh sách
                Vehicle vehicle = new Vehicle(vehicleType, licensePlate, startDate, endDate);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

}
