package myapp.model.entities.entitiesdb;

public class UserAccount {
    private int maTaiKhoan;
    private String vaiTro;
    private String tenDangNhap;
    private String matKhau;

    public UserAccount(int maTaiKhoan, String vaiTro, String tenDangNhap, String matKhau) {
        this.maTaiKhoan = maTaiKhoan;
        this.vaiTro = vaiTro;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
    }

    // Getters and Setters
    public int getMaTaiKhoan() { return maTaiKhoan; }
    public String getVaiTro() { return vaiTro; }
    public String getTenDangNhap() { return tenDangNhap; }
    public String getMatKhau() { return matKhau; }
}