package myapp.model.entities.entitiesdb;
public class Phong {
    private String id; // ID phòng
    private int tang; // Tầng
    private int dienTich; // Diện tích

    public String getID() {
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

    private String tinhTrang; // Tình trạng
    private String thongTinBoSung; // Thông tin bổ sung

}
