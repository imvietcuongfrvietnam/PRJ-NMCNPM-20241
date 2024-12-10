package myapp.dao;

import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Resident;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class ResidentDAO {
    public static void deleteBySoCMND(String soCMND) {
                String query = "DELETE FROM cudan WHERE SoCMND = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, soCMND);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateBySoCMND(Resident entity) {
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

    public static void insertResident(String soCMND, String gioiTinh, Date ngaySinh, String queQuan, String hoTen, String ngheNghiep, String trangThai, String danToc, String quocTich, String trinhDoHocVan, String thongTinBoSung, String maHoGiaDinh) {
        String query = "INSERT INTO cudan (SoCMND, GioiTinh, NgaySinh, QueQuan, HoTen, NgheNghiep, TrangThai, DanToc, QuocTich, TrinhDoHocVan, ThongTinBoSung, MaHoGiaDinh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, soCMND);
            preparedStatement.setString(2, gioiTinh);
            preparedStatement.setDate(3, ngaySinh);
            preparedStatement.setString(4, queQuan);
            preparedStatement.setString(5, hoTen);
            preparedStatement.setString(6, ngheNghiep);
            preparedStatement.setString(7, trangThai);
            preparedStatement.setString(8, danToc);
            preparedStatement.setString(9, quocTich);
            preparedStatement.setString(10, trinhDoHocVan);
            preparedStatement.setString(11, thongTinBoSung);
            preparedStatement.setString(12, maHoGiaDinh);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
