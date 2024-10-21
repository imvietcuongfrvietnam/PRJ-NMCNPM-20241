package model.object;


import java.util.Date;
public class TaiKhoanNguoiDung {
    private int maTaiKhoan;           // Mã tài khoản (primary key)
    private String vaiTro;            // Vai trò
    private String tenDangNhap;       // Tên đăng nhập (unique)
    private String matKhau;           // Mật khẩu
    private Date ngayTaoTaiKhoan;     // Ngày tạo tài khoản

    // Constructor
    public TaiKhoanNguoiDung(int maTaiKhoan, String vaiTro, String tenDangNhap, String matKhau, Date ngayTaoTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
        this.vaiTro = vaiTro;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.ngayTaoTaiKhoan = ngayTaoTaiKhoan;
    }

    // Getters and Setters
    public int getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(int maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public Date getNgayTaoTaiKhoan() {
        return ngayTaoTaiKhoan;
    }

    public void setNgayTaoTaiKhoan(Date ngayTaoTaiKhoan) {
        this.ngayTaoTaiKhoan = ngayTaoTaiKhoan;
    }

    @Override
    public String toString() {
        return "TaiKhoanNguoiDung{" +
                "maTaiKhoan=" + maTaiKhoan +
                ", vaiTro='" + vaiTro + '\'' +
                ", tenDangNhap='" + tenDangNhap + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", ngayTaoTaiKhoan=" + ngayTaoTaiKhoan +
                '}';
    }
}
