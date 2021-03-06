package com.example.operationsunlight.modules.plant;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.operationsunlight.HTTPHandler;
import com.example.operationsunlight.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
//TODO: Read from DB
public class PlantBioFragment_old extends Fragment {
    private static final String TAG = "ARGS" ; // Can be removed only for debug purposes
    private View root;
    private PlantBio plantBio;
    private Plant plant;
    private long plant_id;

//    private static String url = "https://trefle.io/api/v1/plants/";
//    private final static String token = "?token=dZWkjKZTg7acXlHla7dapXq4-cAgxA4nU1eqHCA763M";
//    private String final_request;

    private ImageView plantImage;
    private TextView commonName, scientificName, familyCommonName,
            isVegetable, daysToHarvest, rowSpacing, spread,
            ph_min_max, light, precipitation_min_max, min_root_depth,
            temperature_min_max, plant_fab_label, note_fab_label;
    private FloatingActionButton menu_fab, plant_fab, note_fab;
    private boolean isFabVisible = false;

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_plant_bio, container, false);
        Bundle bundle = getArguments();
        plant_id = bundle.getLong("plant_id");
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
        plant_fab_label = root.findViewById(R.id.plant_plant_fab_label);
        plant_fab = root.findViewById(R.id.plant_plant_fab);
        plant_fab.setVisibility(View.GONE);
        plant_fab_label.setVisibility(View.GONE);
        plant_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(root.getContext(), plant + " Planted!", Toast.LENGTH_LONG).show();
            }
        });
        menu_fab = root.findViewById(R.id.plant_menu_fab);
        menu_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFabVisible = !isFabVisible;
                note_fab.setVisibility(isFabVisible? View.VISIBLE : View.GONE);
                note_fab_label.setVisibility(isFabVisible? View.VISIBLE : View.GONE);
                plant_fab.setVisibility(isFabVisible? View.VISIBLE : View.GONE);
                plant_fab_label.setVisibility(isFabVisible? View.VISIBLE : View.GONE);
            }
        });
//        TextView trefle_disclaimer = root.findViewById(R.id.plant_bio_trefle_disclaimer);
//        trefle_disclaimer.setMovementMethod(LinkMovementMethod.getInstance());
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        final_request = url + plant_id + token;
//        try {
//            new GetPlantInfo().execute().get();
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }

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

//    private class GetPlantInfo extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(root.getContext());
//            progressDialog.setMessage("Loading...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            HTTPHandler handler = new HTTPHandler();
//            String jsonStr = handler.makeServiceCall(final_request);
//            if (jsonStr != null) {
//                try {
//                    JSONObject jsonObject = new JSONObject(jsonStr);
//                    JSONObject jsonPlant = jsonObject.getJSONObject("data");
//
//                    plant = new Plant(
//                            plant_id,
//                            jsonPlant.getString("common_name"),
//                            jsonPlant.getString("scientific_name"),
//                            jsonPlant.getString("family_common_name"),
//                            jsonPlant.getString("image_url"));
//
//                    JSONObject main_species = jsonPlant.getJSONObject("main_species");
//                    JSONObject growth = main_species.getJSONObject("growth");
//
//                    JSONObject row_spacing = growth.getJSONObject("row_spacing");
//                    JSONObject spread = growth.getJSONObject("spread");
//
//                    JSONObject minimum_precipitation = growth.getJSONObject("minimum_precipitation");
//                    JSONObject maximum_precipitation = growth.getJSONObject("maximum_precipitation");
//                    JSONObject minimum_root_depth = growth.getJSONObject("minimum_root_depth");
//                    JSONObject minimum_temperature = growth.getJSONObject("minimum_temperature");
//                    JSONObject maximum_temperature = growth.getJSONObject("maximum_temperature");
//
//                    plantBio = new PlantBio(
//                            plant,
//                            jsonPlant.getString("vegetable"),
//                            growth.getString("days_to_harvest"),
//                            row_spacing.getString("cm"),
//                            spread.getString("cm"),
//                            growth.getString("ph_minimum"),
//                            growth.getString("ph_maximum"),
//                            growth.getString("light"),
//                            minimum_precipitation.getString("mm"),
//                            maximum_precipitation.getString("mm"),
//                            minimum_root_depth.getString("cm"),
//                            minimum_temperature.getString("deg_c"),
//                            maximum_temperature.getString("deg_c"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            progressDialog.dismiss();
//        }
//    }
}