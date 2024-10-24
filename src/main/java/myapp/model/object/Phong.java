package model.object;

public class Phong {
    private String id;               // ID phòng (primary key)
    private int tang;                // Tầng
    private int dienTich;            // Diện tích
    private String tinhTrang;        // Tình trạng
    private String thongTinBoSung;   // Thông tin bổ sung

    // Constructor
    public Phong(String id, int tang, int dienTich, String tinhTrang, String thongTinBoSung) {
        this.id = id;
        this.tang = tang;
        this.dienTich = dienTich;
        this.tinhTrang = tinhTrang;
        this.thongTinBoSung = thongTinBoSung;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Phong{" +
                "id='" + id + '\'' +
                ", tang=" + tang +
                ", dienTich=" + dienTich +
                ", tinhTrang='" + tinhTrang + '\'' +
                ", thongTinBoSung='" + thongTinBoSung + '\'' +
                '}';
    }
}
