package com.group12.project1;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Converters {
    @TypeConverter
    public String convertListToString(List<String> jobs) {
        Gson gson = new Gson();
        return gson.toJson(jobs);
    }

    @TypeConverter
    public List<String> convertStringToList(String jobs) {
        Gson gson = new Gson();
        return gson.fromJson(jobs, new TypeToken<List<String>>(){}.getType());
    }

    @TypeConverter
    public String convertSearchPrefsToString(SearchPreferences prefs) {
        Gson gson = new Gson();
        return gson.toJson(prefs);
    }

    @TypeConverter
    public SearchPreferences convertStringToSearchPrefs(String prefs) {
        Gson gson = new Gson();
        return gson.fromJson(prefs, SearchPreferences.class);
    }
}
