package com.georges.android.meteoandroidapp.utils;

public class UtilTemp {

    public static String setTempartureCelcius(int tempKelvin){
        int som = tempKelvin - 273;
        return som + "°C";
        //TODO : gestion des float pour temp a chifres apres la virgule
    }
}
