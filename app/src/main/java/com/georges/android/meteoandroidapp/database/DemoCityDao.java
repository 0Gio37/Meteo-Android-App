package com.georges.android.meteoandroidapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.georges.android.meteoandroidapp.models.DemoCity;

import java.util.List;

@Dao
public interface DemoCityDao {

    @Query("SELECT * FROM democity")
    List<DemoCity> getAllDemoCity();

    @Insert
    void insertDemoCity(DemoCity...cities);

    @Delete
    void delete(DemoCity demoCity);




}
