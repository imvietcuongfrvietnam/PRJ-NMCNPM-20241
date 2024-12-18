package myapp.model.entities.entitiesdb;

import java.math.BigDecimal;
import java.sql.Date;

public class Bill {
    private String billID;
    private String billName;
    private String houseHoldID;
    private String supplierName;
    private BigDecimal amount;
    private Date expDate;
    private String status;
    private String note;

    public Bill(String billID, String billName, String houseHoldID, String supplierName, BigDecimal amount, Date expDate, String status, String note) {
        this.billID = billID;
        this.billName = billName;
        this.houseHoldID = houseHoldID;
        this.supplierName = supplierName;
        this.amount = amount;
        this.expDate = expDate;
        this.status = status;
        this.note = note;
    }

    public Bill() {
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getHouseHoldID() {
        return houseHoldID;
    }

    public void setHouseHoldID(String houseHoldID) {
        this.houseHoldID = houseHoldID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
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