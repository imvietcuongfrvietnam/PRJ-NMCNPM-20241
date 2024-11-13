package myapp.model.entities.entitiesdb;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuanLyXe {
    private String maHo; // Mã hộ
    private String loaiPhuongTien; // Loại phương tiện
    private String bienSo; // Biển số (chính khóa)
    private String thongTinBoSung; // Thông tin bổ sung
    private String ngayBatDau; // Ngày bắt đầu
    private String ngayKetThuc; // Ngày kết thúc

}
