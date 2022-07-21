package com.georges.android.meteoandroidapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.georges.android.meteoandroidapp.models.DemoCity;

@Database(entities = {DemoCity.class}, version =1)
public abstract class DemoCityDataBase extends RoomDatabase {

    public abstract DemoCityDao demoCityDao();

    private static DemoCityDataBase INSTANCE;

    public static DemoCityDataBase getDBInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),DemoCityDataBase.class, "DEMO_DB")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
