package myapp.model.entities.entitiesdb;

import java.sql.Date;

public class Vehicle {
    private String houseHoldID; // Mã hộ
    private String vehicleType; // Loại phương tiện
    private String licensePlate; // Biển số (chính khóa)
    private Date startDate; // Ngày bắt đầu
    private Date endDate; // Ngày kết thúc
    private String note; // Thông tin bổ sung
    // Constructor
    public Vehicle(String houseHoldID, String vehicleType, String licensePlate, Date startDate, Date endDate, String note) {
        this.houseHoldID = houseHoldID;
        this.vehicleType = vehicleType;
        this.licensePlate = licensePlate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.note = note;
    }
    public Vehicle() {

    }

    // Getter và Setter cho từng thuộc tính
    public String getHouseHoldID() {
        return houseHoldID;
    }

    public void setHouseHoldID(String houseHoldID) {
        this.houseHoldID = houseHoldID;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}