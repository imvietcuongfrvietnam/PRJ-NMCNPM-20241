package myapp.model.entities.entitiesdb;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HoaDonInternet {
    private String maHD;
    private String maKH;
    private BigDecimal soTien;
    private LocalDate ngayHetHan;
    private String thongTinBoSung;

    // Constructor
    public HoaDonInternet(String maHD, String maKH, BigDecimal soTien, LocalDate ngayHetHan, String thongTinBoSung) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.soTien = soTien;
        this.ngayHetHan = ngayHetHan;
        this.thongTinBoSung = thongTinBoSung;
    }

    // Getters and Setters
    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public BigDecimal getSoTien() { return soTien; }
    public void setSoTien(BigDecimal soTien) { this.soTien = soTien; }

    public LocalDate getNgayHetHan() { return ngayHetHan; }
    public void setNgayHetHan(LocalDate ngayHetHan) { this.ngayHetHan = ngayHetHan; }

    public String getThongTinBoSung() { return thongTinBoSung; }
    public void setThongTinBoSung(String thongTinBoSung) { this.thongTinBoSung = thongTinBoSung; }
}
