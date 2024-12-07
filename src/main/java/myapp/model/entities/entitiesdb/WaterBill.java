package myapp.model.entities.entitiesdb;

import java.math.BigDecimal;
import java.time.LocalDate;

public class WaterBill {
    private String maKH;
    private String maHD;
    private LocalDate ngayHetHan;
    private String thongTinBoSung;
    private BigDecimal tienNuoc;

    // Constructor
    public WaterBill(String maKH, String maHD, LocalDate ngayHetHan, String thongTinBoSung, BigDecimal tienNuoc) {
        this.maKH = maKH;
        this.maHD = maHD;
        this.ngayHetHan = ngayHetHan;
        this.thongTinBoSung = thongTinBoSung;
        this.tienNuoc = tienNuoc;
    }

    // Getters and Setters
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public LocalDate getNgayHetHan() { return ngayHetHan; }
    public void setNgayHetHan(LocalDate ngayHetHan) { this.ngayHetHan = ngayHetHan; }

    public String getThongTinBoSung() { return thongTinBoSung; }
    public void setThongTinBoSung(String thongTinBoSung) { this.thongTinBoSung = thongTinBoSung; }

    public BigDecimal getTienNuoc() { return tienNuoc; }
    public void setTienNuoc(BigDecimal tienNuoc) { this.tienNuoc = tienNuoc; }
}
