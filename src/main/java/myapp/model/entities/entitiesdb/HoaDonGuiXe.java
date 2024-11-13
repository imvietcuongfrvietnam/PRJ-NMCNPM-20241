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
public class HoaDonGuiXe {
    private String maHD;
    private BigDecimal soTien;
    private String bienSo;
    private LocalDate ngayHetHan;
    private String thongTinBoSung;
}
