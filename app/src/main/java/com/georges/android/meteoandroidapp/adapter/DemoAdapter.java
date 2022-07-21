package com.georges.android.meteoandroidapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.georges.android.meteoandroidapp.R;
import com.georges.android.meteoandroidapp.activities.DemoActivity;
import com.georges.android.meteoandroidapp.activities.MainActivity;
import com.georges.android.meteoandroidapp.database.DemoCityDataBase;
import com.georges.android.meteoandroidapp.models.DemoCity;

import java.util.List;

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {

    private Context demoContext;
    private List<DemoCity> cityList;

    private int demoPosition;

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
        holder.demoCity = demoCity;
        //demoPosition = holder.getAbsoluteAdapterPosition();

    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        private TextView demoName;
        private TextView demoDesc;
        private TextView demoTemp;
        private DemoCity demoCity;

        public ViewHolder(View view){
            super(view);
            view.setOnLongClickListener(this);
            demoName = (TextView) view.findViewById(R.id.demo_single_item_name);
            demoDesc = (TextView) view.findViewById(R.id.demo_single_item_desc);
            demoTemp = (TextView) view.findViewById(R.id.demo_single_item_temp);
        }

        @Override
        public boolean onLongClick(View view) {
            Log.d("LONG", "CLICK");
            String selectedCityName = demoCity.mNameCity;
            final AlertDialog.Builder builder = new AlertDialog.Builder(demoContext);
            builder.setMessage("Voulez-vous retirer"+selectedCityName+ " de vos favoris ?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DemoCityDataBase demoCityDataBase = DemoCityDataBase.getDBInstance(demoContext.getApplicationContext());
                    demoCityDataBase.demoCityDao().delete(demoCity);
                    DemoAdapter demoAdapter;
                    //notifyItemRemoved(demoPosition);
                    //notifyDataSetChanged();
                    Intent intent = new Intent(demoContext, DemoActivity.class);
                    demoContext.startActivity(intent);
                }
            });
            builder.setNegativeButton("ANNULER", null);
            builder.create().show();
            return true;
        }
    }


}
