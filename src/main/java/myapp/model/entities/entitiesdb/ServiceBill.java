package myapp.model.entities.entitiesdb;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ServiceBill {
    private int maDichVu;
    private String maHD;
    private String maHoGiaDinh;
    private BigDecimal soTien;
    private LocalDate ngayHetHan;
    private String thongTinBoSung;

    // Constructor
    public ServiceBill(int maDichVu, String maHD, String maHoGiaDinh, BigDecimal soTien,
                       LocalDate ngayHetHan, String thongTinBoSung) {
        this.maDichVu = maDichVu;
        this.maHD = maHD;
        this.maHoGiaDinh = maHoGiaDinh;
        this.soTien = soTien;
        this.ngayHetHan = ngayHetHan;
        this.thongTinBoSung = thongTinBoSung;
    }

    // Getters and Setters
    public int getMaDichVu() { return maDichVu; }
    public void setMaDichVu(int maDichVu) { this.maDichVu = maDichVu; }

    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public String getMaHoGiaDinh() { return maHoGiaDinh; }
    public void setMaHoGiaDinh(String maHoGiaDinh) { this.maHoGiaDinh = maHoGiaDinh; }

    public BigDecimal getSoTien() { return soTien; }
    public void setSoTien(BigDecimal soTien) { this.soTien = soTien; }

    public LocalDate getNgayHetHan() { return ngayHetHan; }
    public void setNgayHetHan(LocalDate ngayHetHan) { this.ngayHetHan = ngayHetHan; }

    public String getThongTinBoSung() { return thongTinBoSung; }
    public void setThongTinBoSung(String thongTinBoSung) { this.thongTinBoSung = thongTinBoSung; }
}
