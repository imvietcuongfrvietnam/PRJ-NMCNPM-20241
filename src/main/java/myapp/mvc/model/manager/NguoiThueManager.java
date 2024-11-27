package myapp.mvc.model.manager;

import com.fasterxml.jackson.databind.SerializationFeature;
import myapp.mvc.model.entities.entitiesdb.NguoiThue;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class NguoiThueManager {
    private static final String FILE_PATH = "..\\resources\\logs\\nguoithue.json";
    public void saveNguoiThueToJson(List<NguoiThue> nguoiThues){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File(FILE_PATH), nguoiThues);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
