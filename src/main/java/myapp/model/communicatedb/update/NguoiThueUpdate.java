package myapp.model.communicatedb.update;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.NguoiThue;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class NguoiThueUpdate implements Updater<NguoiThue> {
    @Override
    public void update(NguoiThue entity) {
        SQLConnector connector = SQLConnector.getInstance();
        String query = "UPDATE nguoithue SET HoTen = ?, GioiTinh = ?, NgaySinh = ?, QueQuan = ?, NgheNghiep = ?, " +
                "TrangThai = ?, DanToc = ?, QuocTich = ?, TrinhDoHocVan = ?, ThongTinBoSung = ?, MaHoGiaDinh = ? " +
                "WHERE SoCMND = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getHoTen());
            preparedStatement.setString(2, entity.getGioiTinh());
            preparedStatement.setDate(3, java.sql.Date.valueOf(entity.getNgaySinh()));
            preparedStatement.setString(4, entity.getQueQuan());
            preparedStatement.setString(5, entity.getNgheNghiep());
            preparedStatement.setString(6, entity.getTrangThai());
            preparedStatement.setString(7, entity.getDanToc());
            preparedStatement.setString(8, entity.getQuocTich());
            preparedStatement.setString(9, entity.getTrinhDoHocVan());
            preparedStatement.setString(10, entity.getThongTinBoSung());
            preparedStatement.setString(11, entity.getMaHoGiaDinh());
            preparedStatement.setString(12, entity.getSoCMND());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
