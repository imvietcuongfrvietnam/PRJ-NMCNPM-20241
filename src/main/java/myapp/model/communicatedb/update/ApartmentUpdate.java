package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Apartment;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ApartmentUpdate {
    public void update(String apartmentID, int floor, int area, String status, String note) {
        String query = "UPDATE canho SET Tang = ?, DienTich = ?, TinhTrang = ?, ThongTinBoSung = ? WHERE MaCanHo = ?";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, floor);
            preparedStatement.setInt(2, area);
            preparedStatement.setString(3, status);
            preparedStatement.setString(4, note);
            preparedStatement.setString(5, apartmentID);
            preparedStatement.executeUpdate();
            System.out.println("Cập nhật thành công căn hộ: " + apartmentID);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cập nhật thất bại căn hộ: " + apartmentID);
        }
    }
    public static void removeHouseholdFromApartment(String apartmentID) {
        String query = "UPDATE canho SET TinhTrang = ? WHERE MaCanHo = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "Chưa sử dụng");
            preparedStatement.setString(2, apartmentID);


            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
