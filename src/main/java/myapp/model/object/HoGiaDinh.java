package model.object;
import java.util.Date;

public class HoGiaDinh {
    private String maHoGiaDinh;    // Mã hộ gia đình
    private String maPhongThue;    // Mã phòng thuê (foreign key)
    private Date ngayChuyenVao;    // Ngày chuyển vào
    private Date ngayChuyenRa;     // Ngày chuyển ra
    private String soCMNDChuHo;    // Số CMND của chủ hộ
    private String trangThai;      // Trạng thái

    // Constructor
    public HoGiaDinh(String maHoGiaDinh, String maPhongThue, Date ngayChuyenVao, Date ngayChuyenRa, String soCMNDChuHo, String trangThai) {
        this.maHoGiaDinh = maHoGiaDinh;
        this.maPhongThue = maPhongThue;
        this.ngayChuyenVao = ngayChuyenVao;
        this.ngayChuyenRa = ngayChuyenRa;
        this.soCMNDChuHo = soCMNDChuHo;
        this.trangThai = trangThai;
    }

    // Getters and Setters
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

    public Date getNgayChuyenVao() {
        return ngayChuyenVao;
    }

    public void setNgayChuyenVao(Date ngayChuyenVao) {
        this.ngayChuyenVao = ngayChuyenVao;
    }

    public Date getNgayChuyenRa() {
        return ngayChuyenRa;
    }

    public void setNgayChuyenRa(Date ngayChuyenRa) {
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

    @Override
    public String toString() {
        return "HoGiaDinh{" +
                "maHoGiaDinh='" + maHoGiaDinh + '\'' +
                ", maPhongThue='" + maPhongThue + '\'' +
                ", ngayChuyenVao=" + ngayChuyenVao +
                ", ngayChuyenRa=" + ngayChuyenRa +
                ", soCMNDChuHo='" + soCMNDChuHo + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
