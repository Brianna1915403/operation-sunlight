package com.example.operationsunlight.modules.garden;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.operationsunlight.R;
import com.example.operationsunlight.modules.login.User;
import com.example.operationsunlight.modules.plant.OnPlantListener;
import com.example.operationsunlight.modules.plant.Plant;
import com.example.operationsunlight.modules.plant.PlantRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class GardenFragment extends Fragment implements OnPlantListener {
    View root;
    private RecyclerView recyclerView;
    private PlantRecyclerAdapter adapter;
    private ArrayList<Plant> plant_list = new ArrayList<>();
    private OnPlantListener onPlantListener = this::onPlantClick;

    DatabaseReference userRef, gardenRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_need_sign_in, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        if (preferences.getString("USERNAME", null) == null)
            return root;
        else
            root = inflater.inflate(R.layout.fragment_garden, container, false);
        gardenRef = FirebaseDatabase.getInstance().getReference().child("GARDEN");
        userRef = FirebaseDatabase.getInstance().getReference().child("USER").child(preferences.getString("USERNAME", null));
        recyclerView = root.findViewById(R.id.plant_recyclerview);
        adapter = new PlantRecyclerAdapter(plant_list, root.getContext(), onPlantListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getGarden();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void getGarden() {
        plant_list.clear();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                String gardenRef_str = user.getGardenRef();
                if (gardenRef_str == null) {
                    gardenRef_str = gardenRef.push().getKey();
                    user.setGardenRef(gardenRef_str);
                    userRef.setValue(user);
                }
                gardenRef.child(gardenRef_str).get().addOnSuccessListener(dataSnapshot -> {
                    if (dataSnapshot.getValue() == null)
                        return;
                    Log.d("GARDEN::GET_GARDEN", dataSnapshot.getValue().toString());
                    for (Object obj : (List) dataSnapshot.getValue()) {
                        HashMap map = (HashMap) obj;
                        Plant plant = new Plant(Long.parseLong(map.get("plant_id").toString()),
                                map.get("common_name").toString(), map.get("scientific_name").toString(),
                                map.get("family_common_name").toString(), map.get("image_url").toString());
                        Log.d("GARDEN::GET_GARDEN", plant.toString());
                        plant_list.add(plant);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public void onPlantClick(long plant_id) {
        NavController navController = NavHostFragment.findNavController(this);
        Bundle bundle = new Bundle();
        bundle.putLong("plant_id", plant_id);
        navController.navigate(R.id.nav_plant_bio, bundle);
    }
}