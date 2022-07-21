package com.georges.android.meteoandroidapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.georges.android.meteoandroidapp.R;
import com.georges.android.meteoandroidapp.adapter.DemoAdapter;
import com.georges.android.meteoandroidapp.database.DemoCityDataBase;
import com.georges.android.meteoandroidapp.models.City;
import com.georges.android.meteoandroidapp.models.DemoCity;

import java.util.List;

public class DemoActivity extends AppCompatActivity {
    private Button demoBtnAddCity;
    private Context demoContext;
    private RecyclerView demoRecyclerView;
    private DemoAdapter demoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        demoContext = this;
        demoBtnAddCity = (Button) findViewById(R.id.demo_btn_ajout_ville);
        demoBtnAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(demoContext, MainActivity.class);
                startActivity(intent);
            }
        });

        initRecyclerView();

        loadCityList();


    }

    private void initRecyclerView() {
        demoRecyclerView = (RecyclerView) findViewById(R.id.demo_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        demoRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        demoRecyclerView.addItemDecoration(dividerItemDecoration);
        demoAdapter = new DemoAdapter(this);
        demoRecyclerView.setAdapter(demoAdapter);
    }

    private void loadCityList(){
        DemoCityDataBase demoCityDataBase = DemoCityDataBase.getDBInstance(this.getApplicationContext());
        List<DemoCity> cityList = demoCityDataBase.demoCityDao().getAllDemoCity();
        demoAdapter.setDemoCityList(cityList);
    }


}