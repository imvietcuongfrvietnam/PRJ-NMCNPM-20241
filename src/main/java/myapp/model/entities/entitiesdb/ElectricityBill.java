package myapp.model.entities.entitiesdb;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ElectricityBill {
    private String maHD;
    private String maKH;
    private BigDecimal chiSoDienSuDung;
    private LocalDate ngayHetHan;
    private String thongTinBoSung;
    private BigDecimal tienDien;

    // Constructor
    public ElectricityBill(String maHD, String maKH, BigDecimal chiSoDienSuDung, LocalDate ngayHetHan,
                           String thongTinBoSung, BigDecimal tienDien) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.chiSoDienSuDung = chiSoDienSuDung;
        this.ngayHetHan = ngayHetHan;
        this.thongTinBoSung = thongTinBoSung;
        this.tienDien = tienDien;
    }

    // Getters and Setters
    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public BigDecimal getChiSoDienSuDung() { return chiSoDienSuDung; }
    public void setChiSoDienSuDung(BigDecimal chiSoDienSuDung) { this.chiSoDienSuDung = chiSoDienSuDung; }

    public LocalDate getNgayHetHan() { return ngayHetHan; }
    public void setNgayHetHan(LocalDate ngayHetHan) { this.ngayHetHan = ngayHetHan; }

    public String getThongTinBoSung() { return thongTinBoSung; }
    public void setThongTinBoSung(String thongTinBoSung) { this.thongTinBoSung = thongTinBoSung; }

    public BigDecimal getTienDien() { return tienDien; }
    public void setTienDien(BigDecimal tienDien) { this.tienDien = tienDien; }
}
