package myapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.Resident;

import java.sql.*;

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

    // Phương thức lấy tên người dân theo SoCMND
    public static String getResidentNameByResidentID(String residentID) {
        String residentName = "";
        String query = "SELECT HoTen FROM cudan WHERE SoCMND = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, residentID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                residentName = resultSet.getString("HoTen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return residentName;
    }

    public static String getResidentPhoneByResidentID(String residentID) {
        String residentPhone = null;
        String query = "SELECT SoDienThoai FROM cudan WHERE SoCMND = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, residentID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                residentPhone = resultSet.getString("SoDienThoai");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return residentPhone;
    }

    public static Resident getResidentByApartmentID(String apartmentID) {
        Resident resident = null;
        String query = "SELECT TOP 1 HoTen, SoCMND FROM cudan WHERE MaHoGiaDinh = (SELECT MaHoGiaDinh FROM hogiadinh WHERE MaCanHo = ?) ";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, apartmentID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("HoTen");
                String IDcard = resultSet.getString("SoCMND");

                resident = new Resident(name, IDcard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resident;
    }

    public static ObservableList<Resident> getMembersByHouseHoldID(String houseHoldID) {
        ObservableList<Resident> members = FXCollections.observableArrayList();
        String query = "SELECT HoTen, GioiTinh, NgaySinh, SoCMND FROM cudan WHERE MaHoGiaDinh = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, houseHoldID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("HoTen");
                String gender = resultSet.getString("GioiTinh");
                String birthday = resultSet.getString("NgaySinh");
                String IDcard = resultSet.getString("SoCMND");

                // Tạo đối tượng Resident và thêm vào danh sách
                Resident member = new Resident(name, gender, birthday, IDcard);
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    public static ObservableList<Resident> getResidents() {
        ObservableList<Resident> residentsList = FXCollections.observableArrayList();
        String query = "SELECT HoTen, GioiTinh, NgaySinh, SoCMND, QueQuan, SoDienThoai, " +
                "NgheNghiep, DanToc, QuocTich, TrinhDoHocVan, TrangThai, ThongTinBoSung, MaHoGiaDinh " +
                "FROM nguoithue";

        try (PreparedStatement statement = SQLConnector.getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Date birthday = resultSet.getDate("NgaySinh"); // NgaySinh là kiểu java.sql.Date

                residentsList.add(new Resident(
                        resultSet.getString("HoTen"),
                        resultSet.getString("GioiTinh"),
                        resultSet.getDate("NgaySinh"), // Chuyển thành String nếu cần định dạng
                        resultSet.getString("SoCMND"),
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
            BaseDAO.showErrorAlert("Lỗi khi lấy thông tin cư dân", "Có lỗi xảy ra", "Truy vấn cơ sở dữ liệu thất bại.");
        }
        return residentsList;
    }
}
