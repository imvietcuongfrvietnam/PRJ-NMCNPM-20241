package myapp.mvc.model.entities.entitiesdb;

import java.math.BigDecimal;
import java.time.LocalDate;


public class HoaDonDien {
    private String maHD;
    private String maKH;
    private BigDecimal chiSoDienSuDung;

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public BigDecimal getChiSoDienSuDung() {
        return chiSoDienSuDung;
    }

    public void setChiSoDienSuDung(BigDecimal chiSoDienSuDung) {
        this.chiSoDienSuDung = chiSoDienSuDung;
    }

    public LocalDate getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(LocalDate ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public String getThongTinBoSung() {
        return thongTinBoSung;
    }

    public void setThongTinBoSung(String thongTinBoSung) {
        this.thongTinBoSung = thongTinBoSung;
    }

    public BigDecimal getTienDien() {
        return tienDien;
    }

    public void setTienDien(BigDecimal tienDien) {
        this.tienDien = tienDien;
    }

    private LocalDate ngayHetHan;
    private String thongTinBoSung;
    private BigDecimal tienDien;
}
