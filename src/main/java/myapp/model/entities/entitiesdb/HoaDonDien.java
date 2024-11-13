package myapp.model.entities.entitiesdb;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonDien {
    private String maHD;
    private String maKH;
    private BigDecimal chiSoDienSuDung;
    private LocalDate ngayHetHan;
    private String thongTinBoSung;
    private BigDecimal tienDien;
}
