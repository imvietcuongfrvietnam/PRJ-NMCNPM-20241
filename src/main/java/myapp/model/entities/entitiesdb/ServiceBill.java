package myapp.model.entities.entitiesdb;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Represents a service bill in the system, mapping to the "phidichvu" database table.
 */
public class ServiceBill {
    private int id;
    private String maHoGiaDinh;
    private BigDecimal soTienTrenM2;
    private Date ngayHetHan;
    private String trangThai;
    private String ghiChu;

    /**
     * Constructs a new ServiceBill instance.
     *
     * @param id            the unique identifier of the service bill
     * @param maHoGiaDinh   the household ID associated with the bill
     * @param soTienTrenM2  the cost per square meter
     * @param ngayHetHan    the expiration date of the bill
     * @param trangThai     the status of the bill (e.g., "Đã thanh toán", "Chưa thanh toán")
     * @param ghiChu        additional notes about the bill
     */
    public ServiceBill(int id, String maHoGiaDinh, BigDecimal soTienTrenM2, Date ngayHetHan, String trangThai, String ghiChu) {
        this.id = id;
        this.maHoGiaDinh = maHoGiaDinh;
        this.soTienTrenM2 = soTienTrenM2;
        this.ngayHetHan = ngayHetHan;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    /**
     * Gets the ID of the service bill.
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the service bill.
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
     * Gets the cost per square meter.
     *
     * @return the cost per square meter
     */
    public BigDecimal getSoTienTrenM2() {
        return soTienTrenM2;
    }

    /**
     * Sets the cost per square meter.
     *
     * @param soTienTrenM2 the new cost per square meter
     */
    public void setSoTienTrenM2(BigDecimal soTienTrenM2) {
        this.soTienTrenM2 = soTienTrenM2;
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
