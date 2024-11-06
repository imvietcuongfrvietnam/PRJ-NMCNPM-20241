package myapp.model.entities.entitiesdb;

public class GiaDichVuGuiXe {
    private String loaiXe;
    private int giaGuiMotThang;

    // Constructor
    public GiaDichVuGuiXe(String loaiXe, int giaGuiMotThang) {
        this.loaiXe = loaiXe;
        this.giaGuiMotThang = giaGuiMotThang;
    }

    // Getters and Setters
    public String getLoaiXe() { return loaiXe; }
    public void setLoaiXe(String loaiXe) { this.loaiXe = loaiXe; }

    public int getGiaGuiMotThang() { return giaGuiMotThang; }
    public void setGiaGuiMotThang(int giaGuiMotThang) { this.giaGuiMotThang = giaGuiMotThang; }
}
