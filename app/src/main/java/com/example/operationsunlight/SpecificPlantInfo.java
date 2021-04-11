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
                .load(plantInfo.getImage_url())
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.error_file)
                .into(plantImage);
        commonName = findViewById(R.id.commonNameTextView);
        commonName.setText(plantInfo.getCommon_name());
        scientificName = findViewById(R.id.scientificNameTextView);
        scientificName.setText("Scientific Name: " + plantInfo.getScientific_name());
        familyCommonName = findViewById(R.id.familyCommonNameTextView);
        familyCommonName.setText("Family Common Name: " + plantInfo.getFamily_common_name());
        isVegetable = findViewById(R.id.isVegetableTextView);
        isVegetable.setText("Is vegetable: " + (plantInfo.isVegetable() ? "Yes":"No"));
        daysToHarvest = findViewById(R.id.days_To_HarvestTextView);
        daysToHarvest.setText("Days to Harvest: " + plantInfo.getDaysToHarvest() + " days");
        rowSpacing = findViewById(R.id.row_spacingTextView);
        rowSpacing.setText("Row Spacing: " + plantInfo.getRowSpacing() + "cm");
        spread = findViewById(R.id.spreadTextView);
        spread.setText("Spread: " + plantInfo.getSpread() + "cm");
        ph_min_max = findViewById(R.id.ph_min_max_TextView);
        ph_min_max.setText("Ph Range: " + plantInfo.getMin_ph() + " to " + plantInfo.getMax_ph());
        light = findViewById(R.id.lightTextView);
        light.setText("Light: " + plantInfo.getLight());
        precipitation_min_max = findViewById(R.id.precipitation_min_maxTextView);
        precipitation_min_max.setText("Precipitation Range: " + plantInfo.getMin_precipitation() + "mm" + " to " + plantInfo.getMax_precipitation() + "mm");
        min_root_depth = findViewById(R.id.min_root_depthTextView);
        min_root_depth.setText("Minimum Root Depth: " + plantInfo.getMin_root_depth() + "cm");
        temperature_min_max = findViewById(R.id.temperature_min_max_TextView);
        temperature_min_max.setText("Temperature Range: " + plantInfo.getMin_temperature() + "\u2103" + " to " + plantInfo.getMax_temperature() + "\u2103");


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

                    JSONObject main_species = plants.getJSONObject("main_species");
                    JSONObject growth = main_species.getJSONObject("growth");

                    JSONObject row_spacing = growth.getJSONObject("row_spacing");
                    JSONObject spread = growth.getJSONObject("spread");

                    JSONObject minimum_precipitation = growth.getJSONObject("minimum_precipitation");
                    JSONObject maximum_precipitation = growth.getJSONObject("maximum_precipitation");
                    JSONObject minimum_root_depth = growth.getJSONObject("minimum_root_depth");
                    JSONObject minimum_temperature = growth.getJSONObject("minimum_temperature");
                    JSONObject maximum_temperature = growth.getJSONObject("maximum_temperature");

                    System.out.println("Printing JSON: " + plants);

                        plantInfo = new PlantInfo(
                        plant_id,
                        plants.getString("common_name"),
                        plants.getString("scientific_name"),
                        plants.getString("family_common_name"),
                        plants.getString("image_url"),
                        plants.getBoolean("vegetable"),
                        growth.getString("days_to_harvest"),
                        row_spacing.getString("cm"),
                        spread.getString("cm"),
                        growth.getString("ph_minimum"),
                        growth.getString("ph_maximum"),
                        growth.getString("light"),
                        minimum_precipitation.getString("mm"),
                        maximum_precipitation.getString("mm"),
                        minimum_root_depth.getString("cm"),
                        minimum_temperature.getString("deg_c"),
                        maximum_temperature.getString("deg_c"));

                        System.out.println("Printing plant Object" + plantInfo);

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