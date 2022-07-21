package com.georges.android.meteoandroidapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.georges.android.meteoandroidapp.R;

public class DemoActivity extends AppCompatActivity {
    private Button demoBtnAddCity;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        demoBtnAddCity = (Button) findViewById(R.id.demo_btn_ajout_ville);
        demoBtnAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);

            }
        });











    }
}