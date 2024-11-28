package myapp.mvc.model.entities.entitiesdb;

import java.time.LocalDate;


public class HoGiaDinh {
    private String maHoGiaDinh;
    private String maPhongThue;

    public String getMaHoGiaDinh() {
        return maHoGiaDinh;
    }

    public void setMaHoGiaDinh(String maHoGiaDinh) {
        this.maHoGiaDinh = maHoGiaDinh;
    }

    public String getMaPhongThue() {
        return maPhongThue;
    }

    public void setMaPhongThue(String maPhongThue) {
        this.maPhongThue = maPhongThue;
    }

    public LocalDate getNgayChuyenVao() {
        return ngayChuyenVao;
    }

    public void setNgayChuyenVao(LocalDate ngayChuyenVao) {
        this.ngayChuyenVao = ngayChuyenVao;
    }

    public LocalDate getNgayChuyenRa() {
        return ngayChuyenRa;
    }

    public void setNgayChuyenRa(LocalDate ngayChuyenRa) {
        this.ngayChuyenRa = ngayChuyenRa;
    }

    public String getSoCMNDChuHo() {
        return soCMNDChuHo;
    }

    public void setSoCMNDChuHo(String soCMNDChuHo) {
        this.soCMNDChuHo = soCMNDChuHo;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    private LocalDate ngayChuyenVao;
    private LocalDate ngayChuyenRa;
    private String soCMNDChuHo;
    private String trangThai;

}
