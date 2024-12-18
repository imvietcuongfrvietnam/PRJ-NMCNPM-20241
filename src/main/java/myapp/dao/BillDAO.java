package myapp.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.Bill;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillDAO {
    public static ObservableList<Bill> getBills() {
        ObservableList<Bill> billsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM quanlyhoadon";

        try (PreparedStatement statement = SQLConnector.getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                billsList.add(new Bill(
                        resultSet.getString("MaHoaDon"),
                        resultSet.getString("TenHoaDon"),
                        resultSet.getString("MaHoGiaDinh"),
                        resultSet.getString("TenNhaCungCap"),
                        resultSet.getBigDecimal("SoTien"),
                        resultSet.getDate("NgayHetHan"),
                        resultSet.getString("TinhTrang"),
                        resultSet.getString("GhiChu")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            BaseDAO.showErrorAlert("Lỗi tìm hóa đơn", "Không thể tìm hóa đơn", "Lỗi SQL: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            BaseDAO.showErrorAlert("Lỗi tìm hóa đơn", "Có lỗi xảy ra khi tìm hóa đơn", "Có lỗi xảy ra: " + e.getMessage());
        }
        return billsList;
    }
}
