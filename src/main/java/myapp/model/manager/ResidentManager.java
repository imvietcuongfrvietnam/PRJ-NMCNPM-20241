package myapp.model.manager;

import com.fasterxml.jackson.databind.SerializationFeature;
import myapp.model.entities.entitiesdb.Resident;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ResidentManager {
    private static final String FILE_PATH = "..\\resources\\logs\\resident.json";
    public void saveResidentToJson(List<Resident> residents){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File(FILE_PATH), residents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
