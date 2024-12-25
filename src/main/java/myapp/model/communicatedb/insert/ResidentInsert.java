package myapp.model.communicatedb.insert;

import myapp.model.connectdb.SQLConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ResidentInsert {
    public void insert(String name, String gender, String birthday, String IDcard, String hometown, String phone, String occupation, String ethnicity, String nationality, String education, String status, String houseHoldID, String note) {
        String query = "INSERT INTO cudan (HoTen, GioiTinh, NgaySinh, CCCD, QueQuan, SoDienThoai, NgheNghiep, DanToc, QuocTich, TrinhDoHocVan, TrangThai, MaHoGiaDinh, ThongTinBoSung) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2,gender);
            preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            preparedStatement.setString(4, IDcard);
            preparedStatement.setString(5, hometown);
            preparedStatement.setString(6, phone);
            preparedStatement.setString(7, occupation);
            preparedStatement.setString(8, ethnicity);
            preparedStatement.setString(9, nationality);
            preparedStatement.setString(10, education);
            preparedStatement.setString(11, status);
            preparedStatement.setString(12, houseHoldID);
            preparedStatement.setString(13, note);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
