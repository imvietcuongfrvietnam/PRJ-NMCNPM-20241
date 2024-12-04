package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Resident;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ResidentUpdate implements Updater<Resident> {
    @Override
    public void update(Resident entity) {
        String query = "UPDATE nguoithue SET HoTen = ?, GioiTinh = ?, NgaySinh = ?, QueQuan = ?, NgheNghiep = ?, " +
                "TrangThai = ?, DanToc = ?, QuocTich = ?, TrinhDoHocVan = ?, ThongTinBoSung = ?, MaHoGiaDinh = ? " +
                "WHERE SoCMND = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getGender());
            preparedStatement.setDate(3, java.sql.Date.valueOf(entity.getBirthday()));
            preparedStatement.setString(4, entity.getHometown());
            preparedStatement.setString(5, entity.getOccupation());
            preparedStatement.setString(6, entity.getStatus());
            preparedStatement.setString(7, entity.getEthnicity());
            preparedStatement.setString(8, entity.getNationality());
            preparedStatement.setString(9, entity.getEducation());
            preparedStatement.setString(10, entity.getAdditionalInfo());
            preparedStatement.setString(11, entity.getHouseHoldID());
            preparedStatement.setString(12, entity.getIDcard());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
