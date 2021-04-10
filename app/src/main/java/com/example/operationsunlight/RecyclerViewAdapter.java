package com.example.operationsunlight;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.module.AppGlideModule;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_plant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position){
        Glide.with(mContext)
                .asBitmap()
                .load(plant_list.get(position).image_url)
                .placeholder(R.drawable.ic_error_outline)
                .error(R.drawable.ic_error_outline)
                .into(holder.image);

        holder.common_name.setText(plant_list.get(position).common_name);
        holder.scientific_name.setText(plant_list.get(position).scientific_name);
        holder.family_common_name.setText(plant_list.get(position).family_common_name);
    }

    @Override
    public int getItemCount() {
        return plant_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView common_name, scientific_name, family_common_name;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.plant_image);
            common_name = itemView.findViewById(R.id.common_name_textview);
            scientific_name = itemView.findViewById(R.id.scientific_name_textview);
            family_common_name = itemView.findViewById(R.id.family_common_name_textview);

            parentLayout = itemView.findViewById(R.id.parent);
        }
    }
}