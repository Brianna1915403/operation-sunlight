package com.example.operationsunlight.ui.plant;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.operationsunlight.HTTPHandler;
import com.example.operationsunlight.R;
import com.example.operationsunlight.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlantFragment extends Fragment implements onPlantListener {
    private View root;
    private RecyclerView recyclerView;
    private PlantRecyclerAdapter adapter;
    private ArrayList<Plant> plant_list = new ArrayList<>();
    private SearchView searchView;
    private ToggleButton order;
    private ProgressDialog progressDialog;

    private static String url = "https://trefle.io/api/v1/plants";
    private final static String token = "?token=dZWkjKZTg7acXlHla7dapXq4-cAgxA4nU1eqHCA763M";
    private static String search = "/search" + token + "&q=";
    private static String pagination = "&page=";
    private static String sort = "&order[common_name]=";

    private boolean isAscendingOrder = true;
    private int currentPage = 1;
    private int totalResults;
    private static String final_request = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_plant, container, false);
        recyclerView = root.findViewById(R.id.plant_recyclerview);
        adapter = new PlantRecyclerAdapter(plant_list, root.getContext(), this::onPlantClick);
        searchView = root.findViewById(R.id.plant_search);
        order = root.findViewById(R.id.sort_toggle);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Makes it so the entire search bar is clickable not just the icon
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                isAscendingOrder = !order.isChecked();
                final_request = url + search + query + pagination + currentPage + sort + (isAscendingOrder? "asc" : "desc");
                clearRecycler();
                new GetPlants().execute();
                progressDialog.dismiss();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final_request = final_request.substring(0, final_request.lastIndexOf('=') + 1).concat(isChecked? "desc" : "asc");
                clearRecycler();
                new GetPlants().execute();
            }
        });

        isAscendingOrder = !order.isChecked();
        clearRecycler();
        if (final_request.isEmpty())
            final_request = url + token + sort + (isAscendingOrder? "asc" : "desc");
        new GetPlants().execute();
    }

    private void clearRecycler() {
        plant_list.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPlantClick(int plant_id) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        Bundle bundle = new Bundle();
        bundle.putInt("plant_id", plant_id);
        navController.navigate(R.id.nav_plant_bio, bundle);
    }

    private class GetPlants extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(root.getContext());
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
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            progressDialog.dismiss();
        }
    }
}