package myapp.model.entities;

public class QuyDongGop {
    private int id;
    private String tenQuy;
    private String moTa;

    // Constructor
    public QuyDongGop(int id, String tenQuy, String moTa) {
        this.id = id;
        this.tenQuy = tenQuy;
        this.moTa = moTa;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTenQuy() { return tenQuy; }
    public void setTenQuy(String tenQuy) { this.tenQuy = tenQuy; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
}