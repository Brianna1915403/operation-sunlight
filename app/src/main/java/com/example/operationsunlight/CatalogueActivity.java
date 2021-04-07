package com.example.operationsunlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CatalogueActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private int totalResults;

    private RecyclerView recyclerView;


    private static String url = "https://trefle.io/api/v1/plants";
    private final static String token = "?token=dZWkjKZTg7acXlHla7dapXq4-cAgxA4nU1eqHCA763M";
    private static String search = "/search" + token + "&q=";
    private static String pagination = "&page=";


   ArrayList<Plant> plant_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        new GetPlants().execute();


//
//        listView = findViewById(R.id.list);
//        contactList = new ArrayList<>();
//        new GetContacts().execute();
    }

    private class GetPlants extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CatalogueActivity.this);
            progressDialog.setMessage("Retrieving Data...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HTTPHandler handler = new HTTPHandler();
            String jsonStr = handler.makeServiceCall(url + token);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray plants = jsonObject.getJSONArray("data");
                    totalResults = jsonObject.getJSONObject("meta").getInt("total");
                    System.out.println(totalResults);
                    for (int i = 0; i < plants.length(); ++i) {
                        JSONObject object = plants.getJSONObject(i);

                        plant_list.add( new Plant(object.getString("common_name"),
                                object.getString("scientific_name"),
                                object.getString("family_common_name"),
                                object.getString("image_url")));
                    }
                    System.out.println(plant_list);
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

            RecyclerViewAdapter adapter = new RecyclerViewAdapter(plant_list, CatalogueActivity.this);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(CatalogueActivity.this));
        }
    }
}