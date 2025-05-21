package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Exercise;
import com.Fitory.fitory.repository.IF_exerciseRepository;
import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class exerciseServiceImpl implements IF_exerciseService {


    @Autowired
    public IF_exerciseRepository repository;


    @PostConstruct
    public void initJsonImport() {
        try {
            String jsonFilePath = "src/main/resources/json/exercise.json";
            saveJsonToDb(jsonFilePath);
            System.out.println("✅ 저장 시도");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveJsonToDb(String jsonFilePath) throws IOException, JSONException {
        if(repository.count()>0){
            System.out.println("이미 데이터가 있습니다.");
            return;
        }
        BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath));
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }
        reader.close();


        JSONArray jsonArray = new JSONArray(jsonBuilder.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);

            Exercise exercise = new Exercise();
            exercise.setExercise_id(json.getInt("exercise_id"));
            exercise.setE_name(json.getString("name"));
            exercise.setMetrank(json.getInt("met_rank"));
            exercise.setIntensity(json.getString("intensity"));
            exercise.setIs_anaerobic(json.getString("is_anaerobic"));
            exercise.setRequires_equipment(json.getString("requires_equipment"));

            repository.save(exercise);
        }
    }

}
