package com.example.sepia_coding;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // Storing pet data in list.
    ArrayList<Object> petList = new ArrayList<>();
    private RecyclerView mRecyclerView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Read config file data.
        String configFilePath = Constants.CONFIG_FILE_PATH;
        JSONObject configObject = readJson(configFilePath);
        String workHours = "";
        try {
            workHours = configObject.getJSONObject(Constants.SETTINGS).getString(Constants.WORK_HOURS);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Is App accessible according to config.json time.
        boolean isAccessible = parseWorkingHours(workHours);
        if (isAccessible) {
            // Get pets.json data
            String petFilePath = Constants.PETS_FILE_PATH;
            JSONObject petObject = readJson(petFilePath);
            try {
                petJson(petObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Recycler view implementation.
            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            RecyclerView.Adapter adapter = new RecyclerAdapter(this, petList);
            mRecyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, Constants.WORKING_HOURS_EXCEEDED, Toast.LENGTH_LONG).show();
        }
    }

    // Parse Working hours from config.json.
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean parseWorkingHours(String workHours) {
        // If dynamic day range is specified.
        Map<String, Integer> dayMap = new HashMap<>();
        dayMap.put("M", 1);
        dayMap.put("MO", 1);
        dayMap.put("TU", 2);
        dayMap.put("W", 3);
        dayMap.put("WE", 3);
        dayMap.put("TH", 4);
        dayMap.put("F", 5);
        dayMap.put("FR", 5);
        dayMap.put("SA", 6);
        dayMap.put("SU", 7);

        boolean isAccessible = false;

        // Parsing time from string
        String[] listDateRange = workHours.split(" ");
        String[] listDayRange = listDateRange[0].split("-");

        // Current day
        int dayNo = LocalDate.now().getDayOfWeek().getValue();
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.TIME_FORMAT);

        //Start time and End time
        LocalTime startTime = LocalTime.parse(listDateRange[1], formatter);
        LocalTime endTime = LocalTime.parse(listDateRange[3], formatter);

        // Verify if specified time in config.json validates current time for accessing application.
        if (dayMap.get(listDayRange[0]) <= dayNo && dayMap.get(listDayRange[1]) >= dayNo && time.compareTo(startTime) > 0 && time.compareTo(endTime) < 0) {
            isAccessible = true;
        }

        return isAccessible;
    }

    // Parse JSONObject from json
    public JSONObject readJson(String filePath) {
        String json;
        JSONObject jsonObject = null;
        try {
            InputStream is = null;
            String jsonString = null;
            BufferedReader bufferedReader = null;
            StringBuilder builder = new StringBuilder();
            try {
                is = getAssets().open(filePath);
                bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((jsonString = bufferedReader.readLine()) != null) {
                    builder.append(jsonString);
                }
            } finally {
                if (is != null) {
                    is.close();
                }
            }
            jsonObject = new JSONObject(new String(builder));


        } catch (IOException | JSONException e) {

            e.printStackTrace();
        }
        return jsonObject;
    }

    public void petJson(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonObject.getString(Constants.PETS_FIELD));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String image_url = obj.getString(Constants.IMAGE_URL_FIELD);
            String title = obj.getString(Constants.TITLE_FIELD);
            String content_url = obj.getString(Constants.CONTENT_URL_FIELD);
            String date_added = obj.getString(Constants.DATE_FIELD);
            petList.add(new petObject(image_url, title, content_url, date_added));
        }
    }
}

