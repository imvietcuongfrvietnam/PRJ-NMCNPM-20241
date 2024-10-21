package model.object;

import java.util.Date;

public class ThongTinNguoiDung {
    private int maTaiKhoan;       // Mã tài khoản (foreign key)
    private String ten;           // Tên người dùng
    private String soCMND;        // Số CMND (primary key)
    private Date ngaySinh;        // Ngày sinh
    private String email;         // Email
    private String queQuan;       // Quê quán
    private String dienThoai;     // Điện thoại

    // Constructor
    public ThongTinNguoiDung(int maTaiKhoan, String ten, String soCMND, Date ngaySinh, String email, String queQuan, String dienThoai) {
        this.maTaiKhoan = maTaiKhoan;
        this.ten = ten;
        this.soCMND = soCMND;
        this.ngaySinh = ngaySinh;
        this.email = email;
        this.queQuan = queQuan;
        this.dienThoai = dienThoai;
    }

    // Getters and Setters
    public int getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(int maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    @Override
    public String toString() {
        return "ThongTinNguoiDung{" +
                "maTaiKhoan=" + maTaiKhoan +
                ", ten='" + ten + '\'' +
                ", soCMND='" + soCMND + '\'' +
                ", ngaySinh=" + ngaySinh +
                ", email='" + email + '\'' +
                ", queQuan='" + queQuan + '\'' +
                ", dienThoai='" + dienThoai + '\'' +
                '}';
    }
}
