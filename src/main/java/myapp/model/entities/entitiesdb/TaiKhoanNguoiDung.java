package myapp.model.entities.entitiesdb;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaiKhoanNguoiDung {
    private int maTaiKhoan;
    private String vaiTro;
    private String tenDangNhap;
    private String matKhau;
}
