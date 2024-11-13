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
public class HoaDonInternet {
    private String maHD;
    private String maKH;
    private BigDecimal soTien;
    private LocalDate ngayHetHan;
    private String thongTinBoSung;

}
