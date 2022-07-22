package com.georges.android.meteoandroidapp.utils;

import com.georges.android.meteoandroidapp.models.City;

import org.json.JSONException;
import org.json.JSONObject;

public class UtilApi {

    static public final String API_KEY = "01897e497239c8aff78d9b8538fb24ea";
    static public final String urlByLatAndLong = "http://api.openweathermap.org/data/2.5/weather?lat=47.390026&lon=0.688891&appid=";
    static public final String urlByCityName = "https://api.openweathermap.org/data/2.5/weather?&q=";


    public static City convertJsonToCityObjetc(String mStringJson) {
        JSONObject json = null;
        try {
            json = new JSONObject(mStringJson);
            String mName = json.getString("name");
            String mDescription = json.getJSONArray("weather").getJSONObject(0).getString("description");
            //TODO request api avec units=metric to recup temp en celsius
            String mTemperature = UtilTemp.setTempartureCelcius(json.getJSONObject("main").getInt("temp"));
            int mWeatherResIconWhite = UtilWeatherIcon.setWeatherIcon(json.getJSONArray("weather").getJSONObject(0).getInt("id"));
            double mLatitude = json.getJSONObject("coord").getDouble("lat");
            double mLongitude = json.getJSONObject("coord").getDouble("lon");
            City currentCity = new City(mName, mDescription, mTemperature, mLatitude, mLongitude, mWeatherResIconWhite);
            return currentCity;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}