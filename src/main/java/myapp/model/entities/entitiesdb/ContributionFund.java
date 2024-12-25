package myapp.model.entities.entitiesdb;

public class ContributionFund {
    private String fundName;
    private String fundID;
    private String houseHoldID;
    private String amount;
    private String startDate;
    private String endDate;
    private String contribitonDate;
    private String status;
    private String note;

    // Constructor
    public ContributionFund(String fundName, String fundID, String amount, String startDate, String endDate) {
        this.fundName = fundName;
        this.fundID = fundID;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ContributionFund(String fundName, String fundID, String houseHoldID, String amount, String contribitonDate, String status, String note) {
        this.fundName = fundName;
        this.fundID = fundID;
        this.houseHoldID = houseHoldID;
        this.amount = amount;
        this.contribitonDate = contribitonDate;
        this.status = status;
        this.note = note;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundID() {
        return fundID;
    }

    public void setFundID(String fundID) {
        this.fundID = fundID;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getContribitonDate() {
        return contribitonDate;
    }

    public void setContribitonDate(String contribitonDate) {
        this.contribitonDate = contribitonDate;
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
