package com.georges.android.meteoandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewCityName;
    private TextView mTextViewNoConnexionMessage;
    private Button mButtonFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /*  */
        mTextViewCityName = (TextView) findViewById(R.id.text_view_city_name);
        mTextViewCityName.setText("Ma ville en dur dans le code");

        ConnectivityManager connMgr =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            Toast.makeText(this, mTextViewCityName.getText(), Toast.LENGTH_SHORT).show();
            Log.d("TAG", "je suis connecté");
        } else{
            mButtonFavorite = (Button)findViewById(R.id.btn_favorite);
            mButtonFavorite.setVisibility(View.INVISIBLE);
            mTextViewNoConnexionMessage = (TextView)findViewById(R.id.text_view_no_connexion_message);
            mTextViewNoConnexionMessage.setVisibility(View.VISIBLE);
            Log.d("TAG", "je ne suis pas connecté");
        }


    }

}