package com.georges.android.meteoandroidapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.georges.android.meteoandroidapp.models.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UtilFavorite {

    private static final String PREFS_NAME = null ;
    private static final String PREFS_FAVORITE_CITIES = null;

    public static Boolean checkDoublon(ArrayList<City> list, City city){
        Boolean response = false;
        for (City item:list) {
            if (item.mName.equals(city.mName)){
                response = true;
            }
        }
    return response;
    }

    public static void saveFavouriteCities(Context context, ArrayList<City> cities){
        JSONArray jsonArrayCities = new JSONArray();
        for (int i =0; i < cities.size(); i++){
            jsonArrayCities.put(cities.get(i).mStringJson);
        }
        SharedPreferences preferences = context.getSharedPreferences(UtilFavorite.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(UtilFavorite.PREFS_FAVORITE_CITIES, jsonArrayCities.toString());
        editor.apply();
    }

    public static ArrayList<City> initFavouriteCities(Context context){
        ArrayList<City> cities = new ArrayList<>();
        SharedPreferences preferences = context.getSharedPreferences(UtilFavorite.PREFS_NAME, Context.MODE_PRIVATE);

        try {
            JSONArray jsonArray = new JSONArray(preferences.getString(UtilFavorite.PREFS_FAVORITE_CITIES, ""));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectCity = new JSONObject(jsonArray.getString(i));
                City city = new City(jsonObjectCity.toString());
                cities.add(city);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cities;
    }






}
