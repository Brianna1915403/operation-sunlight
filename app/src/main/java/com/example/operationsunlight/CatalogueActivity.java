package com.example.operationsunlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.operationsunlight.ui.plant.Plant;
import com.example.operationsunlight.ui.plant.PlantRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CatalogueActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private int totalResults;

    private RecyclerView recyclerView;


    private static String url = "https://trefle.io/api/v1/plants";
    private final static String token = "?token=dZWkjKZTg7acXlHla7dapXq4-cAgxA4nU1eqHCA763M";
    private static String search = "/search" + token + "&q=";
    private static String pagination = "&page=";
    private static String sort = "&order[common_name]=";

    private static String final_request = "";

    private String searchQuery = "";
    private boolean isAscendingOrder = true;
    private int currentPage = 1;

    Button searchBTN, nextBTN, previousBTN;
    EditText searchBar;
    ToggleButton ascendingOrder;
    TextView pageNum;

    int max_pages = 1;

    ArrayList<Plant> plant_list = new ArrayList<>();

    PlantRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        adapter = new PlantRecyclerAdapter(plant_list, CatalogueActivity.this);
        searchBTN = findViewById(R.id.searchButton);
        nextBTN = findViewById(R.id.nextPageButton);
        previousBTN = findViewById(R.id.previousPageButton);

        searchBar = findViewById(R.id.searchBar);
        ascendingOrder = findViewById(R.id.ascendingOrder_ToggleButton);
        recyclerView = findViewById(R.id.recyclerView);

        pageNum = findViewById(R.id.pageNumberTextView);

        pageNum.setText("No Query Entered");

        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentPage = 1;
                searchQuery = searchBar.getText().toString();
                isAscendingOrder = ascendingOrder.isChecked();
                if(searchQuery.isEmpty())
                    return;
                if(isAscendingOrder == false){
                    final_request = url + search + searchQuery + pagination + currentPage + sort + "asc";
                }else if(isAscendingOrder == true){
                    final_request = url + search + searchQuery + pagination + currentPage + sort + "desc";
                }
                clearRecycler();
                new GetPlants().execute();
                max_pages = (totalResults/20) + 1;
                pageNum.setText("Page " + currentPage + " out of " + max_pages);
            }

        });

        nextBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPage < max_pages)
                currentPage++;
                if(isAscendingOrder == false){
                    final_request = url + search + searchQuery + pagination + currentPage + sort + "asc";
                }else if(isAscendingOrder == true){
                    final_request = url + search + searchQuery + pagination + currentPage + sort + "desc";
                }
                clearRecycler();
                new GetPlants().execute();
                pageNum.setText("Page " + currentPage + " out of " + max_pages);
            }
        });

        previousBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPage > 1)
                currentPage--;
                if(isAscendingOrder == false){
                    final_request = url + search + searchQuery + pagination + currentPage + sort + "asc";
                }else if(isAscendingOrder == true){
                    final_request = url + search + searchQuery + pagination + currentPage + sort + "desc";
                }
                clearRecycler();
                new GetPlants().execute();
                pageNum.setText("Page " + currentPage + " out of " + max_pages);
            }
        });




//
//        listView = findViewById(R.id.list);
//        contactList = new ArrayList<>();
//        new GetContacts().execute();
    }

    private void clearRecycler(){
        plant_list.clear();
        adapter.notifyDataSetChanged();
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
            String jsonStr = handler.makeServiceCall(final_request);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray plants = jsonObject.getJSONArray("data");
                    totalResults = jsonObject.getJSONObject("meta").getInt("total");
                    System.out.println(totalResults);
                    for (int i = 0; i < plants.length(); ++i) {
                        JSONObject object = plants.getJSONObject(i);

                        plant_list.add( new Plant(object.getInt("id"), object.getString("common_name"),
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

            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(CatalogueActivity.this));
        }
    }
}