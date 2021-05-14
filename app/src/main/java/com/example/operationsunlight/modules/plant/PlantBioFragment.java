package com.example.operationsunlight.modules.plant;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.operationsunlight.R;
import com.example.operationsunlight.modules.garden.Garden;
import com.example.operationsunlight.modules.login.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class PlantBioFragment extends Fragment implements View.OnClickListener {
    View root;
    SharedPreferences preferences;
    DatabaseReference plantRef, gardenRef, userRef;

    private ImageView plantImage;
    private TextView commonName, scientificName, familyCommonName,
            isVegetable, daysToHarvest, rowSpacing, spread,
            ph_min_max, light, precipitation_min_max, min_root_depth,
            temperature_min_max, plant_fab_label, remove_fab_label;
    private FloatingActionButton menu_fab, plant_fab, remove_fab;
    private boolean isFabVisible = false;
    private long plant_id;
    List<Plant> plantList = new ArrayList<>();
    Plant plant;
    PlantBio plantBio;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_plant_bio, container, false);
        preferences = getActivity().getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE);
        Bundle bundle = getArguments();
        plant_id = bundle.getLong("plant_id");
        gardenRef = FirebaseDatabase.getInstance().getReference().child("GARDEN");
        plantRef = FirebaseDatabase.getInstance().getReference().child("PLANT").child(String.valueOf(plant_id));
        userRef = FirebaseDatabase.getInstance().getReference().child("USER").child(preferences.getString("USERNAME", null));
        getGarden();
        getPlant();

        plantImage = root.findViewById(R.id.plantImage);
        commonName = root.findViewById(R.id.commonNameTextView);
        scientificName = root.findViewById(R.id.scientificNameTextView);
        familyCommonName = root.findViewById(R.id.familyCommonNameTextView);
        isVegetable = root.findViewById(R.id.isVegetableTextView);
        daysToHarvest = root.findViewById(R.id.days_To_HarvestTextView);
        rowSpacing = root.findViewById(R.id.row_spacingTextView);
        ph_min_max = root.findViewById(R.id.ph_min_max_TextView);
        light = root.findViewById(R.id.lightTextView);
        precipitation_min_max = root.findViewById(R.id.precipitation_min_maxTextView);
        min_root_depth = root.findViewById(R.id.min_root_depthTextView);
        temperature_min_max = root.findViewById(R.id.temperature_min_max_TextView);

        remove_fab_label = root.findViewById(R.id.plant_remove_fab_label);
        remove_fab = root.findViewById(R.id.plant_remove_fab);
        remove_fab.setVisibility(View.GONE);
        remove_fab_label.setVisibility(View.GONE);
        remove_fab.setOnClickListener(this::onClick);

        plant_fab_label = root.findViewById(R.id.plant_plant_fab_label);
        plant_fab = root.findViewById(R.id.plant_plant_fab);
        plant_fab.setVisibility(View.GONE);
        plant_fab_label.setVisibility(View.GONE);
        plant_fab.setOnClickListener(this::onClick);

        menu_fab = root.findViewById(R.id.plant_menu_fab);
        menu_fab.setOnClickListener(this::onClick);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plant_menu_fab:
                isFabVisible = !isFabVisible;
                plant_fab.setVisibility(isFabVisible? View.VISIBLE : View.GONE);
                plant_fab_label.setVisibility(isFabVisible? View.VISIBLE : View.GONE);
                remove_fab.setVisibility(isFabVisible? View.VISIBLE : View.GONE);
                remove_fab_label.setVisibility(isFabVisible? View.VISIBLE : View.GONE);
                remove_fab.setEnabled(plantList.contains(plant));
                plant_fab.setEnabled(!plantList.contains(plant));
                break;
            case R.id.plant_plant_fab:
                remove_fab.setEnabled(true);
                plant_fab.setEnabled(false);
                addPlantToGarden();
                Toast.makeText(root.getContext(), plant.common_name + " has been planted!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.plant_remove_fab:
                plant_fab.setEnabled(true);
                remove_fab.setEnabled(false);
                removePlantFromGarden();
                Toast.makeText(root.getContext(), plant.common_name + " has been removed.", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void getGarden() {
        plantList.clear();
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
                    Log.d("PLANT_BIO::GET_GARDEN", dataSnapshot.getValue().toString());
                    for (Object obj : (List) dataSnapshot.getValue()) {
                        HashMap map = (HashMap) obj;
                        Plant plant = new Plant(Long.parseLong(map.get("plant_id").toString()),
                                map.get("common_name").toString(), map.get("scientific_name").toString(),
                                map.get("family_common_name").toString(), map.get("image_url").toString());
                        Log.d("GARDEN::GET_GARDEN", plant.toString());
                        plantList.add(plant);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void addPlantToGarden() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                String gardenRef_str = user.getGardenRef();
                if (gardenRef_str == null) {
                    gardenRef_str = gardenRef.push().getKey();
                    user.setGardenRef(gardenRef_str);
                    userRef.setValue(user);
                    return;
                }
                plantList.add(plant);
                gardenRef.child(gardenRef_str).setValue(plantList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void removePlantFromGarden() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                String gardenRef_str = user.getGardenRef();
                if (gardenRef_str == null) {
                    gardenRef_str = gardenRef.push().getKey();
                    user.setGardenRef(gardenRef_str);
                    userRef.setValue(user);
                    return;
                }
                plantList.remove(plant);
                gardenRef.child(gardenRef_str).setValue(plantList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void getPlant() {
        plantRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String common_name = (String) snapshot.child("common_name").getValue();
                String scientific_name = (String) snapshot.child("scientific_name").getValue();
                String family_common_name = (String) snapshot.child("family_common_name").getValue();
                String image_url = (String) snapshot.child("image_url").getValue();
                String is_Vegetable = snapshot.child("isVegetable").getValue().toString();
                String days_To_Harvest = (String) snapshot.child("daysToHarvest").getValue();
                String row_Spacing = (String) snapshot.child("rowSpacing").getValue();
                String min_ph = snapshot.child("min_pH").getValue().toString();
                String max_ph = snapshot.child("max_pH").getValue().toString();
                String light_level = (String) snapshot.child("light").getValue();
                String min_precipitation = (String) snapshot.child("min_precipitation").getValue();
                String max_precipitation = (String) snapshot.child("max_precipitation").getValue();
                String min_Root_Depth = (String) snapshot.child("min_root_depth").getValue();
                String min_temperature = snapshot.child("min_temp").getValue().toString();
                String max_temperature = snapshot.child("max_temp").getValue().toString();

                plant = new Plant(plant_id, common_name, scientific_name, family_common_name, image_url);
                plantBio = new PlantBio(plant, is_Vegetable, days_To_Harvest, row_Spacing, min_ph, max_ph,
                        light_level, min_precipitation, max_precipitation,
                        min_Root_Depth, min_temperature, max_temperature);

                Picasso.get()
                        .load(plantBio.getImage_url())
                        .placeholder(R.drawable.error_file)
                        .error(R.drawable.error_file)
                        .into(plantImage);

                commonName.setText(plantBio.getCommon_name());
                scientificName.setText("Scientific Name: " + plantBio.getScientific_name());
                familyCommonName.setText("Family Common Name: " + plantBio.getFamily_common_name());
                isVegetable.setText("Is vegetable: " + plantBio.isVegetable());
                daysToHarvest.setText("Days to Harvest: " + plantBio.getDaysToHarvest() + " days");
                rowSpacing.setText("Row Spacing: " + plantBio.getRowSpacing() + " cm");
                ph_min_max.setText("Ph Range: " + plantBio.getMin_ph() + " to " + plantBio.getMax_ph());
                light.setText("Light: " + plantBio.getLight());
                precipitation_min_max.setText("Precipitation Range: " + plantBio.getMin_precipitation() + " mm" + " to " + plantBio.getMax_precipitation() + " mm");
                min_root_depth.setText("Minimum Root Depth: " + plantBio.getMin_root_depth() + " cm");
                temperature_min_max.setText("Temperature Range: " + plantBio.getMin_temperature() + " \u2103" + " to " + plantBio.getMax_temperature() + " \u2103");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
