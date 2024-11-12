package myapp.model.entities.entitiesdb;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiaDichVu {
    private int id;
    private String tenDichVu;
    private BigDecimal donGia;
    private String thongTinBoSung;

    // Constructor

}
