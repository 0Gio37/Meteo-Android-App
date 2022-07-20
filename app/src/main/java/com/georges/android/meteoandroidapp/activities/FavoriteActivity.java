package com.georges.android.meteoandroidapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.georges.android.meteoandroidapp.R;
import com.georges.android.meteoandroidapp.adapter.FavoriteAdapter;
import com.georges.android.meteoandroidapp.models.City;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.georges.android.meteoandroidapp.databinding.ActivityFavoriteBinding;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private ActivityFavoriteBinding binding;
    //private TextView mGetMessage;
    private ArrayList<City> mCities;
    private RecyclerView mRecylerViewListFavorite;
    private FavoriteAdapter mAdapter;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        mContext = this;


        //recup info main activity
        Bundle extras = getIntent().getExtras();
        String strMessage = extras.getString("monMessage");
        //mGetMessage = (TextView) findViewById(R.id.text_view_message_activity);
        //mGetMessage.setText("Message :" + strMessage);

       //crea list temp en dur
       mCities = new ArrayList<>();
       City city1 = new City("Montréal", "Légères pluies", "22°C", R.drawable.weather_rainy_white);
       City city2 = new City("New York", "Ensoleillé","22°C", R.drawable.weather_sunny_white);
       City city3 = new City("Paris", "Nuageux", "24°C", R.drawable.weather_foggy_white);
       City city4 = new City("Toulouse", "Pluies modérées", "20°C", R.drawable.weather_rainy_white);

        mCities.add(city1);
        mCities.add(city2);
        mCities.add(city3);
        mCities.add(city4);

        //binding de la recylcler view
        mRecylerViewListFavorite = (RecyclerView) findViewById(R.id.recycler_view_list_favorite);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecylerViewListFavorite.setLayoutManager(layoutManager);
        //creation de l'instance adapter / set recycler
        mAdapter = new FavoriteAdapter(this, mCities);
        mRecylerViewListFavorite.setAdapter(mAdapter);

        //crea de la modale suite au click sur le btn search
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                Toast.makeText(mContext, "float btn ok", Toast.LENGTH_SHORT).show();
                View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_favorite, null);
                final EditText editTextCity = (EditText) v.findViewById(R.id.edit_text_modal_add_favorit_city);
                builder.setView(v);
                builder.setTitle("Ajouter un favori ?");
                builder.setMessage("Taper son nom et faites entrer");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newFavoriCity = editTextCity.getText().toString();
                        City city5 = new City(newFavoriCity, "Pluies modérées", "20°C", R.drawable.weather_rainy_white);
                        mCities.add(city5);
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(mContext, newFavoriCity + " ajouté aux favoris !", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.create().show();
            }
        });
    }
}