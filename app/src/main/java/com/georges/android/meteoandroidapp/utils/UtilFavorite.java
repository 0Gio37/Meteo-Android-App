package com.georges.android.meteoandroidapp.utils;

import com.georges.android.meteoandroidapp.models.City;
import java.util.List;

public class UtilFavorite {

    public static Boolean checkDoublon(List<City> list, City city){
        Boolean response = false;
        for (City item:list) {
            if (item.mName.equals(city.mName)){
                response = true;
            }
        }
    return response;
    }

}
