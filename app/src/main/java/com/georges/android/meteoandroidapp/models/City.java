package com.georges.android.meteoandroidapp.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.georges.android.meteoandroidapp.utils.UtilWeatherIcon;
import com.georges.android.meteoandroidapp.utils.UtilTemp;

public class City {

    public String mName;
    public String mDescription;
    public String mTemperature;
    public int mIdCity;
    public double mLatitude;
    public double mLongitude;
    public int mWeatherResIconWhite;
    //public int mWeatherResIconGrey;
    public String mCountry;
    public String mStringJson;
    public int mIdDataBase;


    public City(String mStringJson) throws JSONException {
        this.mStringJson = mStringJson;
        JSONObject json = new JSONObject(mStringJson);

        this.mName = json.getString("name");
        this.mDescription = json.getJSONArray("weather").getJSONObject(0).getString("description");
        //TODO request api avec units=metric to recup temp en celsius
//        this.mTemperature = json.getJSONObject("main").getInt("temp");
       this.mTemperature = UtilTemp.setTempartureCelcius(json.getJSONObject("main").getInt("temp"));
        this.mIdCity = json.getInt("id");
        this.mWeatherResIconWhite = UtilWeatherIcon.setWeatherIcon(json.getJSONArray("weather").getJSONObject(0).getInt("id"));
        this.mLatitude = json.getJSONObject("coord").getDouble("lat");
        this.mLongitude = json.getJSONObject("coord").getDouble("lon");
        this.mCountry = json.getJSONObject("sys").getString("country");
    }



}
