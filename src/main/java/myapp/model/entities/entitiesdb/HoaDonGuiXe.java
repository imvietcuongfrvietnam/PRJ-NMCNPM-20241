package myapp.model.entities.entitiesdb;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HoaDonGuiXe {
    private String maHD;
    private BigDecimal soTien;
    private String bienSo;
    private LocalDate ngayHetHan;
    private String thongTinBoSung;

    // Constructor
    public HoaDonGuiXe(String maHD, BigDecimal soTien, String bienSo, LocalDate ngayHetHan, String thongTinBoSung) {
        this.maHD = maHD;
        this.soTien = soTien;
        this.bienSo = bienSo;
        this.ngayHetHan = ngayHetHan;
        this.thongTinBoSung = thongTinBoSung;
    }

    // Getters and Setters
    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public BigDecimal getSoTien() { return soTien; }
    public void setSoTien(BigDecimal soTien) { this.soTien = soTien; }

    public String getBienSo() { return bienSo; }
    public void setBienSo(String bienSo) { this.bienSo = bienSo; }

    public LocalDate getNgayHetHan() { return ngayHetHan; }
    public void setNgayHetHan(LocalDate ngayHetHan) { this.ngayHetHan = ngayHetHan; }

    public String getThongTinBoSung() { return thongTinBoSung; }
    public void setThongTinBoSung(String thongTinBoSung) { this.thongTinBoSung = thongTinBoSung; }
}
