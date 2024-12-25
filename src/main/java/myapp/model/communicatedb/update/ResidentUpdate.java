package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Resident;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ResidentUpdate {
    public void update(Resident resident) {
        String query = "UPDATE cudan SET HoTen = ?, GioiTinh = ?, NgaySinh = ?, QueQuan = ?, SoDienThoai = ?, NgheNghiep = ?, " +
                "TrangThai = ?, DanToc = ?, QuocTich = ?, TrinhDoHocVan = ?, ThongTinBoSung = ?, MaHoGiaDinh = ? " +
                "WHERE CCCD = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, resident.getName());
            preparedStatement.setString(2, resident.getGender());
            preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDate.parse(resident.getBirthday(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            preparedStatement.setString(4, resident.getHometown());
            preparedStatement.setString(5, resident.getPhone());
            preparedStatement.setString(6, resident.getOccupation());
            preparedStatement.setString(7, resident.getStatus());
            preparedStatement.setString(8, resident.getEthnicity());
            preparedStatement.setString(9, resident.getNationality());
            preparedStatement.setString(10, resident.getEducation());
            preparedStatement.setString(11, resident.getNote());
            preparedStatement.setString(12, resident.getHouseHoldID());
            preparedStatement.setString(13, resident.getIDcard());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
