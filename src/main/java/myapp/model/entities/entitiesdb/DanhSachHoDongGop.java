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
public class DanhSachHoDongGop {
        private String maHoDongGop;
        private int maQuy;
        private BigDecimal soTienDongGop;
        private String thongTinBoSung;
        private int id;
        private String dotDongGop;
        private LocalDate ngayDongGop;

        // Constructor

    }
