package myapp.model.entities.entitiesdb;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

public class PaymentHistory {

    private String maHoGiaDinh;     // Mã hộ gia đình
    private BigDecimal soTienDaDong; // Số tiền đã đóng
    private String loaiPhiThanhToan; // Loại phí thanh toán
    private Date ngayDong;     // Ngày đóng

    // Constructor
    public PaymentHistory(String maHoGiaDinh, BigDecimal soTienDaDong, String loaiPhiThanhToan, Date ngayDong) {
        this.maHoGiaDinh = maHoGiaDinh;
        this.soTienDaDong = soTienDaDong;
        this.loaiPhiThanhToan = loaiPhiThanhToan;
        this.ngayDong = ngayDong;
    }

    // Getter and Setter methods

    public String getMaHoGiaDinh() {
        return maHoGiaDinh;
    }

    public void setMaHoGiaDinh(String maHoGiaDinh) {
        this.maHoGiaDinh = maHoGiaDinh;
    }

    public BigDecimal getSoTienDaDong() {
        return soTienDaDong;
    }

    public void setSoTienDaDong(BigDecimal soTienDaDong) {
        this.soTienDaDong = soTienDaDong;
    }

    public String getLoaiPhiThanhToan() {
        return loaiPhiThanhToan;
    }

    public void setLoaiPhiThanhToan(String loaiPhiThanhToan) {
        this.loaiPhiThanhToan = loaiPhiThanhToan;
    }

    public Date getNgayDong() {
        return ngayDong;
    }

    public void setNgayDong(Date ngayDong) {
        this.ngayDong = ngayDong;
    }

}
