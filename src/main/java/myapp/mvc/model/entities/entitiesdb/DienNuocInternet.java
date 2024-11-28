package myapp.mvc.model.entities.entitiesdb;

public class DienNuocInternet {
    // Thuộc tính
    private String maHoGiaDinh;
    private String maKHDien;
    private String maKHNuoc;
    private String thongTinBoSung;
    private String maKHInternet;
    private String NCCDien;
    private String NCCNuoc;
    private String NCCInternet;

    // Constructor không tham số (mặc định)
    public DienNuocInternet() {
    }

    // Constructor đầy đủ tham số
    public DienNuocInternet(String maHoGiaDinh, String maKHDien, String maKHNuoc, String thongTinBoSung, String maKHInternet, String NCCDien, String NCCNuoc, String NCCInternet) {
        this.maHoGiaDinh = maHoGiaDinh;
        this.maKHDien = maKHDien;
        this.maKHNuoc = maKHNuoc;
        this.thongTinBoSung = thongTinBoSung;
        this.maKHInternet = maKHInternet;
        this.NCCDien = NCCDien;
        this.NCCNuoc = NCCNuoc;
        this.NCCInternet = NCCInternet;
    }

    // Getter và Setter
    public String getMaHoGiaDinh() {
        return maHoGiaDinh;
    }

    public void setMaHoGiaDinh(String maHoGiaDinh) {
        this.maHoGiaDinh = maHoGiaDinh;
    }

    public String getMaKHDien() {
        return maKHDien;
    }

    public void setMaKHDien(String maKHDien) {
        this.maKHDien = maKHDien;
    }

    public String getMaKHNuoc() {
        return maKHNuoc;
    }

    public void setMaKHNuoc(String maKHNuoc) {
        this.maKHNuoc = maKHNuoc;
    }

    public String getThongTinBoSung() {
        return thongTinBoSung;
    }

    public void setThongTinBoSung(String thongTinBoSung) {
        this.thongTinBoSung = thongTinBoSung;
    }

    public String getMaKHInternet() {
        return maKHInternet;
    }

    public void setMaKHInternet(String maKHInternet) {
        this.maKHInternet = maKHInternet;
    }

    public String getNCCDien() {
        return NCCDien;
    }

    public void setNCCDien(String NCCDien) {
        this.NCCDien = NCCDien;
    }

    public String getNCCNuoc() {
        return NCCNuoc;
    }

    public void setNCCNuoc(String NCCNuoc) {
        this.NCCNuoc = NCCNuoc;
    }

    public String getNCCInternet() {
        return NCCInternet;
    }

    public void setNCCInternet(String NCCInternet) {
        this.NCCInternet = NCCInternet;
    }

    // Phương thức toString
    @Override
    public String toString() {
        return "DienNuocInternet{" +
                "maHoGiaDinh='" + maHoGiaDinh + '\'' +
                ", maKHDien='" + maKHDien + '\'' +
                ", maKHNuoc='" + maKHNuoc + '\'' +
                ", thongTinBoSung='" + thongTinBoSung + '\'' +
                ", maKHInternet='" + maKHInternet + '\'' +
                ", NCCDien='" + NCCDien + '\'' +
                ", NCCNuoc='" + NCCNuoc + '\'' +
                ", NCCInternet='" + NCCInternet + '\'' +
                '}';
    }
}
