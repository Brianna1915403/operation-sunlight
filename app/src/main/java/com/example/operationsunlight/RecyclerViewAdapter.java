package com.example.operationsunlight;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
   ArrayList<Plant> plant_list = new ArrayList<>();
    Context mContext;

    public RecyclerViewAdapter(ArrayList<Plant> plants, Context mContext) {
        this.plant_list = plants;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewlist_catalogue, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position){
        Glide.with(mContext)
                .asBitmap()
                .load(plant_list.get(position).image_url)
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.error_file)
                .into(holder.image);

        holder.common_name.setText(plant_list.get(position).common_name);
        holder.scientific_name.setText(plant_list.get(position).scientific_name);
        holder.family_common_name.setText(plant_list.get(position).family_common_name);

        int plant_id = plant_list.get(position).plant_id;

        holder.viewPlantInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendToInfo = new Intent(mContext, SpecificPlantInfo.class);
                sendToInfo.putExtra("plant_id", plant_id);
                mContext.startActivity(sendToInfo);
            }
        });


    }



    @Override
    public int getItemCount() {
        return plant_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView common_name, scientific_name, family_common_name;
        LinearLayout parentLayout;
        Button viewPlantInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.images);
            common_name = itemView.findViewById(R.id.common_nameTextView);
            scientific_name = itemView.findViewById(R.id.scientific_nameTextView);
            family_common_name = itemView.findViewById(R.id.family_common_nameTextView);
            viewPlantInfo = itemView.findViewById(R.id.viewInfoButton);

            parentLayout = itemView.findViewById(R.id.parent);
        }
    }
}