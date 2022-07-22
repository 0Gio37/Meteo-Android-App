package com.georges.android.meteoandroidapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import com.georges.android.meteoandroidapp.utils.UtilWeatherIcon;
import com.georges.android.meteoandroidapp.utils.UtilTemp;

@Entity
public class City {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name="name")
    public String mName;
    @ColumnInfo(name="desc")
    public String mDescription;
    @ColumnInfo(name="temp")
    public String mTemperature;
    @ColumnInfo(name="id-city")
    public int mIdCity;
    @ColumnInfo(name="latitude")
    public double mLatitude;
    @ColumnInfo(name="longitude")
    public double mLongitude;
    @ColumnInfo(name="icon-weather")
    public int mWeatherResIconWhite;
    //public int mWeatherResIconGrey;
    @ColumnInfo(name="country")
    public String mCountry;
    @ColumnInfo(name="json")
    public String mStringJson;
    public int mIdDataBase;

    public City(String mName, String mDescription, String mTemperature, double mLatitude, double mLongitude, int mWeatherResIconWhite) {
        this.mName = mName;
        this.mDescription = mDescription;
        this.mTemperature = mTemperature;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mWeatherResIconWhite = mWeatherResIconWhite;
    }

/*    public City(String mStringJson) throws JSONException {
        this.mStringJson = mStringJson;
        JSONObject json = new JSONObject(mStringJson);

        this.mName = json.getString("name");
        this.mDescription = json.getJSONArray("weather").getJSONObject(0).getString("description");
        //TODO request api avec units=metric to recup temp en celsius
        this.mTemperature = UtilTemp.setTempartureCelcius(json.getJSONObject("main").getInt("temp"));
        this.mIdCity = json.getInt("id");
        this.mWeatherResIconWhite = UtilWeatherIcon.setWeatherIcon(json.getJSONArray("weather").getJSONObject(0).getInt("id"));
        this.mLatitude = json.getJSONObject("coord").getDouble("lat");
        this.mLongitude = json.getJSONObject("coord").getDouble("lon");
        this.mCountry = json.getJSONObject("sys").getString("country");
    }*/



}
