package myapp.model.connectdb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import myapp.model.entities.entitiesdb.HouseHold;
import myapp.model.entities.entitiesdb.Resident;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SQLConnector {

    // Thông tin kết nối cơ sở dữ liệu
    private static final String DB_URL = "jdbc:sqlserver://LAPTOP-cua-Hieu\\SQLEXPRESS:1433;databaseName=QlThuphi;encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "123456";

    // Định dạng ngày tháng
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Kết nối cơ sở dữ liệu (singleton)
    private static Connection connection;

    // Lấy kết nối duy nhất
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        }
        return connection;
    }

    // Đóng kết nối
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Truy vấn danh sách hộ gia đình
    public static ObservableList<HouseHold> getHouseHolds() {
        ObservableList<HouseHold> houseHoldsList = FXCollections.observableArrayList();
        String query = "SELECT MaHoGiaDinh, MaCanHo, " +
                "FORMAT(NgayChuyenVao, 'dd/MM/yyyy') AS NgayChuyenVao, " +
                "FORMAT(NgayChuyenRa, 'dd/MM/yyyy') AS NgayChuyenRa, " +
                "SoCMNDChuHo, TrangThai FROM hogiadinh";

        try (PreparedStatement statement = getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                houseHoldsList.add(new HouseHold(
                        resultSet.getString("MaHoGiaDinh"),
                        resultSet.getString("MaCanHo"),
                        resultSet.getString("NgayChuyenVao"),
                        resultSet.getString("NgayChuyenRa"),
                        resultSet.getString("SoCMNDChuHo"),
                        resultSet.getString("TrangThai")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return houseHoldsList;
    }

    // Truy vấn danh sách cư dân
    public static ObservableList<Resident> getResidents() {
        ObservableList<Resident> residentsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM cudan";

        try (PreparedStatement statement = getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String birthday = formatBirthday(resultSet.getString("NgaySinh"));

                residentsList.add(new Resident(
                        resultSet.getString("HoTen"),
                        resultSet.getString("GioiTinh"),
                        birthday,
                        resultSet.getString("SoCMND"),
                        resultSet.getString("QueQuan"),
                        resultSet.getString("NgheNghiep"),
                        resultSet.getString("DanToc"),
                        resultSet.getString("QuocTich"),
                        resultSet.getString("TrinhDoHocVan"),
                        resultSet.getString("TrangThai"),
                        resultSet.getString("ThongTinBoSung"),
                        resultSet.getString("MaHoGiaDinh")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return residentsList;
    }

    // Định dạng ngày sinh từ yyyy-MM-dd sang dd/MM/yyyy
    private static String formatBirthday(String birthday) {
        try {
            LocalDate date = LocalDate.parse(birthday, INPUT_FORMATTER);
            return date.format(OUTPUT_FORMATTER);
        } catch (Exception e) {
            return birthday; // Trả về ngày gốc nếu không định dạng được
        }
    }
}
