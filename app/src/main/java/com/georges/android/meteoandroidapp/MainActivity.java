package com.georges.android.meteoandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewCityName;
    private TextView mTextViewNoConnexionMessage;
    private Button mButtonFavorite;
    private Button mButtonAccesSetting;
    private EditText mTestMessage;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mTextViewCityName = (TextView) findViewById(R.id.text_view_city_name);
        mTextViewCityName.setText("Ma ville en dur dans le code");


        //action btn favorite
        mTestMessage = (EditText) findViewById(R.id.edit_text_test);
        mButtonFavorite = (Button) findViewById(R.id.btn_favorite);
        mButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "clic btn favorite", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, FavoriteActivity.class);
                intent.putExtra("monMessage", mTestMessage.getText().toString());
                startActivity(intent);
            }
        });

        //test de connexion
        ConnectivityManager connMgr =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            Toast.makeText(this, mTextViewCityName.getText(), Toast.LENGTH_SHORT).show();
            Log.d("TAG", "je suis connecté");
        } else{
            mButtonFavorite = (Button)findViewById(R.id.btn_favorite);
            mButtonFavorite.setVisibility(View.INVISIBLE);
            mButtonAccesSetting = (Button) findViewById(R.id.btn_acces_settings);
            mButtonAccesSetting.setVisibility(View.VISIBLE);
            mTextViewNoConnexionMessage = (TextView)findViewById(R.id.text_view_no_connexion_message);
            mTextViewNoConnexionMessage.setVisibility(View.VISIBLE);
            Log.d("TAG", "je ne suis pas connecté");
        }
    }

}