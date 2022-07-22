package com.georges.android.meteoandroidapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.georges.android.meteoandroidapp.models.City;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CityDao {

    @Query("SELECT * FROM city")
    List<City> getAllCities();


    @Query("DELETE FROM city")
    void deleteAll();

    @Insert
    void insertCity(City...cities);

  /*  @Delete
    void deleteOne(City City);
*/

}