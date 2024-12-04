package myapp.model.communicatedb.select;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Resident;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ResidentDataHandler {

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
