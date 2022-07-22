package com.georges.android.meteoandroidapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.georges.android.meteoandroidapp.database.CityDataBase;
import com.georges.android.meteoandroidapp.utils.UtilFavorite;
import com.georges.android.meteoandroidapp.R;
import com.georges.android.meteoandroidapp.adapter.FavoriteAdapter;
import com.georges.android.meteoandroidapp.models.City;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.georges.android.meteoandroidapp.databinding.ActivityFavoriteBinding;
import java.io.IOException;
import java.util.List;
import com.georges.android.meteoandroidapp.utils.UtilApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FavoriteActivity extends AppCompatActivity {

    private ActivityFavoriteBinding binding;
    private RecyclerView mRecylerViewListFavorite;
    private FavoriteAdapter mAdapter;
    private Context mContext;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private List<City> listCitiesFromDB;


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
        mOkHttpClient = new OkHttpClient();
        mHandler = new Handler();

       //list des cities en room
        CityDataBase cityDataBase = CityDataBase.getDBInstance(this.getApplicationContext());
        listCitiesFromDB = cityDataBase.cityDao().getAllCities();

        //binding de la recylcler view
        mRecylerViewListFavorite = (RecyclerView) findViewById(R.id.recycler_view_list_favorite);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecylerViewListFavorite.setLayoutManager(layoutManager);

        //creation de l'instance adapter / set recycler -> room
        mAdapter = new FavoriteAdapter(this, listCitiesFromDB);
        mRecylerViewListFavorite.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        //btn retour main layout
        FloatingActionButton back = binding.back;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });


        //btn supp de toute la liste de favoris
        FloatingActionButton del = binding.del;
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Supprimer toute la liste ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CityDataBase cityDataBase = CityDataBase.getDBInstance(mContext.getApplicationContext());
                        if(cityDataBase.cityDao().getAllCities().size() >0){
                            cityDataBase.cityDao().deleteAll();
                            Toast.makeText(mContext, "Liste des favoris effacée", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(mContext, MainActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(mContext, "Liste déjà vide", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Annuler", null);
                builder.create().show();
            }
        });

        //btn search -> open model
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                //Toast.makeText(mContext, "float btn ok", Toast.LENGTH_SHORT).show();
                View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_favorite, null);
                final EditText editTextCity = (EditText) v.findViewById(R.id.edit_text_modal_add_favorit_city);
                builder.setView(v);
                builder.setTitle("Ajouter un favori ?");
                builder.setMessage("Taper son nom et faites entrer");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newFavoriCity = editTextCity.getText().toString();
                        callAPI(newFavoriCity);
                    }
                });
                builder.setNegativeButton("Annuler", null);
                builder.create().show();
            }
        });
    }

    public void callAPI(String newFavoriCity){
        Request request = new Request.Builder().url(UtilApi.urlByCityName+newFavoriCity+"&appid="+UtilApi.API_KEY).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String stringJson = response.body().string();
                if (response.isSuccessful()) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            updateFavoriteCityList(stringJson);
                        }
                    });
                } else{
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "Cette ville n'existe pas !", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    public void updateFavoriteCityList(String stringJson){
        CityDataBase cityDataBase = CityDataBase.getDBInstance(this.getApplicationContext());
        City newFavoriteCity = UtilApi.convertJsonToCityObjetc(stringJson);
            if(UtilFavorite.checkDoublon(listCitiesFromDB,newFavoriteCity)){
                Toast.makeText(mContext, "Ville déjà en favori ", Toast.LENGTH_LONG).show();
            }else{
                cityDataBase.cityDao().insertCity(newFavoriteCity);
                //mAdapter.notifyDataSetChanged();
                Intent intent = new Intent(mContext, FavoriteActivity.class);
                startActivity(intent);
                Toast.makeText(mContext, newFavoriteCity.mName + " ajouté aux favoris !", Toast.LENGTH_SHORT).show();
            }
    }
}