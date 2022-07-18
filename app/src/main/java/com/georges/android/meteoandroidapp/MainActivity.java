package com.georges.android.meteoandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewCityName = (TextView) findViewById(R.id.text_view_city_name);
        mTextViewCityName.setText("New York");
        String currentMTextViewCityName = mTextViewCityName.getText().toString();
        Toast.makeText(this, currentMTextViewCityName, Toast.LENGTH_SHORT).show();

    }

}