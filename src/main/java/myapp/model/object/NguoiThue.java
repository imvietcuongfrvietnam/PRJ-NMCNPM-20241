package model.object;

import java.util.Date;

public class NguoiThue {
    private String soCMND;
    private String gioiTinh;
    private Date ngaySinh;
    private String queQuan;
    private String hoTen;
    private String ngheNghiep;
    private String trangThai;
    private String danToc;
    private String quocTich;
    private String trinhDoHocVan;
    private String thongTinBoSung;
    private String maHoGiaDinh;

    // Constructor
    public NguoiThue(String soCMND, String gioiTinh, Date ngaySinh, String queQuan, String hoTen,
                     String ngheNghiep, String trangThai, String danToc, String quocTich,
                     String trinhDoHocVan, String thongTinBoSung, String maHoGiaDinh) {
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

    // Getters and Setters
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

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
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

    @Override
    public String toString() {
        return "NguoiThue{" +
                "soCMND='" + soCMND + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", ngaySinh=" + ngaySinh +
                ", queQuan='" + queQuan + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", ngheNghiep='" + ngheNghiep + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", danToc='" + danToc + '\'' +
                ", quocTich='" + quocTich + '\'' +
                ", trinhDoHocVan='" + trinhDoHocVan + '\'' +
                ", thongTinBoSung='" + thongTinBoSung + '\'' +
                ", maHoGiaDinh='" + maHoGiaDinh + '\'' +
                '}';
    }
}
