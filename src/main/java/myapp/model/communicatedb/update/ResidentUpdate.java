package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Resident;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ResidentUpdate {
    public void update(Resident resident) {
        String query = "UPDATE nguoithue SET HoTen = ?, GioiTinh = ?, NgaySinh = ?, QueQuan = ?, NgheNghiep = ?, " +
                "TrangThai = ?, DanToc = ?, QuocTich = ?, TrinhDoHocVan = ?, ThongTinBoSung = ?, MaHoGiaDinh = ? " +
                "WHERE SoCMND = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, resident.getName());
            preparedStatement.setString(2, resident.getGender());
            preparedStatement.setDate(3, java.sql.Date.valueOf(resident.getBirthday()));
            preparedStatement.setString(4, resident.getHometown());
            preparedStatement.setString(5, resident.getOccupation());
            preparedStatement.setString(6, resident.getStatus());
            preparedStatement.setString(7, resident.getEthnicity());
            preparedStatement.setString(8, resident.getNationality());
            preparedStatement.setString(9, resident.getEducation());
            preparedStatement.setString(10, resident.getNote());
            preparedStatement.setString(11, resident.getHouseHoldID());
            preparedStatement.setString(12, resident.getIDcard());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
