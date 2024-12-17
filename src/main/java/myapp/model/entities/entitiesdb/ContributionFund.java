package myapp.model.entities.entitiesdb;

import java.math.BigDecimal;
import java.sql.Date;

public class ContributionFund {
    private String fundName;
    private String fundID;
    private BigDecimal amount;
    private Date periodOfTime;
    private Date endTime;

    public ContributionFund(String fundName, String fundID, BigDecimal amount, Date periodOfTime, Date endTime) {
        this.fundName = fundName;
        this.fundID = fundID;
        this.amount = amount;
        this.periodOfTime = periodOfTime;
        this.endTime = endTime;
    }

    public String getFundName() {
        return fundName;
    }

    public String getFundID() {
        return fundID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getPeriodOfTime() {
        return periodOfTime;
    }
}

