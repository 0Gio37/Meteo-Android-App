package com.georges.android.meteoandroidapp.utils;

import com.georges.android.meteoandroidapp.R;

public class UtilWeatherIcon {

    public static int setWeatherIcon(int id){
        int iconLocalPath = 0;
        switch (id){
            case 800:
                iconLocalPath = R.drawable.weather_sunny_white;
                break;
            case 802:
                iconLocalPath = R.drawable.weather_thunder_white;
                break;
            case 803:
                iconLocalPath = R.drawable.weather_drizzle_white;
                break;
            case 805:
                iconLocalPath = R.drawable.weather_rainy_white;
                break;
            case 806:
                iconLocalPath = R.drawable.weather_snowy_white;
                break;
            case 807:
                iconLocalPath = R.drawable.weather_foggy_white;
                break;
            case 808:
                iconLocalPath = R.drawable.weather_cloudy_white;
                break;
        }
        return iconLocalPath;
    }

}
