package com.example.operationsunlight.ui.plant;
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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlantRecyclerAdapter extends RecyclerView.Adapter<PlantRecyclerAdapter.ViewHolder>{
    private onPlantListener onPlantListener;
    ArrayList<Plant> plant_list = new ArrayList<>();
    Context mContext;

    public PlantRecyclerAdapter(ArrayList<Plant> plants, Context mContext, onPlantListener onPlantListener) {
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
        Glide.with(mContext)
                .asBitmap()
                .load(plant_list.get(position).image_url)
                .placeholder(R.drawable.error_file)
                .error(R.drawable.error_file)
                .into(holder.image);

        holder.plant_id = plant_list.get(position).plant_id;
        holder.common_name.setText(plant_list.get(position).common_name);
        holder.scientific_name.setText(plant_list.get(position).scientific_name);
        holder.family_common_name.setText(plant_list.get(position).family_common_name);
    }

    @Override
    public int getItemCount() {
        return plant_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int plant_id;
        CircleImageView image;
        TextView common_name, scientific_name, family_common_name;
        LinearLayout parentLayout;
        onPlantListener onPlantListener;

        public ViewHolder(@NonNull View itemView, onPlantListener onPlantListener) {
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