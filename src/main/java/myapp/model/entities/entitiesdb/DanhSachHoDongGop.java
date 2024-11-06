package myapp.model.entities.entitiesdb;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DanhSachHoDongGop {
    private String maHoDongGop;
    private int maQuy;
    private BigDecimal soTienDongGop;
    private String thongTinBoSung;
    private int id;
    private String dotDongGop;
    private LocalDate ngayDongGop;

    // Constructor
    public DanhSachHoDongGop(String maHoDongGop, int maQuy, BigDecimal soTienDongGop,
                             String thongTinBoSung, int id, String dotDongGop, LocalDate ngayDongGop) {
        this.maHoDongGop = maHoDongGop;
        this.maQuy = maQuy;
        this.soTienDongGop = soTienDongGop;
        this.thongTinBoSung = thongTinBoSung;
        this.id = id;
        this.dotDongGop = dotDongGop;
        this.ngayDongGop = ngayDongGop;
    }

    // Getters and Setters
    public String getMaHoDongGop() { return maHoDongGop; }
    public void setMaHoDongGop(String maHoDongGop) { this.maHoDongGop = maHoDongGop; }

    public int getMaQuy() { return maQuy; }
    public void setMaQuy(int maQuy) { this.maQuy = maQuy; }

    public BigDecimal getSoTienDongGop() { return soTienDongGop; }
    public void setSoTienDongGop(BigDecimal soTienDongGop) { this.soTienDongGop = soTienDongGop; }

    public String getThongTinBoSung() { return thongTinBoSung; }
    public void setThongTinBoSung(String thongTinBoSung) { this.thongTinBoSung = thongTinBoSung; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDotDongGop() { return dotDongGop; }
    public void setDotDongGop(String dotDongGop) { this.dotDongGop = dotDongGop; }

    public LocalDate getNgayDongGop() { return ngayDongGop; }
    public void setNgayDongGop(LocalDate ngayDongGop) { this.ngayDongGop = ngayDongGop; }
}
