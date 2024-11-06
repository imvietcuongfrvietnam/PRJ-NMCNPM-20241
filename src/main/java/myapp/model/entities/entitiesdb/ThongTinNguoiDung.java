package myapp.model.entities.entitiesdb;

public class ThongTinNguoiDung {
    private String soCMND;
    private String ten;
    private String email;
    private String queQuan;
    private String dienThoai;

    public ThongTinNguoiDung(String soCMND, String ten, String email, String queQuan, String dienThoai) {
        this.soCMND = soCMND;
        this.ten = ten;
        this.email = email;
        this.queQuan = queQuan;
        this.dienThoai = dienThoai;
    }

    // Getters and Setters
    public String getSoCMND() { return soCMND; }
    public String getTen() { return ten; }
    public String getEmail() { return email; }
    public String getQueQuan() { return queQuan; }
    public String getDienThoai() { return dienThoai; }
}
