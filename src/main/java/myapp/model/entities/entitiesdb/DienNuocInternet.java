package myapp.model.entities.entitiesdb;

public class DienNuocInternet {
    private String maHoGiaDinh;
    private String maKHDien;
    private String maKHNuoc;
    private String thongTinBoSung;
    private String maKHInternet;
    private String nccDien;
    private String nccNuoc;
    private String nccInternet;

    // Constructor
    public DienNuocInternet(String maHoGiaDinh, String maKHDien, String maKHNuoc, String thongTinBoSung,
                            String maKHInternet, String nccDien, String nccNuoc, String nccInternet) {
        this.maHoGiaDinh = maHoGiaDinh;
        this.maKHDien = maKHDien;
        this.maKHNuoc = maKHNuoc;
        this.thongTinBoSung = thongTinBoSung;
        this.maKHInternet = maKHInternet;
        this.nccDien = nccDien;
        this.nccNuoc = nccNuoc;
        this.nccInternet = nccInternet;
    }

    // Getters and Setters
    public String getMaHoGiaDinh() { return maHoGiaDinh; }
    public void setMaHoGiaDinh(String maHoGiaDinh) { this.maHoGiaDinh = maHoGiaDinh; }

    public String getMaKHDien() { return maKHDien; }
    public void setMaKHDien(String maKHDien) { this.maKHDien = maKHDien; }

    public String getMaKHNuoc() { return maKHNuoc; }
    public void setMaKHNuoc(String maKHNuoc) { this.maKHNuoc = maKHNuoc; }

    public String getThongTinBoSung() { return thongTinBoSung; }
    public void setThongTinBoSung(String thongTinBoSung) { this.thongTinBoSung = thongTinBoSung; }

    public String getMaKHInternet() { return maKHInternet; }
    public void setMaKHInternet(String maKHInternet) { this.maKHInternet = maKHInternet; }

    public String getNCCDien() { return nccDien; }
    public void setNCCDien(String nccDien) { this.nccDien = nccDien; }

    public String getNCCNuoc() { return nccNuoc; }
    public void setNCCNuoc(String nccNuoc) { this.nccNuoc = nccNuoc; }

    public String getNCCInternet() { return nccInternet; }
    public void setNCCInternet(String nccInternet) { this.nccInternet = nccInternet; }
}
