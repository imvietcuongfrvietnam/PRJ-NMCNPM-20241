package myapp.model.object;

public class Fee {
    private String feeId;
    private String feeType; // Loại phí (phí dịch vụ, phí quản lý, phí đóng góp)
    private double amount;
    private String householdId; // Liên kết với hộ gia đình nào

    public Fee(String feeId, String feeType, double amount, String householdId) {
        this.feeId = feeId;
        this.feeType = feeType;
        this.amount = amount;
        this.householdId = householdId;
    }

    // Getter và Setter
    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }
}
