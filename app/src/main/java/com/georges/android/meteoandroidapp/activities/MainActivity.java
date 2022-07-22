package com.georges.android.meteoandroidapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
import com.georges.android.meteoandroidapp.database.DemoCityDataBase;
import com.georges.android.meteoandroidapp.databinding.ActivityMainBinding;
import com.georges.android.meteoandroidapp.models.City;

import org.json.JSONException;
import java.io.IOException;

import com.georges.android.meteoandroidapp.models.DemoCity;
import com.georges.android.meteoandroidapp.utils.UtilApi;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewCityName;
    private TextView mTextViewCityDescription;
    private TextView mTextViewCityTemp;
    private ImageView mImageViewCityWeatherIcon;
    private TextView mTextViewNoConnexionMessage;
    private Button mButtonFavorite;
    private Button mButtonAccesSetting;
    private EditText mTestMessage;
    private Context mContext;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private City mCurrentCity;
    private ActivityMainBinding binding;

    //demo attribut
    private EditText demoName;
    private EditText demoDesc;
    private EditText demoTemp;
    private Button demoBtnSave;
    private Button demoBtnSeeListe;


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

        //demo binding
        demoName = binding.demoName;
        demoDesc = binding.demoDesc;
        demoTemp = binding.demoTemp;
        demoBtnSave = binding.demoBtnValider;
        demoBtnSeeListe = binding.demoBtnVoirListe;

        //demo onclick btn listener
        demoBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                demoSaveNewCity(demoName.getText().toString(), demoDesc.getText().toString(),demoTemp.getText().toString());
                Intent intent = new Intent(mContext, DemoActivity.class);
                startActivity(intent);
            }
        });

        //demo onclick voir la liste
        demoBtnSeeListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DemoActivity.class);
                startActivity(intent);
            }
        });



        //action btn favorite
        //mButtonFavorite = (Button) findViewById(R.id.btn_favorite);
        mButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "clic btn favorite", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, FavoriteActivity.class);
                //intent.putExtra("monMessage", mTestMessage.getText().toString());
                startActivity(intent);
            }
        });

        //test de connexion
        ConnectivityManager connMgr =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
           // Toast.makeText(this, mTextViewCityName.getText(), Toast.LENGTH_SHORT).show();
            callAPI();
            Log.d("TAG", "connexion ok");
        } else{
            //callAPI();
            displayNoConexionPage();
        }
    }

    //appel de l API
    public void callAPI(){
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
                    Log.d("TAG", stringJson);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            renderCurrentWeather(stringJson);
                        }
                    });
                }
            }
        });
    }

    //affichage d'une page d'erreur de connexion
    public void displayNoConexionPage(){
        mButtonFavorite = (Button)findViewById(R.id.btn_favorite);
        mButtonFavorite.setVisibility(View.INVISIBLE);
        mButtonAccesSetting = (Button) findViewById(R.id.btn_acces_settings);
        mButtonAccesSetting.setVisibility(View.VISIBLE);
        mTextViewNoConnexionMessage = (TextView)findViewById(R.id.text_view_no_connexion_message);
        mTextViewNoConnexionMessage.setVisibility(View.VISIBLE);
        Log.d("TAG", "erreur de connexion");
        //TODO affiche bouton dans le layout pour acces aux setting du tel
    }

    //methode qui met à jour l'affichage de la vue
    public void renderCurrentWeather(String strJson){
        try {
            mCurrentCity = new City(strJson);
            mTextViewCityName.setText(mCurrentCity.mName);
            mTextViewCityDescription.setText(mCurrentCity.mDescription);
            mTextViewCityTemp.setText((mCurrentCity.mTemperature));
            mImageViewCityWeatherIcon.setImageResource(mCurrentCity.mWeatherResIconWhite);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //demo save NewCity methode
    private void demoSaveNewCity(String demoName, String demoDesc, String demoTemp){
        DemoCityDataBase demoCityDataBase = DemoCityDataBase.getDBInstance(this.getApplicationContext());
        DemoCity demoCity = new DemoCity(demoName,demoDesc,demoTemp);
        demoCityDataBase.demoCityDao().insertDemoCity(demoCity);
        finish();

    }








}