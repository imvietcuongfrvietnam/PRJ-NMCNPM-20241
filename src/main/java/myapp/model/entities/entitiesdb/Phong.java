package myapp.model.entities.entitiesdb;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Phong {
    private String id; // ID phòng
    private int tang; // Tầng
    private int dienTich; // Diện tích
    private String tinhTrang; // Tình trạng
    private String thongTinBoSung; // Thông tin bổ sung

}
