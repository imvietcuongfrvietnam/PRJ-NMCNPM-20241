package myapp.model.entities;

public class Fee {
    private String feeID;
    private String feeName;
    private String houseHoldID;
    private String amount;
    private String expDate;
    private String status;
    private String note;

    public Fee(String feeID, String feeName, String houseHoldID, String amount, String expDate, String status, String note) {
        this.feeID = feeID;
        this.feeName = feeName;
        this.houseHoldID = houseHoldID;
        this.amount = amount;
        this.expDate = expDate;
        this.status = status;
        this.note = note;
    }

    public Fee() {
    }

    public String getFeeID() {
        return feeID;
    }

    public void setFeeID(String feeID) {
        this.feeID = feeID;
    }

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public String getHouseHoldID() {
        return houseHoldID;
    }

    public void setHouseHoldID(String houseHoldID) {
        this.houseHoldID = houseHoldID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
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
