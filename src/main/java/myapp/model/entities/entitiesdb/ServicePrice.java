package myapp.model.entities.entitiesdb;

import java.math.BigDecimal;

public class ServicePrice {
    private int id;
    private String tenDichVu;
    private BigDecimal donGia;
    private String thongTinBoSung;

    // Constructor
    public ServicePrice(int id, String tenDichVu, BigDecimal donGia, String thongTinBoSung) {
        this.id = id;
        this.tenDichVu = tenDichVu;
        this.donGia = donGia;
        this.thongTinBoSung = thongTinBoSung;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTenDichVu() { return tenDichVu; }
    public void setTenDichVu(String tenDichVu) { this.tenDichVu = tenDichVu; }

    public BigDecimal getDonGia() { return donGia; }
    public void setDonGia(BigDecimal donGia) { this.donGia = donGia; }

    public String getThongTinBoSung() { return thongTinBoSung; }
    public void setThongTinBoSung(String thongTinBoSung) { this.thongTinBoSung = thongTinBoSung; }
}
