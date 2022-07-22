package com.georges.android.meteoandroidapp.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.georges.android.meteoandroidapp.models.City;


@Database(entities = {City.class}, version =1)
public abstract class CityDataBase extends RoomDatabase {

    public abstract CityDao cityDao();

    private static CityDataBase INSTANCE;

    public static CityDataBase getDBInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),CityDataBase.class, "CITY_DB")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}