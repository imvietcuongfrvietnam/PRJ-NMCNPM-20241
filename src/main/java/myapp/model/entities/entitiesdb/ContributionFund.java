package myapp.model.entities.entitiesdb;

import java.math.BigDecimal;
import java.sql.Date;

public class ContributionFund {
    private String fundName;
    private int fundID;
    private String amount;
    private Date periodOfTime;
    private Date endTime;

    public ContributionFund(String fundName, int fundID, String amount, Date periodOfTime, Date endTime) {
        this.fundName = fundName;
        this.fundID = fundID;
        this.amount = amount;
        this.periodOfTime = periodOfTime;
        this.endTime = endTime;
    }

    public String getFundName() {
        return fundName;
    }

    public int getFundID() {
        return fundID;
    }

    public String getAmount() {
        return amount;
    }

    public Date getPeriodOfTime() {
        return periodOfTime;
    }
}

