package myapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.HouseHold;
import myapp.model.entities.entitiesdb.Resident;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataHandler {

    // Phương thức lấy tên người dân theo SoCMND
    public static String getResidentNameByResidentID(String residentID) {
        String residentName = "";
        String query = "SELECT HoTen FROM cudan WHERE SoCMND = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, residentID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                residentName = resultSet.getString("HoTen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return residentName;
    }
    // Phương thức lấy số điện thoại người dân theo SoCMND
    public static String getResidentPhoneByResidentID(String residentID) {
        String residentPhone = null;
        String query = "SELECT SoDienThoai FROM cudan WHERE SoCMND = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, residentID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                residentPhone = resultSet.getString("SoDienThoai");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return residentPhone;
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

    // Phương thức lấy thông tin chủ hộ gia đình theo MaCanHo
    public static Resident getResidentByApartmentID(String apartmentID) {
        Resident resident = null;
        String query = "SELECT TOP 1 HoTen, SoCMND FROM cudan WHERE MaHoGiaDinh = (SELECT MaHoGiaDinh FROM hogiadinh WHERE MaCanHo = ?) ";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, apartmentID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("HoTen");
                String IDcard = resultSet.getString("SoCMND");

                resident = new Resident(name, IDcard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resident;
    }


    public static ObservableList<Resident> getMembersByHouseHoldID(String houseHoldID) {
        ObservableList<Resident> members = FXCollections.observableArrayList();
        String query = "SELECT HoTen, GioiTinh, NgaySinh, SoCMND FROM cudan WHERE MaHoGiaDinh = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, houseHoldID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("HoTen");
                String gender = resultSet.getString("GioiTinh");
                String birthday = resultSet.getString("NgaySinh");
                String IDcard = resultSet.getString("SoCMND");

                // Tạo đối tượng Resident và thêm vào danh sách
                Resident member = new Resident(name, gender, birthday, IDcard);
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }


}
