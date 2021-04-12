package com.example.operationsunlight.ui.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.operationsunlight.R;
import com.example.operationsunlight.ui.plant.Plant;
import com.example.operationsunlight.ui.plant.PlantRecyclerAdapter;
import com.example.operationsunlight.ui.plant.onPlantListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class WeatherRecyclerAdapter extends RecyclerView.Adapter<WeatherRecyclerAdapter.ViewHolder>{
    ArrayList<FutureWeather> weather_list = new ArrayList<>();
    Context mContext;

    public WeatherRecyclerAdapter(ArrayList<FutureWeather> weathers, Context mContext) {
        this.weather_list = weathers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public WeatherRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherRecyclerAdapter.ViewHolder holder, int position){
        Glide.with(mContext)
                .asBitmap()
                .load(weather_list.get(position).getIcon())
                .placeholder(R.drawable.error_file)
                .error(R.drawable.error_file)
                .into(holder.image);

        holder.date_time.setText("" + weather_list.get(position).getDatetime());
        holder.pop.setText("" + weather_list.get(position).getPop());
        holder.temperature.setText("" + weather_list.get(position).getTemp());
    }

    @Override
    public int getItemCount() {
        return weather_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView date_time, pop, temperature;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.weather_image);
            date_time = itemView.findViewById(R.id.dateTimeTextView);
            pop = itemView.findViewById(R.id.percentageOfPrecipitationTextView);
            temperature = itemView.findViewById(R.id.temperatureTextView);
            parentLayout = itemView.findViewById(R.id.parent);

        }
    }
}
