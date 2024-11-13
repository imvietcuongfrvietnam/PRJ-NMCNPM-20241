package myapp.model.entities.entitiessystem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resident {
    private int index;
    private String name;
    private String birthday;
    private String hometown;


}
