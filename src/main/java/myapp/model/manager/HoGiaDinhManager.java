package myapp.model.manager;

import com.fasterxml.jackson.databind.SerializationFeature;
import myapp.model.entities.entitiesdb.HoGiaDinh;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class HoGiaDinhManager {
    private static final String FILE_PATH = "..\\resources\\logs\\hogiadinh.json";
    public void saveHoGiaDinhToJson(List<HoGiaDinh> hoGiaDinhs){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File(FILE_PATH), hoGiaDinhs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
