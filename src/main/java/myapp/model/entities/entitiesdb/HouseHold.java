package myapp.model.entities.entitiesdb;

public class HouseHold {
    private String houseHoldID;
    private String apartmentID;
    private String moveInDate;
    private String moveOutDate;
    private String residentID;
    private String status;

    public HouseHold(String houseHoldID, String apartmentID, String moveInDate, String moveOutDate, String residentID, String status) {
        this.houseHoldID = houseHoldID;
        this.apartmentID = apartmentID;
        this.moveInDate = moveInDate;
        this.moveOutDate = moveOutDate;
        this.residentID = residentID;
        this.status = status;
    }

    public HouseHold() {
    }

    public HouseHold(String houseHoldID, String apartmentID, String address, String moveInDateValue, String moveOutDateValue, String statusValue, String residentID) {

    }

    public HouseHold(String houseHoldID, String moveInDate, String moveOutDate) {
        this.houseHoldID = houseHoldID;
        this.moveInDate = moveInDate;
        this.moveOutDate = moveOutDate;
    }

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

    public String getMoveInDate() {
        return moveInDate;
    }

    public void setMoveInDate(String moveInDate) {
        this.moveInDate = moveInDate;
    }

    public String getMoveOutDate() {
        return moveOutDate;
    }

    public void setMoveOutDate(String moveOutDate) {
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
