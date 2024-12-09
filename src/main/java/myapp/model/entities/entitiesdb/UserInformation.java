package myapp.model.entities.entitiesdb;

import java.sql.Date;

public class UserInformation {
    private String soCMND;
    private String ten;
    private String email;
    private String queQuan;
    private String dienThoai;
    private Date ngaySinh;
    private String diaChi;
    private String gioiTinh;


    public UserInformation(String soCMND, String ten, String email, String queQuan, String dienThoai, Date ngaySinh, String diaChi, String gioiTinh) {
        this.soCMND = soCMND;
        this.ten = ten;
        this.email = email;
        this.queQuan = queQuan;
        this.dienThoai = dienThoai;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    // Getters and Setters
    public String getSoCMND() { return soCMND; }
    public String getTen() { return ten; }
    public String getEmail() { return email; }
    public String getQueQuan() { return queQuan; }
    public String getDienThoai() { return dienThoai; }
    public java.util.Date getNgaySinh(){ return ngaySinh;}
}
