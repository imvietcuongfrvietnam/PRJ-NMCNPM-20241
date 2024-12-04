package myapp.model.entities.entitiesdb;

public class Apartment {
    private String maCanHo; // Mã căn hộ
    private int tang; // Tầng
    private int dienTich; // Diện tích
    private String tinhTrang; // Tình trạng
    private String thongTinBoSung; // Thông tin bổ sung

    public Apartment(String maCanHo, int tang, int dienTich, String tinhTrang, String thongTinBoSung) {
        this.maCanHo = maCanHo;
        this.tang = tang;
        this.dienTich = dienTich;
        this.tinhTrang = tinhTrang;
        this.thongTinBoSung = thongTinBoSung;
    }

    public Apartment() {
    }

    public String getMaCanHo() {
        return maCanHo;
    }

    public void setMaCanHo(String maCanHo) {
        this.maCanHo = maCanHo;
    }

    public int getTang() {
        return tang;
    }

    public void setTang(int tang) {
        this.tang = tang;
    }

    public int getDienTich() {
        return dienTich;
    }

    public void setDienTich(int dienTich) {
        this.dienTich = dienTich;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getThongTinBoSung() {
        return thongTinBoSung;
    }

    public void setThongTinBoSung(String thongTinBoSung) {
        this.thongTinBoSung = thongTinBoSung;
    }
}
