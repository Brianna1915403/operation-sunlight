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
        int weather_icon;
        switch (weather_list.get(position).getIcon()) {
            case "01d": weather_icon = R.drawable.w01d_2x; break;
            case "01n": weather_icon = R.drawable.w01n_2x; break;
            case "02d": weather_icon = R.drawable.w02d_2x; break;
            case "02n": weather_icon = R.drawable.w02n_2x; break;
            case "03d": weather_icon = R.drawable.w03d_2x; break;
            case "03n": weather_icon = R.drawable.w03n_2x; break;
            case "04n": weather_icon = R.drawable.w04n_2x; break;
            case "04d": weather_icon = R.drawable.w04d_2x; break;
            case "09d": weather_icon = R.drawable.w09d_2x; break;
            case "09n": weather_icon = R.drawable.w09n_2x; break;
            case "10d": weather_icon = R.drawable.w10d_2x; break;
            case "10n": weather_icon = R.drawable.w10n_2x; break;
            case "11d": weather_icon = R.drawable.w11d_2x; break;
            case "11n": weather_icon = R.drawable.w11n_2x; break;
            case "13d": weather_icon = R.drawable.w13d_2x; break;
            case "13n": weather_icon = R.drawable.w13n_2x; break;
            case "50d": weather_icon = R.drawable.w50d_2x; break;
            case "50n": weather_icon = R.drawable.w50n_2x; break;
            default: weather_icon = R.drawable.ic_error_outline; break;
        }

        Glide.with(mContext)
                .asBitmap()
                .load(weather_icon)
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
