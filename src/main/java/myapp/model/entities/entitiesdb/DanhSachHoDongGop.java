package myapp.model.entities.entitiesdb;


import myapp.model.communicatedb.update.Updater;
import myapp.model.connectdb.SQLConnector;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;


public class DanhSachHoDongGop {
        private String maHoDongGop;
        private int maQuy;

        public String getMaHoDongGop() {
                return maHoDongGop;
        }

        public void setMaHoDongGop(String maHoDongGop) {
                this.maHoDongGop = maHoDongGop;
        }

        public int getMaQuy() {
                return maQuy;
        }

        public void setMaQuy(int maQuy) {
                this.maQuy = maQuy;
        }

        public void setSoTienDongGop(BigDecimal soTienDongGop) {
                this.soTienDongGop = soTienDongGop;
        }

        public void setThongTinBoSung(String thongTinBoSung) {
                this.thongTinBoSung = thongTinBoSung;
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getDotDongGop() {
                return dotDongGop;
        }

        public void setDotDongGop(String dotDongGop) {
                this.dotDongGop = dotDongGop;
        }

        public LocalDate getNgayDongGop() {
                return ngayDongGop;
        }

        public void setNgayDongGop(LocalDate ngayDongGop) {
                this.ngayDongGop = ngayDongGop;
        }

        private BigDecimal soTienDongGop;
        private String thongTinBoSung;
        private int id;
        private String dotDongGop;
        private LocalDate ngayDongGop;

        public BigDecimal getSoTienDongGop() {
                return this.soTienDongGop;
        }

        public String getThongTinBoSung() {
                return this.thongTinBoSung;
        }

        public static class DienNuocInternetUpdate implements Updater<DienNuocInternet> {
            @Override
            public void update(DienNuocInternet entity) {
                SQLConnector connector = SQLConnector.getInstance();
                String query = "UPDATE diennuocinternet SET NCCDien = ?, NCCNuoc = ?, NCCInternet = ? WHERE MaHoGiaDinh = ?";

                try (Connection connection = connector.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                    preparedStatement.setString(1, entity.getNCCDien());
                    preparedStatement.setString(2, entity.getNCCNuoc());
                    preparedStatement.setString(3, entity.getNCCInternet());
                    preparedStatement.setString(4, entity.getMaHoGiaDinh());

                    preparedStatement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
}
