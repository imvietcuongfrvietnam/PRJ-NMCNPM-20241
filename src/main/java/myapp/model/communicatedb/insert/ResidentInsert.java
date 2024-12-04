package myapp.model.communicatedb.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class ResidentInsert {
    public void insert(String soCMND, String gioiTinh, Date ngaySinh, String queQuan, String hoTen, String ngheNghiep, String trangThai, String danToc, String quocTich, String trinhDoHocVan, String thongTinBoSung, String maHoGiaDinh) {
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
