package com.georges.android.meteoandroidapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DemoCity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "demo_name")
    public String mNameCity;

    @ColumnInfo(name = "demo_description")
    public String mDescWeather;

    @ColumnInfo(name = "city_temp")
    public String mTemp;

}
