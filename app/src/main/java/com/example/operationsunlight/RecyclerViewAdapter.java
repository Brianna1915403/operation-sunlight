package com.example.operationsunlight;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.URL;
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
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(plant_list.get(position).image_url)
                .error(R.drawable.error_image)
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
            image = itemView.findViewById(R.id.images);
            common_name = itemView.findViewById(R.id.common_nameTextView);
            scientific_name = itemView.findViewById(R.id.scientific_nameTextView);
            family_common_name = itemView.findViewById(R.id.family_common_nameTextView);

            parentLayout = itemView.findViewById(R.id.parent);
        }
    }
}