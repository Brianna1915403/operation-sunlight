package com.example.operationsunlight.modules.plant;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.operationsunlight.HTTPHandler;
import com.example.operationsunlight.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PlantFragment extends Fragment implements onPlantListener {
    private View root;
    private RecyclerView recyclerView;
    private PlantRecyclerAdapter adapter;
    private ArrayList<Plant> plant_list = new ArrayList<>();
    private SearchView searchView;
    private ToggleButton order;
    private ImageButton nextBTN, previousBTN;
    private TextView pageNum;
    private ProgressDialog progressDialog;
    private onPlantListener onPlantListener = this::onPlantClick;

    private static String url = "https://trefle.io/api/v1/plants";
    private final static String token = "?token=dZWkjKZTg7acXlHla7dapXq4-cAgxA4nU1eqHCA763M";
    private static String search = "/search" + token + "&q=";
    private static String pagination = "&page=";
    private static String sort = "&order[common_name]=";

    private String searchQuery = "";
    private boolean isAscendingOrder = true;
    private int currentPage = 1;
    int max_pages = 1;
    private int totalResults;
    private static String final_request = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_no_connection, container, false);
        if (!HTTPHandler.hasInternetConnection(getActivity(), root.getContext()))
            return root;
        else
            root = inflater.inflate(R.layout.fragment_plant, container, false);

        TextView trefle_disclaimer = root.findViewById(R.id.plant_trefle_disclaimer);
        trefle_disclaimer.setMovementMethod(LinkMovementMethod.getInstance());
        recyclerView = root.findViewById(R.id.plant_recyclerview);
        adapter = new PlantRecyclerAdapter(plant_list, root.getContext(), onPlantListener);
        searchView = root.findViewById(R.id.plant_search);
        searchView.setOnClickListener(new View.OnClickListener() { // Makes it so the entire search bar is clickable not just the icon
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if(searchView.getQuery().toString().isEmpty()) {
                    final_request = "";
                    searchQuery = "";
                    updateRecycler();
                }
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                currentPage = 1;
                final_request = url + search + searchQuery + pagination + currentPage + sort + (isAscendingOrder? "asc" : "desc");
                updateRecycler();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { return false; }
        });

        order = root.findViewById(R.id.sort_toggle);
        order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAscendingOrder = !isChecked;
                final_request = final_request.substring(0, final_request.lastIndexOf('=') + 1).concat(isAscendingOrder? "asc" : "desc");
                updateRecycler();
            }
        });

        nextBTN = root.findViewById(R.id.nextPageButton);
        nextBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPage < max_pages) {
                    currentPage++;
                    final_request = url + (searchQuery.isEmpty() ? token : search + searchQuery) + pagination + currentPage + sort + (isAscendingOrder ? "asc" : "desc");
                    updateRecycler();
                    updatePage();
                }
            }
        });

        previousBTN = root.findViewById(R.id.previousPageButton);
        previousBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPage > 1) {
                    currentPage--;
                    final_request = url + (searchQuery.isEmpty() ? token : search + searchQuery) + pagination + currentPage + sort + (isAscendingOrder ? "asc" : "desc");
                    updateRecycler();
                    updatePage();
                }
            }
        });

        pageNum = root.findViewById(R.id.pageNumberTextView);

        isAscendingOrder = !order.isChecked();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!HTTPHandler.hasInternetConnection(getActivity(), root.getContext()))
            return;
        updateRecycler();
    }

    @Override
    public void onPlantClick(int plant_id) {
        NavController navController = NavHostFragment.findNavController(this);
        Bundle bundle = new Bundle();
        bundle.putInt("plant_id", plant_id);
        navController.navigate(R.id.nav_plant_bio, bundle);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        final_request = "";
    }

    private void updatePage() {
        max_pages = (totalResults / 20) + 1;
        pageNum.setText("Page " + currentPage + " out of " + max_pages);
    }

    private void clearRecycler() {
        plant_list.clear();
        adapter.notifyDataSetChanged();
    }

    private void updateRecycler() {
        try {
            if (final_request.isEmpty())
                final_request = url + token + pagination + currentPage + sort + (isAscendingOrder ? "asc" : "desc");
            clearRecycler();
            new GetPlants().execute().get();
            System.out.println("updateRecycler:: " + final_request);
            updatePage();
            progressDialog.dismiss();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class GetPlants extends AsyncTask<Void, Void, Void> {
        final HTTPHandler handler = new HTTPHandler();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(root.getContext());
            progressDialog.setMessage("Loading Data...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String jsonStr = handler.makeServiceCall(final_request);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray plantObjects = jsonObject.getJSONArray("data");
                    totalResults = jsonObject.getJSONObject("meta").getInt("total");
                    for (int i = 0; i < plantObjects.length(); ++i) {
                        JSONObject plantObject = plantObjects.getJSONObject(i);
                        plant_list.add( new Plant(
                                plantObject.getInt("id"),
                                plantObject.getString("common_name"),
                                plantObject.getString("scientific_name"),
                                plantObject.getString("family_common_name"),
                                plantObject.getString("image_url")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new PlantRecyclerAdapter(plant_list, root.getContext(), onPlantListener);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            progressDialog.dismiss();
        }
    }
}