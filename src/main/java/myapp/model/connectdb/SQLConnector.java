package myapp.model.connectdb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import myapp.model.entities.entitiesdb.Fee;
import myapp.model.entities.entitiesdb.*;

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
    // Truy vấn danh sách cư dân
    public static ObservableList<Resident> getResidents() {
        ObservableList<Resident> residentsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM cudan";

        try (PreparedStatement statement = getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String birthday = formatDate(resultSet.getString("NgaySinh"));

                residentsList.add(new Resident(
                        resultSet.getString("HoTen"),
                        resultSet.getString("GioiTinh"),
                        birthday,
                        resultSet.getString("CCCD"),
                        resultSet.getString("QueQuan"),
                        resultSet.getString("SoDienThoai"),
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
    // Truy vấn danh sách hộ gia đình
    public static ObservableList<HouseHold> getHouseHolds() {
        ObservableList<HouseHold> houseHoldsList = FXCollections.observableArrayList();
        String query = "SELECT MaHoGiaDinh, MaCanHo, " +
                "FORMAT(NgayChuyenVao, 'dd/MM/yyyy') AS NgayChuyenVao, " +
                "FORMAT(NgayChuyenRa, 'dd/MM/yyyy') AS NgayChuyenRa, " +
                "CCCDChuHo, TrangThai FROM hogiadinh";

        try (PreparedStatement statement = getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                houseHoldsList.add(new HouseHold(
                        resultSet.getString("MaHoGiaDinh"),
                        resultSet.getString("MaCanHo"),
                        resultSet.getString("NgayChuyenVao"),
                        resultSet.getString("NgayChuyenRa"),
                        resultSet.getString("CCCDChuHo"),
                        resultSet.getString("TrangThai")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return houseHoldsList;
    }
    // Truy vấn danh sách khoản phí
    public static ObservableList<Fee> getFees() {
        ObservableList<Fee> feesList = FXCollections.observableArrayList();
        String query = "SELECT * FROM quanlykhoanphi";

        try (PreparedStatement statement = getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String expDate = formatDate(resultSet.getString("NgayHetHan"));
                feesList.add(new Fee(
                        resultSet.getString("MaKhoanPhi"),
                        resultSet.getString("TenKhoanPhi"),
                        resultSet.getString("MaHoGiaDinh"),
                        resultSet.getString("SoTien"),
                        expDate,
                        resultSet.getString("TinhTrang"),
                        resultSet.getString("GhiChu")

                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feesList;
    }

    // Truy vấn danh sách hóa đơn
    public static ObservableList<Bill> getBills() {
        ObservableList<Bill> billsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM quanlyhoadon";

        try (PreparedStatement statement = getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String expDate = formatDate(resultSet.getString("NgayHetHan"));
                billsList.add(new Bill(
                        resultSet.getString("MaHoaDon"),
                        resultSet.getString("TenHoaDon"),
                        resultSet.getString("MaHoGiaDinh"),
                        resultSet.getString("TenNhaCungCap"),
                        resultSet.getString("SoTien"),
                        expDate,
                        resultSet.getString("TinhTrang"),
                        resultSet.getString("GhiChu")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billsList;
    }
    // Truy vấn danh sách quỹ đóng góp
    public static ObservableList<ContributionFund> getFunds() {
        ObservableList<ContributionFund> fundsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM quanlyquydonggop";

        try (PreparedStatement statement = getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                fundsList.add(new ContributionFund(
                        resultSet.getString("TenQuy"),
                        resultSet.getString("MaQuy"),
                        resultSet.getString("MaHoGiaDinh"),
                        resultSet.getString("SoTienDongGop"),
                        formatDate(resultSet.getString("NgayDongGop")),
                        resultSet.getString("TrangThai"),
                        resultSet.getString("GhiChu")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fundsList;
    }
    // Truy vấn danh sách quỹ đóng góp
    public static ObservableList<ContributionFund> getContributionFunds() {
        ObservableList<ContributionFund> contributionFundsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM bangquydonggop";

        try (PreparedStatement statement = getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                contributionFundsList.add(new ContributionFund(
                        resultSet.getString("TenQuy"),
                        resultSet.getString("MaQuy"),
                        resultSet.getString("SoTien"),
                        formatDate(resultSet.getString("NgayBatDau")),
                        formatDate(resultSet.getString("NgayKetThuc"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contributionFundsList;
    }
    // Truy vấn danh sách căn hộ
    public static ObservableList<Apartment> getApartments() {
        ObservableList<Apartment> apartmentsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM canho";

        try (PreparedStatement statement = getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                apartmentsList.add(new Apartment(
                        resultSet.getString("MaCanHo"),
                        resultSet.getInt("Tang"),
                        resultSet.getInt("DienTich"),
                        resultSet.getString("TinhTrang"),
                        resultSet.getString("ThongTinBoSung")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apartmentsList;
    }
    // Truy vấn danh sách phương tiện
    public static ObservableList<Vehicle> getVehicles() {
        ObservableList<Vehicle> vehiclesList = FXCollections.observableArrayList();
        String query = "SELECT * FROM quanlyphuongtien";

        try (PreparedStatement statement = getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String startDate = formatDate(resultSet.getString("NgayBatDau"));
                String endDate = formatDate(resultSet.getString("NgayKetThuc"));

                vehiclesList.add(new Vehicle(
                        resultSet.getString("MaHoGiaDinh"),
                        resultSet.getString("LoaiPhuongTien"),
                        resultSet.getString("BienSo"),
                        startDate,
                        endDate,
                        resultSet.getString("GhiChu")

                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiclesList;
    }
    // Truy vấn danh sách tài khoản người dùng
    public static ObservableList<UserAccount> getUsers() {
        ObservableList<UserAccount> userAccountsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM taikhoannguoidung";

        try (PreparedStatement statement = getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String dateOfBirth = formatDate(resultSet.getString("NgaySinh"));

                userAccountsList.add(new UserAccount(
                        resultSet.getInt("MaTaiKhoan"),
                        resultSet.getString("VaiTro"),
                        resultSet.getString("TenDangNhap"),
                        resultSet.getString("MatKhau"),
                        resultSet.getString("HoTen"),
                        resultSet.getString("GioiTinh"),
                        dateOfBirth,
                        resultSet.getString("CMND"),
                        resultSet.getString("SoDienThoai"),
                        resultSet.getString("Email"),
                        resultSet.getString("QueQuan"),
                        resultSet.getString("DiaChi")

                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userAccountsList;
    }
    // Định dạng ngày sinh từ yyyy-MM-dd sang dd/MM/yyyy
    private static String formatDate(String formatDate) {
        try {
            LocalDate date = LocalDate.parse(formatDate, INPUT_FORMATTER);
            return date.format(OUTPUT_FORMATTER);
        } catch (Exception e) {
            return formatDate; // Trả về ngày gốc nếu không định dạng được
        }
    }
}
