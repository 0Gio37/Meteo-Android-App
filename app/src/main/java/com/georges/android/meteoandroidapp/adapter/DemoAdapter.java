package com.georges.android.meteoandroidapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.georges.android.meteoandroidapp.R;
import com.georges.android.meteoandroidapp.models.DemoCity;

import java.util.List;

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {

    private Context demoContext;
    private List<DemoCity> cityList;

    public DemoAdapter(Context demoContext){
        this.demoContext = demoContext;
    }

    public void setDemoCityList(List<DemoCity> cityList){
        this.cityList = cityList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DemoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(demoContext).inflate(R.layout.demo_single_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DemoAdapter.ViewHolder holder, int position) {
        DemoCity demoCity = cityList.get(position);
        holder.demoName.setText(demoCity.mNameCity);
        holder.demoDesc.setText(demoCity.mDescWeather);
        holder.demoTemp.setText(demoCity.mTemp);
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView demoName;
        private TextView demoDesc;
        private TextView demoTemp;
        public ViewHolder(View view){
            super(view);
            demoName = (TextView) view.findViewById(R.id.demo_single_item_name);
            demoDesc = (TextView) view.findViewById(R.id.demo_single_item_desc);
            demoTemp = (TextView) view.findViewById(R.id.demo_single_item_temp);
        }

    }


}
