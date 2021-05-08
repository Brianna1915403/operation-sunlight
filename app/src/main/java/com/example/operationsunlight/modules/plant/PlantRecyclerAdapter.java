package com.example.operationsunlight.modules.plant;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.operationsunlight.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlantRecyclerAdapter extends RecyclerView.Adapter<PlantRecyclerAdapter.ViewHolder>{
    private OnPlantListener onPlantListener;
    ArrayList<Plant> plant_list = new ArrayList<>();
    Context mContext;

    public PlantRecyclerAdapter(ArrayList<Plant> plants, Context mContext, OnPlantListener onPlantListener) {
        this.plant_list = plants;
        this.mContext = mContext;
        this.onPlantListener = onPlantListener;
    }

    @NonNull
    @Override
    public PlantRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_plant, parent, false);
        return new ViewHolder(view, onPlantListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantRecyclerAdapter.ViewHolder holder, int position){
        Plant plant = plant_list.get(position);
        Picasso.get()
                .load(plant.image_url)
                .placeholder(R.drawable.error_file)
                .error(R.drawable.error_file)
                .into(holder.image);
        
        holder.plant_id = plant .plant_id;
        holder.common_name.setText(plant.common_name);
        holder.scientific_name.setText(plant.scientific_name);
        holder.family_common_name.setText(plant.family_common_name);
    }

    @Override
    public int getItemCount() {
        return plant_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private int plant_id;
        private long plant_id;
        CircleImageView image;
        TextView common_name, scientific_name, family_common_name;
        LinearLayout parentLayout;
        OnPlantListener onPlantListener;

        public ViewHolder(@NonNull View itemView, OnPlantListener onPlantListener) {
            super(itemView);
            this.onPlantListener = onPlantListener;
            image = itemView.findViewById(R.id.plant_image);
            common_name = itemView.findViewById(R.id.common_name_textview);
            scientific_name = itemView.findViewById(R.id.scientific_name_textview);
            family_common_name = itemView.findViewById(R.id.family_common_name_textview);
            parentLayout = itemView.findViewById(R.id.parent);

            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            onPlantListener.onPlantClick(plant_id);
        }
    }
}