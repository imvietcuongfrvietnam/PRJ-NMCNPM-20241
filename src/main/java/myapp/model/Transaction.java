package model;

import java.util.Date;

public class Transaction {
    private String transactionId;
    private String feeId;
    private String householdId;
    private double paidAmount;
    private Date paymentDate;

    public Transaction(String transactionId, String feeId, String householdId, double paidAmount, Date paymentDate) {
        this.transactionId = transactionId;
        this.feeId = feeId;
        this.householdId = householdId;
        this.paidAmount = paidAmount;
        this.paymentDate = paymentDate;
    }

    // Getter và Setter
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
