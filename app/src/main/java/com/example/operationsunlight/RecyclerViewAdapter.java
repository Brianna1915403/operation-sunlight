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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    ArrayList<String> mImageURIs = new ArrayList<>();
    ArrayList<String> common_name = new ArrayList<>();
    ArrayList<String> scientific_name = new ArrayList<>();
    Context mContext;

    public RecyclerViewAdapter(ArrayList<String> mImages, ArrayList<String> mCommonNames,
                               ArrayList<String> mScientificNames, Context mContext) {
        this.mImageURIs = mImages;
        this.common_name = mCommonNames;
        this.scientific_name = mScientificNames;
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
                .load(mImageURIs.get(position))
                .into(holder.image);

        holder.common_name.setText(common_name.get(position));
        holder.scientific_name.setText(scientific_name.get(position));

    }

    @Override
    public int getItemCount() {
        return common_name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView common_name, scientific_name;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.images);
            common_name = itemView.findViewById(R.id.common_nameTextView);
            scientific_name = itemView.findViewById(R.id.scientific_nameTextView);

            parentLayout = itemView.findViewById(R.id.parent);
        }
    }
}