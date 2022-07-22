package com.georges.android.meteoandroidapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.georges.android.meteoandroidapp.R;
import com.georges.android.meteoandroidapp.adapter.DemoAdapter;
import com.georges.android.meteoandroidapp.models.DemoCity;

import java.util.List;

public class DemoActivity extends AppCompatActivity {
    private Button demoBtnAddCity;
    private Button demoDeleteTable;
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

        //btn supp all liste
        demoDeleteTable = (Button) findViewById(R.id.demo_btn_supp_table);
        demoDeleteTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* final AlertDialog.Builder builder = new AlertDialog.Builder(demoContext);
                builder.setTitle("Supprimer toute la liste ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        DemoCityDataBase demoCityDataBase = DemoCityDataBase.getDBInstance(demoContext.getApplicationContext());
                        demoCityDataBase.demoCityDao().deleteAll();
                        Toast.makeText(demoContext, "Liste des favoris effacée", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(demoContext, MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Annuler", null);
                builder.create().show();*/
            }
        });

        initRecyclerView();

    /*    loadCityList();*/


    }

    private void initRecyclerView() {
        demoRecyclerView = (RecyclerView) findViewById(R.id.demo_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        demoRecyclerView.setLayoutManager(layoutManager);
        demoAdapter = new DemoAdapter(this);
        demoRecyclerView.setAdapter(demoAdapter);
        demoAdapter.notifyDataSetChanged();
    }
/*
    private void loadCityList(){
        DemoCityDataBase demoCityDataBase = DemoCityDataBase.getDBInstance(this.getApplicationContext());
        List<DemoCity> cityList = demoCityDataBase.demoCityDao().getAllDemoCity();
        demoAdapter.setDemoCityList(cityList);
    }*/


}