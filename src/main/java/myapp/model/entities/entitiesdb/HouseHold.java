package myapp.model.entities.entitiesdb;

import java.sql.Date;

/**
 * Lớp HouseHold đại diện cho thông tin của một hộ gia đình.
 */
public class HouseHold {
    private String houseHoldID;
    private String apartmentID;
    private Date moveInDate;
    private Date moveOutDate;
    private String residentID;
    private String status;

    /**
     * Constructor đầy đủ để khởi tạo tất cả các thuộc tính.
     *
     * @param houseHoldID Mã hộ gia đình.
     * @param apartmentID Mã căn hộ.
     * @param moveInDate Ngày chuyển vào.
     * @param moveOutDate Ngày chuyển ra.
     * @param residentID Mã cư dân.
     * @param status Trạng thái của hộ gia đình.
     */
    public HouseHold(String houseHoldID, String apartmentID, Date moveInDate, Date moveOutDate, String residentID, String status) {
        this.houseHoldID = houseHoldID;
        this.apartmentID = apartmentID;
        this.moveInDate = moveInDate;
        this.moveOutDate = moveOutDate;
        this.residentID = residentID;
        this.status = status;
    }

    /**
     * Constructor mặc định.
     */
    public HouseHold() {
    }

    /**
     * Constructor để khởi tạo các thông tin cơ bản.
     *
     * @param houseHoldID Mã hộ gia đình.
     * @param apartmentID Mã căn hộ.
     * @param moveInDate Ngày chuyển vào.
     * @param moveOutDate Ngày chuyển ra.
     */
    public HouseHold(String houseHoldID, String apartmentID, Date moveInDate, Date moveOutDate) {
        this.houseHoldID = houseHoldID;
        this.apartmentID = apartmentID;
        this.moveInDate = moveInDate;
        this.moveOutDate = moveOutDate;
    }

    /**
     * Constructor tối giản để sử dụng cho các truy vấn đơn giản.
     *
     * @param houseHoldID Mã hộ gia đình.
     * @param moveInDate Ngày chuyển vào.
     * @param moveOutDate Ngày chuyển ra.
     */
    public HouseHold(String houseHoldID, Date moveInDate, Date moveOutDate) {
        this.houseHoldID = houseHoldID;
        this.moveInDate = moveInDate;
        this.moveOutDate = moveOutDate;
    }

    // Getters và Setters

    public String getHouseHoldID() {
        return houseHoldID;
    }

    public void setHouseHoldID(String houseHoldID) {
        this.houseHoldID = houseHoldID;
    }

    public String getApartmentID() {
        return apartmentID;
    }

    public void setApartmentID(String apartmentID) {
        this.apartmentID = apartmentID;
    }

    public Date getMoveInDate() {
        return moveInDate;
    }

    public void setMoveInDate(Date moveInDate) {
        this.moveInDate = moveInDate;
    }

    public Date getMoveOutDate() {
        return moveOutDate;
    }

    public void setMoveOutDate(Date moveOutDate) {
        this.moveOutDate = moveOutDate;
    }

    public String getResidentID() {
        return residentID;
    }

    public void setResidentID(String residentID) {
        this.residentID = residentID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
