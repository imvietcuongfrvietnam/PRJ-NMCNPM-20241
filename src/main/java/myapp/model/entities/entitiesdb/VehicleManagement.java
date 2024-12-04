package myapp.model.entities.entitiesdb;

public class VehicleManagement {
    private String maHo; // Mã hộ
    private String loaiPhuongTien; // Loại phương tiện
    private String bienSo; // Biển số (chính khóa)
    private String thongTinBoSung; // Thông tin bổ sung
    private String ngayBatDau; // Ngày bắt đầu
    private String ngayKetThuc; // Ngày kết thúc

    // Constructor
    public VehicleManagement(String maHo, String loaiPhuongTien, String bienSo, String thongTinBoSung, String ngayBatDau, String ngayKetThuc) {
        this.maHo = maHo;
        this.loaiPhuongTien = loaiPhuongTien;
        this.bienSo = bienSo;
        this.thongTinBoSung = thongTinBoSung;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    // Getter và Setter cho từng thuộc tính
    public String getMaHo() {
        return maHo;
    }

    public void setMaHo(String maHo) {
        this.maHo = maHo;
    }

    public String getLoaiPhuongTien() {
        return loaiPhuongTien;
    }

    public void setLoaiPhuongTien(String loaiPhuongTien) {
        this.loaiPhuongTien = loaiPhuongTien;
    }

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public String getThongTinBoSung() {
        return thongTinBoSung;
    }

    public void setThongTinBoSung(String thongTinBoSung) {
        this.thongTinBoSung = thongTinBoSung;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
}
