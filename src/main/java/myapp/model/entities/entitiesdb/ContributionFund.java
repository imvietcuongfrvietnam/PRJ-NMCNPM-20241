package myapp.model.entities.entitiesdb;

public class ContributionFund {
    private String fundName;
    private String fundID;
    private String amount;
    private String startDate;
    private String endDate;

    // Constructor
    public ContributionFund(String fundName, String fundID, String amount, String startDate, String endDate) {
        this.fundName = fundName;
        this.fundID = fundID;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getter methods
    public String getFundName() {
        return fundName;
    }

    public String getFundID() {
        return fundID;
    }

    public String getAmount() {
        return amount;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
