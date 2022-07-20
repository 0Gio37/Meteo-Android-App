package com.georges.android.meteoandroidapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dataBase_weather";
    private static final int DATABASE_VERSION = 1;
    public Context mContext;

    private static final String TABLE_CITY = "city";
    private static final String KEY_ID = "id";
    private static final String KEY_ID_CITY ="id_city";
    private static final String KEY_NAME = "name";
    private static final String KEY_TEMP = "temperature";
    private static final String KEY_DESC = "description";
    private static final String KEY_RES_ICON ="res_icon" ;
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LNG = "longitude";
    private static final String CREATE_TABLE_CITY = "CREATE TABLE "
            + TABLE_CITY
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_ID_CITY + " INTEGER,"
            + KEY_NAME + " TEXT,"
            + KEY_TEMP + " TEXT,"
            + KEY_DESC + " TEXT,"
            + KEY_RES_ICON + " INTEGER,"
            + KEY_LAT + " DECIMAL (3, 10),"
            + KEY_LNG + " DECIMAL (3, 10)"
            + ")";




    public DataBaseHelper(Context mContext){
        super(mContext, DATABASE_NAME, null, DATABASE_VERSION);

    };

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
        onCreate(sqLiteDatabase);
    }
}
