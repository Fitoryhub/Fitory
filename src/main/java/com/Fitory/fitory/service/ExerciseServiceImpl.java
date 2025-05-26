package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Exercises;
import com.Fitory.fitory.repository.ExerciseRepository;
import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {


    @Autowired
    public ExerciseRepository ERepo;


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
        if(ERepo.count()>0){
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

            Exercises exercises = new Exercises();
            exercises.setExercise_id(json.getInt("exercise_id"));
            exercises.setE_name(json.getString("name"));
            exercises.setMetrank(json.getInt("met_rank"));
            exercises.setIntensity(json.getString("intensity"));
            exercises.setIsAnaerobic(json.getString("is_anaerobic"));
            exercises.setRequiresEquipment(json.getString("requires_equipment"));

            ERepo.save(exercises);
        }
    }


    @Override
    public List<Exercises> getMatchingExercises(int met, String requiresEquipment, String isAnaerobic) {
        return ERepo.findByMatchingExercise(met, requiresEquipment, isAnaerobic);
    }

    @Override
    public List<Exercises> getMatchingExercises_v2(int met, String isAnaerobic) {
        return ERepo.findByMetrankAndIsAnaerobic(met, isAnaerobic);
    }

    @Override
    public List<Exercises> getMatchingExercises_v3(int met, String requiresEquipment) {
        return ERepo.findByMetrankAndrequiresEquipment(met, requiresEquipment);
    }

    @Override
    public List<Exercises> getMatchingExercises_v4(int met) {
        return ERepo.findAllByMetrank(met);
    }
}
