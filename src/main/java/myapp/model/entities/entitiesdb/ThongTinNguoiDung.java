package myapp.model.entities.entitiesdb;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThongTinNguoiDung {
    private String soCMND;
    private String ten;
    private String email;
    private String queQuan;
    private String dienThoai;
}
