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
public class HoaDonDichVu {
    private int maDichVu;
    private String maHD;
    private String maHoGiaDinh;
    private BigDecimal soTien;
    private LocalDate ngayHetHan;
    private String thongTinBoSung;
}
