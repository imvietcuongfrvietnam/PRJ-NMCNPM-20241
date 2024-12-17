package myapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.HouseHold;
import myapp.model.entities.entitiesdb.Resident;

import java.sql.*;

/**
 * Lớp ResidentDAO cung cấp các phương thức để tương tác với cơ sở dữ liệu liên quan đến đối tượng Resident.
 * Các phương thức này bao gồm việc thêm, sửa, xóa và truy vấn dữ liệu của các cư dân.
 */
public class ResidentDAO {

    /**
     * Xóa một cư dân khỏi cơ sở dữ liệu theo số CMND.
     *
     * @param soCMND Số CMND của cư dân cần xóa.
     */
    public static void deleteBySoCMND(String soCMND) {
        String query = "DELETE FROM nguoithue WHERE SoCMND = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, soCMND);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Thêm Phí Gửi Xe", "Không thể thêm phí gửi xe", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Thêm Phí Gửi Xe", "Không thể thêm phí gửi xe", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    /**
     * Cập nhật thông tin của một cư dân trong cơ sở dữ liệu theo số CMND.
     *
     * @param entity Đối tượng Resident chứa thông tin mới của cư dân.
     */
    public static void updateBySoCMND(Resident entity) {
        String query = "UPDATE nguoithue SET HoTen = ?, GioiTinh = ?, NgaySinh = ?, QueQuan = ?, NgheNghiep = ?, " +
                "TrangThai = ?, DanToc = ?, QuocTich = ?, TrinhDoHocVan = ?, ThongTinBoSung = ?, MaHoGiaDinh = ? , SoDienThoai = ?" +
                "WHERE SoCMND = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getGender());
            preparedStatement.setDate(3, entity.getBirthday());
            preparedStatement.setString(4, entity.getHometown());
            preparedStatement.setString(5, entity.getOccupation());
            preparedStatement.setString(6, entity.getStatus());
            preparedStatement.setString(7, entity.getEthnicity());
            preparedStatement.setString(8, entity.getNationality());
            preparedStatement.setString(9, entity.getEducation());
            preparedStatement.setString(10, entity.getAdditionalInfo());
            preparedStatement.setString(11, entity.getHouseHoldID());
            preparedStatement.setString(12, entity.getPhone());
            preparedStatement.setString(13, entity.getIDcard());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Thêm Phí Gửi Xe", "Không thể thêm phí gửi xe", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Thêm Phí Gửi Xe", "Không thể thêm phí gửi xe", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    /**
     * Thêm một cư dân mới vào cơ sở dữ liệu.
     *
     * @param soCMND       Số CMND của cư dân.
     * @param gioiTinh     Giới tính của cư dân.
     * @param ngaySinh     Ngày sinh của cư dân.
     * @param queQuan      Quê quán của cư dân.
     * @param hoTen        Họ và tên của cư dân.
     * @param ngheNghiep   Nghề nghiệp của cư dân.
     * @param trangThai    Trạng thái của cư dân.
     * @param danToc       Dân tộc của cư dân.
     * @param quocTich     Quốc tịch của cư dân.
     * @param trinhDoHocVan Trình độ học vấn của cư dân.
     * @param thongTinBoSung Thông tin bổ sung về cư dân.
     * @param maHoGiaDinh Mã hộ gia đình của cư dân.
     */
    public static void insertResident(String soCMND, String gioiTinh, Date ngaySinh, String queQuan, String hoTen, String ngheNghiep, String trangThai, String danToc, String quocTich, String trinhDoHocVan, String thongTinBoSung, String maHoGiaDinh) {
        String query = "INSERT INTO nguoithue (SoCMND, GioiTinh, NgaySinh, QueQuan, HoTen, NgheNghiep, TrangThai, DanToc, QuocTich, TrinhDoHocVan, ThongTinBoSung, MaHoGiaDinh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Thêm Phí Gửi Xe", "Không thể thêm phí gửi xe", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Thêm Phí Gửi Xe", "Không thể thêm phí gửi xe", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    /**
     * Lấy tên của cư dân theo số CMND.
     *
     * @param residentID Số CMND của cư dân cần lấy tên.
     * @return Tên của cư dân hoặc chuỗi rỗng nếu không tìm thấy.
     */
    public static String getResidentNameByResidentID(String residentID) {
        String residentName = "";
        String query = "SELECT HoTen FROM nguoithue WHERE SoCMND = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, residentID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                residentName = resultSet.getString("HoTen");
            }
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Thêm Phí Gửi Xe", "Không thể thêm phí gửi xe", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Thêm Phí Gửi Xe", "Không thể thêm phí gửi xe", "Có lỗi xảy ra: " + e.getMessage());
        }

        return residentName;
    }

    /**
     * Lấy số điện thoại của cư dân theo số CMND.
     *
     * @param residentID Số CMND của cư dân cần lấy số điện thoại.
     * @return Số điện thoại của cư dân hoặc null nếu không tìm thấy.
     */
    public static String getResidentPhoneByResidentID(String residentID) {
        String residentPhone = null;
        String query = "SELECT SoDienThoai FROM nguoithue WHERE SoCMND = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, residentID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                residentPhone = resultSet.getString("SoDienThoai");
            }
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Thêm Phí Gửi Xe", "Không thể thêm phí gửi xe", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Thêm Phí Gửi Xe", "Không thể thêm phí gửi xe", "Có lỗi xảy ra: " + e.getMessage());
        }

        return residentPhone;
    }

    /**
     * Lấy thông tin cư dân theo mã căn hộ.
     *
     * @param apartmentID Mã căn hộ.
     * @return Đối tượng Resident chứa thông tin của cư dân hoặc null nếu không tìm thấy.
     */
    public static Resident getResidentByApartmentID(String apartmentID) {
        Resident resident = null;
        String query = "SELECT TOP 1 nt.HoTen, nt.GioiTinh, nt.NgaySinh, nt.SoCMND, nt.QueQuan, nt.SoDienThoai, "
                + "nt.NgheNghiep, nt.DanToc, nt.QuocTich, nt.TrinhDoHocVan, nt.TrangThai, nt.ThongTinBoSung, nt.MaHoGiaDinh "
                + "FROM nguoithue nt "
                + "JOIN hogiadinh hgd ON nt.MaHoGiaDinh = hgd.MaHoGiaDinh "
                + "WHERE hgd.MaCanHo = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Gán tham số cho câu truy vấn
            preparedStatement.setString(1, apartmentID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Lấy dữ liệu từ ResultSet
                    String hoTen = resultSet.getString("HoTen");
                    String gioiTinh = resultSet.getString("GioiTinh");
                    Date ngaySinh = resultSet.getDate("NgaySinh");
                    String soCMND = resultSet.getString("SoCMND");
                    String queQuan = resultSet.getString("QueQuan");
                    String soDienThoai = resultSet.getString("SoDienThoai");
                    String ngheNghiep = resultSet.getString("NgheNghiep");
                    String danToc = resultSet.getString("DanToc");
                    String quocTich = resultSet.getString("QuocTich");
                    String trinhDoHocVan = resultSet.getString("TrinhDoHocVan");
                    String trangThai = resultSet.getString("TrangThai");
                    String thongTinBoSung = resultSet.getString("ThongTinBoSung");
                    String maHoGiaDinh = resultSet.getString("MaHoGiaDinh");

                    // Tạo đối tượng Resident
                    resident = new Resident(
                            hoTen, gioiTinh, ngaySinh, soCMND, queQuan, soDienThoai,
                            ngheNghiep, danToc, quocTich, trinhDoHocVan, trangThai, thongTinBoSung, maHoGiaDinh
                    );
                }
            }

        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Lấy Thông Tin Cư Dân", "Không thể lấy thông tin cư dân", "Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Lấy Thông Tin Cư Dân", "Không thể lấy thông tin cư dân", "Có lỗi xảy ra: " + e.getMessage());
            e.printStackTrace();
        }

        return resident;
    }


    /**
     * Lấy danh sách các thành viên trong một hộ gia đình.
     *
     * @param houseHoldID Mã hộ gia đình.
     * @return Danh sách cư dân trong hộ gia đình.
     */
    public static ObservableList<Resident> getMembersByHouseHoldID(String houseHoldID) {
        ObservableList<Resident> members = FXCollections.observableArrayList();
        String query = "SELECT HoTen, GioiTinh, NgaySinh, SoCMND, QueQuan, SoDienThoai, NgheNghiep, "
                + "DanToc, QuocTich, TrinhDoHocVan, TrangThai, ThongTinBoSung, MaHoGiaDinh "
                + "FROM nguoithue WHERE MaHoGiaDinh = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Gán tham số cho câu truy vấn
            preparedStatement.setString(1, houseHoldID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Lấy dữ liệu từ ResultSet
                String hoTen = resultSet.getString("HoTen");
                String gioiTinh = resultSet.getString("GioiTinh");
                Date ngaySinh = resultSet.getDate("NgaySinh");
                String soCMND = resultSet.getString("SoCMND");
                String queQuan = resultSet.getString("QueQuan");
                String soDienThoai = resultSet.getString("SoDienThoai");
                String ngheNghiep = resultSet.getString("NgheNghiep");
                String danToc = resultSet.getString("DanToc");
                String quocTich = resultSet.getString("QuocTich");
                String trinhDoHocVan = resultSet.getString("TrinhDoHocVan");
                String trangThai = resultSet.getString("TrangThai");
                String thongTinBoSung = resultSet.getString("ThongTinBoSung");
                String maHoGiaDinh = resultSet.getString("MaHoGiaDinh");

                // Tạo đối tượng Resident
                Resident resident = new Resident(
                        hoTen, gioiTinh, ngaySinh, soCMND, queQuan, soDienThoai,
                        ngheNghiep, danToc, quocTich, trinhDoHocVan, trangThai, thongTinBoSung, maHoGiaDinh
                );

                // Thêm vào danh sách members
                members.add(resident);
            }
        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi Lấy Danh Sách Cư Dân", "Không thể lấy danh sách cư dân", "Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi Lấy Danh Sách Cư Dân", "Không thể lấy danh sách cư dân", "Có lỗi xảy ra: " + e.getMessage());
            e.printStackTrace();
        }

        return members;
    }


    public static ObservableList<Resident> getResidents() {
        ObservableList<Resident> residentsList = FXCollections.observableArrayList();
        String query = "SELECT HoTen, GioiTinh, NgaySinh, SoCMND, QueQuan, SoDienThoai, "
                + "NgheNghiep, DanToc, QuocTich, TrinhDoHocVan, TrangThai, ThongTinBoSung, MaHoGiaDinh "
                + "FROM nguoithue";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Lấy dữ liệu từ ResultSet
                String hoTen = resultSet.getString("HoTen");
                String gioiTinh = resultSet.getString("GioiTinh");
                Date ngaySinh = resultSet.getDate("NgaySinh");
                String soCMND = resultSet.getString("SoCMND");
                String queQuan = resultSet.getString("QueQuan");
                String soDienThoai = resultSet.getString("SoDienThoai");
                String ngheNghiep = resultSet.getString("NgheNghiep");
                String danToc = resultSet.getString("DanToc");
                String quocTich = resultSet.getString("QuocTich");
                String trinhDoHocVan = resultSet.getString("TrinhDoHocVan");
                String trangThai = resultSet.getString("TrangThai");
                String thongTinBoSung = resultSet.getString("ThongTinBoSung");
                String maHoGiaDinh = resultSet.getString("MaHoGiaDinh");

                // Tạo đối tượng Resident và thêm vào danh sách
                residentsList.add(new Resident(
                        hoTen, gioiTinh, ngaySinh, soCMND, queQuan, soDienThoai,
                        ngheNghiep, danToc, quocTich, trinhDoHocVan, trangThai, thongTinBoSung, maHoGiaDinh
                ));
            }

        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi tìm người thuê", "Không thể tìm danh sách người thuê", "Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi tìm người thuê", "Không thể tìm danh sách người thuê", "Có lỗi xảy ra: " + e.getMessage());
            e.printStackTrace();
        }

        return residentsList;
    }

    public static void deleteResident(Resident resident) {
        String query = "DELETE FROM nguoithue WHERE SoCMND = ?";
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Gán giá trị cho tham số SoCMND
            preparedStatement.setString(1, resident.getIDcard());

            // Thực thi câu lệnh DELETE
            int rowsAffected = preparedStatement.executeUpdate();

            // Kiểm tra kết quả
            if (rowsAffected > 0) {
                System.out.println("Xóa người thuê thành công!");
            } else {
                System.out.println("Không tìm thấy người thuê với CMND: " + resident.getIDcard());
            }

        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi xóa người thuê", "Không thể xóa người thuê", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi xóa người thuê", "Không thể xóa người thuê", "Có lỗi xảy ra: " + e.getMessage());
        }
    }
    public static ObservableList<Resident> getResidentByHouseholdID(HouseHold houseHold) {
        ObservableList<Resident> residentsList = FXCollections.observableArrayList();
        String query = "SELECT HoTen, GioiTinh, NgaySinh, SoCMND, QueQuan, SoDienThoai, "
                + "NgheNghiep, DanToc, QuocTich, TrinhDoHocVan, TrangThai, ThongTinBoSung, MaHoGiaDinh"
                + "FROM nguoithue WHERE MaHoGiaDinh = ?";

        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Gán tham số cho câu truy vấn
            preparedStatement.setString(1, houseHold.getHouseHoldID());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Lấy dữ liệu từ ResultSet
                    String hoTen = resultSet.getString("HoTen");
                    String gioiTinh = resultSet.getString("GioiTinh");
                    Date ngaySinh = resultSet.getDate("NgaySinh");
                    String soCMND = resultSet.getString("SoCMND");
                    String queQuan = resultSet.getString("QueQuan");
                    String soDienThoai = resultSet.getString("SoDienThoai");
                    String ngheNghiep = resultSet.getString("NgheNghiep");
                    String danToc = resultSet.getString("DanToc");
                    String quocTich = resultSet.getString("QuocTich");
                    String trinhDoHocVan = resultSet.getString("TrinhDoHocVan");
                    String trangThai = resultSet.getString("TrangThai");
                    String thongTinBoSung = resultSet.getString("ThongTinBoSung");
                    String maHoGiaDinh = resultSet.getString("MaHoGiaDinh");
                    // Tạo đối tượng Resident và thêm vào danh sách
                    residentsList.add(new Resident(
                            hoTen, gioiTinh, ngaySinh, soCMND, queQuan, soDienThoai,
                            ngheNghiep, danToc, quocTich, trinhDoHocVan, trangThai, thongTinBoSung, maHoGiaDinh
                    ));
                }
            }

        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi tìm người thuê", "Không thể tìm người thuê trong căn hộ", "Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi tìm người thuê", "Không thể tìm người thuê trong căn hộ", "Có lỗi xảy ra: " + e.getMessage());
            e.printStackTrace();
        }

        return residentsList;
    }

    public static int countByType(String type) {
        String query = "SELECT * FROM nguoithue WHERE TrangThai = ?";
        int count = 0; // Biến để đếm số lượng kết quả
        try (Connection connection = SQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, type);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Chỉ đếm số lượng người thuê, không cần lấy thông tin chi tiết
                count++;
            }

        } catch (SQLException e) {
            BaseDAO.showErrorAlert("Lỗi tìm người thuê", "Không thể tìm người thuê trong căn hộ", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            BaseDAO.showErrorAlert("Lỗi tìm người thuê", "Có lỗi xảy ra khi tìm người thuê", "Có lỗi xảy ra: " + e.getMessage());
        }
        return count; // Trả về số lượng người thuê theo loại
    }


}
