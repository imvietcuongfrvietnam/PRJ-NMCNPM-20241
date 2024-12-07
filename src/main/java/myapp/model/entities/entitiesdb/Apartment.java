package myapp.model.entities.entitiesdb;

public class Apartment {
    private String apartmentID; // Mã căn hộ
    private int floor; // Tầng
    private int area; // Diện tích
    private String houseHoldID;
    private String status; // Tình trạng
    private String note; // Thông tin bổ sung

    public Apartment(String apartmentID, int floor, int area, String status, String note) {
        this.apartmentID = apartmentID;
        this.floor = floor;
        this.area = area;
        this.status = status;
        this.note = note;
    }

    public Apartment() {
    }

    public String getApartmentID() {
        return apartmentID;
    }

    public void setApartmentID(String apartmentID) {
        this.apartmentID = apartmentID;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}