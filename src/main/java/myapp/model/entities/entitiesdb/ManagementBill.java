package myapp.model.entities.entitiesdb;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Represents a management bill in the system, mapping to the "phiquanly" database table.
 */
public class ManagementBill {
    private int id;
    private String maHoGiaDinh;
    private BigDecimal soTienTrenThang;
    private Date ngayHetHan;
    private String trangThai;
    private String ghiChu;

    /**
     * Constructs a new ManagementBill instance.
     *
     * @param id            the unique identifier of the management bill
     * @param maHoGiaDinh   the household ID associated with the bill
     * @param soTienTrenThang  the cost per month
     * @param ngayHetHan    the expiration date of the bill
     * @param trangThai     the status of the bill (e.g., "Đã thanh toán", "Chưa thanh toán")
     * @param ghiChu        additional notes about the bill
     */
    public ManagementBill(int id, String maHoGiaDinh, BigDecimal soTienTrenThang, Date ngayHetHan, String trangThai, String ghiChu) {
        this.id = id;
        this.maHoGiaDinh = maHoGiaDinh;
        this.soTienTrenThang = soTienTrenThang;
        this.ngayHetHan = ngayHetHan;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    /**
     * Gets the ID of the management bill.
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the management bill.
     *
     * @param id the new ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the household ID associated with the bill.
     *
     * @return the household ID
     */
    public String getMaHoGiaDinh() {
        return maHoGiaDinh;
    }

    /**
     * Sets the household ID associated with the bill.
     *
     * @param maHoGiaDinh the new household ID
     */
    public void setMaHoGiaDinh(String maHoGiaDinh) {
        this.maHoGiaDinh = maHoGiaDinh;
    }

    /**
     * Gets the cost per month.
     *
     * @return the cost per month
     */
    public BigDecimal getSoTienTrenThang() {
        return soTienTrenThang;
    }

    /**
     * Sets the cost per month.
     *
     * @param soTienTrenThang the new cost per month
     */
    public void setSoTienTrenThang(BigDecimal soTienTrenThang) {
        this.soTienTrenThang = soTienTrenThang;
    }

    /**
     * Gets the expiration date of the bill.
     *
     * @return the expiration date
     */
    public Date getNgayHetHan() {
        return ngayHetHan;
    }

    /**
     * Sets the expiration date of the bill.
     *
     * @param ngayHetHan the new expiration date
     */
    public void setNgayHetHan(Date ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    /**
     * Gets the status of the bill.
     *
     * @return the status
     */
    public String getTrangThai() {
        return trangThai;
    }

    /**
     * Sets the status of the bill.
     *
     * @param trangThai the new status
     */
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    /**
     * Gets additional notes about the bill.
     *
     * @return the notes
     */
    public String getGhiChu() {
        return ghiChu;
    }

    /**
     * Sets additional notes about the bill.
     *
     * @param ghiChu the new notes
     */
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
