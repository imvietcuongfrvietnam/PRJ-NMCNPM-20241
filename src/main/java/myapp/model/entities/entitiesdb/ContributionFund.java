package myapp.model.entities.entitiesdb;

public class ContributionFund {
    private String fundName;
    private String fundID;
    private String amount;
    private String periodOfTime;

    public ContributionFund(String fundName, String fundID, String amount, String periodOfTime) {
        this.fundName = fundName;
        this.fundID = fundID;
        this.amount = amount;
        this.periodOfTime = periodOfTime;
    }

    public String getFundName() {
        return fundName;
    }

    public String getFundID() {
        return fundID;
    }

    public String getAmount() {
        return amount;
    }

    public String getPeriodOfTime() {
        return periodOfTime;
    }
}

