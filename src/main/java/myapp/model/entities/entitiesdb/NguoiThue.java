package myapp.model.entities.entitiesdb;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
