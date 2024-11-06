package myapp.model.entities.entitiesdb;

public class NguoiThue {
    private String soCMND; // Số CMND (chính khóa)
    private String gioiTinh; // Giới tính
    private String ngaySinh; // Ngày sinh
    private String queQuan; // Quê quán
    private String hoTen; // Họ tên
    private String ngheNghiep; // Nghề nghiệp
    private String trangThai; // Trạng thái
    private String danToc; // Dân tộc
    private String quocTich; // Quốc tịch
    private String trinhDoHocVan; // Trình độ học vấn
    private String thongTinBoSung; // Thông tin bổ sung
    private String maHoGiaDinh; // Mã hộ gia đình

    // Constructor
    public NguoiThue(String soCMND, String gioiTinh, String ngaySinh, String queQuan, String hoTen, String ngheNghiep,
                     String trangThai, String danToc, String quocTich, String trinhDoHocVan, String thongTinBoSung,
                     String maHoGiaDinh) {
        this.soCMND = soCMND;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.queQuan = queQuan;
        this.hoTen = hoTen;
        this.ngheNghiep = ngheNghiep;
        this.trangThai = trangThai;
        this.danToc = danToc;
        this.quocTich = quocTich;
        this.trinhDoHocVan = trinhDoHocVan;
        this.thongTinBoSung = thongTinBoSung;
        this.maHoGiaDinh = maHoGiaDinh;
    }

    // Getter và Setter cho từng thuộc tính
    public String getSoCMND() {
        return soCMND;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgheNghiep() {
        return ngheNghiep;
    }

    public void setNgheNghiep(String ngheNghiep) {
        this.ngheNghiep = ngheNghiep;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getDanToc() {
        return danToc;
    }

    public void setDanToc(String danToc) {
        this.danToc = danToc;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }

    public String getTrinhDoHocVan() {
        return trinhDoHocVan;
    }

    public void setTrinhDoHocVan(String trinhDoHocVan) {
        this.trinhDoHocVan = trinhDoHocVan;
    }

    public String getThongTinBoSung() {
        return thongTinBoSung;
    }

    public void setThongTinBoSung(String thongTinBoSung) {
        this.thongTinBoSung = thongTinBoSung;
    }

    public String getMaHoGiaDinh() {
        return maHoGiaDinh;
    }

    public void setMaHoGiaDinh(String maHoGiaDinh) {
        this.maHoGiaDinh = maHoGiaDinh;
    }
}
