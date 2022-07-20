package com.georges.android.meteoandroidapp.utils;

import android.util.Log;

import com.georges.android.meteoandroidapp.models.City;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UtilFavorite {

    public static Boolean checkDoublon(ArrayList<City> list, City city){
        Boolean response = false;
        for (City item:list) {
            if (item.mName.equals(city.mName)){
                response = true;
            }
        }
    return response;
    }

}
