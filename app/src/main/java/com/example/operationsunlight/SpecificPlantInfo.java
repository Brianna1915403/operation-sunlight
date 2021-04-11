package com.example.operationsunlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import javax.xml.transform.Result;

public class SpecificPlantInfo extends AppCompatActivity {

    TextView commonName, scientificName, familyCommonName,
    isVegetable, isEdible, daysToHarvest, rowSpacing, spread,
    ph_min_max, light, precipitation_min_max, min_root_depth,
    temperature_min_max;

    String final_request = "";

    int plant_id;

    private ProgressDialog progressDialog;

    RecyclerViewAdapter adapter;

    PlantInfo plantInfo;

    ImageView plantImage;

    private static String url = "https://trefle.io/api/v1/plants/";
    private final static String token = "?token=dZWkjKZTg7acXlHla7dapXq4-cAgxA4nU1eqHCA763M";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_plant_info);

        Intent intent = getIntent();

        plant_id = intent.getIntExtra("plant_id", 1);

        final_request = url + plant_id + token;

        plantImage = findViewById(R.id.plantImage);

        try {
            new SpecificPlantInfo.GetPlantInfo().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Glide.with(SpecificPlantInfo.this)
                .asBitmap()
                .load("")
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.error_file)
                .into(plantImage);
        commonName = findViewById(R.id.commonNameTextView);
        commonName.setText(plantInfo.getCommon_name());
        scientificName = findViewById(R.id.scientificNameTextView);
        scientificName.setText(plantInfo.getScientific_name());
        familyCommonName = findViewById(R.id.familyCommonNameTextView);
        familyCommonName.setText(plantInfo.getFamily_common_name());
        isVegetable = findViewById(R.id.isVegetableTextView);
        isVegetable.setText(plantInfo.isVegetable() ? "Yes":"No");
        daysToHarvest = findViewById(R.id.days_To_HarvestTextView);
        daysToHarvest.setText(plantInfo.getDaysToHarvest());
        rowSpacing = findViewById(R.id.row_spacingTextView);
        rowSpacing.setText(plantInfo.getRowSpacing());
        spread = findViewById(R.id.spreadTextView);
        spread.setText(plantInfo.getSpread());
        ph_min_max = findViewById(R.id.ph_min_max_TextView);
        ph_min_max.setText(plantInfo.getMin_ph() + " to " + plantInfo.getMax_ph());
        light = findViewById(R.id.lightTextView);
        light.setText(plantInfo.getLight());
        precipitation_min_max = findViewById(R.id.precipitation_min_maxTextView);
        precipitation_min_max.setText(plantInfo.getMin_precipitation() + " to " + plantInfo.getMax_precipitation());
        min_root_depth = findViewById(R.id.min_root_depthTextView);
        min_root_depth.setText(plantInfo.getMin_root_depth());
        temperature_min_max = findViewById(R.id.temperature_min_max_TextView);
        temperature_min_max.setText(plantInfo.getMin_temperature() + " to " + plantInfo.getMax_temperature());


    }


    private class GetPlantInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SpecificPlantInfo.this);
            progressDialog.setMessage("Retrieving Data...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HTTPHandler handler = new HTTPHandler();
            String jsonStr = handler.makeServiceCall(final_request);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONObject plants = jsonObject.getJSONObject("data");

                        plantInfo = new PlantInfo(
                        plant_id,
                        plants.getString("common_name"),
                        plants.getString("scientific_name"),
                        plants.getString("family_common_name"),
                        plants.getString("image_url"),
                        plants.getBoolean("vegetable"),
                        plants.getInt("main_species.growth.days_to_harvest"),
                        plants.getString("main_species.growth.row_spacing.cm"),
                        plants.getString("main_species.growth.spread.cm"),
                        plants.getInt("main_species.growth.ph_minimum"),
                        plants.getInt("main_species.growth.ph_maximum"),
                        plants.getInt("main_species.growth.light"),
                        plants.getInt("main_species.growth.minumum_precipitation.mm"),
                        plants.getInt("main_species.growth.maximum_precipitation.mm"),
                        plants.getInt("main_species.growth.minimum_root_depth.cm"),
                        plants.getInt("main_species.growth.minimum_temperature.deg_c"),
                        plants.getInt("main_species.growth.maximum_temperature.deg_c"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }
}