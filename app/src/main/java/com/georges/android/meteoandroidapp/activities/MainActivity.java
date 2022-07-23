package com.georges.android.meteoandroidapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.georges.android.meteoandroidapp.R;
import com.georges.android.meteoandroidapp.database.CityDataBase;
import com.georges.android.meteoandroidapp.databinding.ActivityMainBinding;
import com.georges.android.meteoandroidapp.models.City;
import java.io.IOException;
import com.georges.android.meteoandroidapp.utils.UtilApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewNoConnexionMessage;
    private TextView mTextViewCityName;
    private TextView mTextViewCityDescription;
    private TextView mTextViewCityTemp;
    private ImageView mImageViewCityWeatherIcon;
    private Button mButtonFavorite;
    private Button mButtonAccesSetting;
    private EditText mTestMessage;
    private Context mContext;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private City mCurrentCity;
    private ActivityMainBinding binding;
    private FloatingActionButton btnAddFavorites;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Location mCurrentLocation;
    final private int REQUEST_CODE = 123;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mContext = this;
        mOkHttpClient = new OkHttpClient();
        mHandler = new Handler();

        mTextViewCityName = binding.textViewCityName;
        mTextViewCityDescription = binding.textViewDescription;
        mTextViewCityTemp = binding.textViewTemp;
        mImageViewCityWeatherIcon = binding.imageViewPicto;
        mButtonFavorite = binding.btnFavorite;
        btnAddFavorites = binding.floatingAddFavorite;

        //btn voir favoris
        mButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FavoriteActivity.class);
                startActivity(intent);
            }
        });

        initLocationListener();

        //test de connexion
        ConnectivityManager connMgr =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            //callAPI();
            updateWeatherDataCoordinatesFromMyLocation();
        } else{
            displayNoConexionPage();
        }

        //btn ajout aux favoris
        btnAddFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityDataBase cityDataBase = CityDataBase.getDBInstance(mContext.getApplicationContext());
                cityDataBase.cityDao().insertCity(mCurrentCity);
                Intent intent = new Intent(mContext, FavoriteActivity.class);
                startActivity(intent);
            }
        });


    }

    public void initLocationListener(){
        Log.d("TAG", "dans la fonction initlocation");
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                mCurrentLocation = location;
                Log.d("LOL", "onLocationChanged: " + location);
                updateWeatherDataCoordinates();
                mLocationManager.removeUpdates(mLocationListener);
            }
        };
    }

    public void updateWeatherDataCoordinatesFromMyLocation(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
            Log.d("TAG", "updateWeatherDataCoordinatesFromMyLocation -> if");
        } else {
            Log.d("TAG", "updateWeatherDataCoordinatesFromMyLocation -> else");
            Log.d("TAG", "location listener : "+mLocationListener);
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
        }
    }


    public void updateWeatherDataCoordinates(){
        String newLat = String.valueOf(mCurrentLocation.getLatitude());
        String newLong = String.valueOf(mCurrentLocation.getLongitude());
        String newUrl = "http://api.openweathermap.org/data/2.5/weather?lat="+newLat+"&lon="+newLong+"&appid="+UtilApi.API_KEY;
        Log.d("TAG", "updateWeatherDataCoordinates -> "+newUrl);
        Request request = new Request.Builder().url(newUrl).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String stringJson = response.body().string();
                if (response.isSuccessful()) {
                    mHandler.post(new Runnable() {
                        public void run() {
                            renderCurrentWeather(stringJson);
                        }
                    });
                } else {
                    mHandler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(mContext, "Lieu non trouvé !!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }


    //appel de l API avec ancienne methode lat et long fix
/*    public void callAPI(){
        Request request = new Request.Builder().url(UtilApi.urlByLatAndLong+UtilApi.API_KEY).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                //displayNoConexionPage();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String stringJson = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            renderCurrentWeather(stringJson);
                        }
                    });
                }
            }
        });
    }*/

    //affichage d'une page d'erreur de connexion
    public void displayNoConexionPage(){
        mButtonFavorite = (Button)findViewById(R.id.btn_favorite);
        mButtonFavorite.setVisibility(View.INVISIBLE);
        mButtonAccesSetting = (Button) findViewById(R.id.btn_acces_settings);
        mButtonAccesSetting.setVisibility(View.VISIBLE);
        mTextViewNoConnexionMessage = (TextView)findViewById(R.id.text_view_no_connexion_message);
        mTextViewNoConnexionMessage.setVisibility(View.VISIBLE);
        btnAddFavorites.setVisibility(View.INVISIBLE);
        //TODO affiche bouton dans le layout pour acces aux setting du tel
    }

    //methode qui met à jour l'affichage de la vue
    public void renderCurrentWeather(String strJson){
        mCurrentCity = UtilApi.convertJsonToCityObjetc(strJson);
        mTextViewCityName.setText(mCurrentCity.mName);
        mTextViewCityDescription.setText(mCurrentCity.mDescription);
        mTextViewCityTemp.setText((mCurrentCity.mTemperature));
        mImageViewCityWeatherIcon.setImageResource(mCurrentCity.mWeatherResIconWhite);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAG", "onRequestPermissionsResult -> if");
                    // Permission Granted
                    updateWeatherDataCoordinatesFromMyLocation();

                } else {
                    Log.d("TAG", "onRequestPermissionsResult -> else");
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "Location Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}