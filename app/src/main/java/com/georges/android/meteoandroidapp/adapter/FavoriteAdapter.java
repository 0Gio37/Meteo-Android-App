package com.georges.android.meteoandroidapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.georges.android.meteoandroidapp.R;
import com.georges.android.meteoandroidapp.activities.FavoriteActivity;
import com.georges.android.meteoandroidapp.models.City;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{

    private Context mContext;
//    private ArrayList<City> mCities;
    private List<City> mCities;

    //public FavoriteAdapter(Context mContext, ArrayList<City> mCities){
    public FavoriteAdapter(Context mContext, List<City> mCities){
        this.mContext = mContext;
        this.mCities = mCities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_favorite_city, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    };

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        City city = mCities.get(position);
        holder.mTexteViewSingleItemCityName.setText(city.mName);
        holder.mTexteViewSingleItemDescription.setText(city.mDescription);
        holder.mTexteViewSingleItemTemperature.setText(city.mTemperature);
        holder.mTexteViewSingleItemWeatherIcon.setImageResource(city.mWeatherResIconWhite);
        holder.mCity = city;
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        public TextView mTexteViewSingleItemCityName;
        public TextView mTexteViewSingleItemDescription;
        public TextView mTexteViewSingleItemTemperature;
        public ImageView mTexteViewSingleItemWeatherIcon;
        public City mCity;
        public AppCompatActivity activity;

        public ViewHolder(View view){
            super(view);
            view.setOnLongClickListener(this);
            mTexteViewSingleItemCityName = (TextView) view.findViewById(R.id.text_view_single_item_name);
            mTexteViewSingleItemDescription = (TextView) view.findViewById(R.id.text_view_single_item_description);
            mTexteViewSingleItemTemperature = (TextView) view.findViewById(R.id.text_view_single_item_temp);
            mTexteViewSingleItemWeatherIcon = (ImageView) view.findViewById(R.id.image_view_single_item_picto);
        }

        @Override
        public boolean onLongClick(View v) {
            String selectedCityName = mCity.mName;
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle((mContext.getString(R.string.delete))+" ?" );
            builder.setMessage("Voulez-vous retirer"+selectedCityName+ " de vos favoris ?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mCities.remove(mCity);
                    notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("ANNULER", null);
            builder.create().show();
            return true;
        }
    }

}
