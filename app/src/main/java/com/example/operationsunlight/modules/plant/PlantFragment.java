package com.example.operationsunlight.modules.plant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
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
import com.example.operationsunlight.modules.login.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PlantFragment extends Fragment implements OnPlantListener {
    private View root;
    private RecyclerView recyclerView;
    private PlantRecyclerAdapter adapter;
    private ArrayList<Plant> plant_list = new ArrayList<>();
    DatabaseReference reference;
    SharedPreferences preferences;
    long ID = 0;
    private OnPlantListener onPlantListener = this::onPlantClick;

    private SearchView searchView;
    private ToggleButton order;
    private ImageButton nextBTN, previousBTN;
    private TextView pageNum;
    private ProgressDialog progressDialog;

//    TREFLE HAS BEEN DISCONTINUED HENCE THE CODE IS DEPRECATED
//    private static String url = "https://trefle.io/api/v1/plants";
//    private final static String token = "?token=dZWkjKZTg7acXlHla7dapXq4-cAgxA4nU1eqHCA763M";
//    private static String search = "/search" + token + "&q=";
//    private static String pagination = "&page=";
//    private static String sort = "&order[common_name]=";
//
//    private String searchQuery = "";
//    private boolean isAscendingOrder = true;
//    private int currentPage = 1;
//    int max_pages = 1;
//    private int totalResults;
//    private static String final_request = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_need_sign_in, container, false);
        preferences = getActivity().getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE);
        if (preferences.getString("USERNAME", null) == null) {
            return root;
        } else
            root = inflater.inflate(R.layout.fragment_plant, container, false);
        reference = FirebaseDatabase.getInstance().getReference().child("PLANT");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    ID = snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        recyclerView = root.findViewById(R.id.plant_recyclerview);
        adapter = new PlantRecyclerAdapter(plant_list, root.getContext(), onPlantListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                plant_list.add(snapshot.getValue(Plant.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
//        TextView trefle_disclaimer = root.findViewById(R.id.plant_trefle_disclaimer);
//        trefle_disclaimer.setMovementMethod(LinkMovementMethod.getInstance());
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
//                if(searchView.getQuery().toString().isEmpty()) {
//                    final_request = "";
//                    searchQuery = "";
                    updateRecycler();
//                }
                Toast.makeText(root.getContext(), "Trefel has been discontinued, hence we cannot preform this action", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                searchQuery = query;
//                currentPage = 1;
//                final_request = url + search + searchQuery + pagination + currentPage + sort + (isAscendingOrder? "asc" : "desc");
                updateRecycler();
                Toast.makeText(root.getContext(), "Trefel has been discontinued, hence we cannot preform this action", Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { return false; }
        });

        order = root.findViewById(R.id.sort_toggle);
        order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                isAscendingOrder = !isChecked;
//                final_request = final_request.substring(0, final_request.lastIndexOf('=') + 1).concat(isAscendingOrder? "asc" : "desc");
                updateRecycler();
                Toast.makeText(root.getContext(), "Trefel has been discontinued, hence we cannot preform this action", Toast.LENGTH_LONG).show();
            }
        });

        nextBTN = root.findViewById(R.id.nextPageButton);
        nextBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(currentPage < max_pages) {
//                    currentPage++;
//                    final_request = url + (searchQuery.isEmpty() ? token : search + searchQuery) + pagination + currentPage + sort + (isAscendingOrder ? "asc" : "desc");
                    updateRecycler();
//                    updatePage();
//                }
                Toast.makeText(root.getContext(), "Trefel has been discontinued, hence we cannot preform this action", Toast.LENGTH_LONG).show();
            }
        });

        previousBTN = root.findViewById(R.id.previousPageButton);
        previousBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(currentPage > 1) {
//                    currentPage--;
//                    final_request = url + (searchQuery.isEmpty() ? token : search + searchQuery) + pagination + currentPage + sort + (isAscendingOrder ? "asc" : "desc");
                    updateRecycler();
//                    updatePage();
//                }
                Toast.makeText(root.getContext(), "Trefel has been discontinued, hence we cannot preform this action", Toast.LENGTH_LONG);
            }
        });

        pageNum = root.findViewById(R.id.pageNumberTextView);

//        isAscendingOrder = !order.isChecked();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!HTTPHandler.hasInternetConnection(getActivity(), root.getContext()) || preferences.getString("USERNAME", null) == null)
            return;
        updateRecycler();
    }

    @Override
    public void onPlantClick(long plant_id) {
        NavController navController = NavHostFragment.findNavController(this);
        Bundle bundle = new Bundle();
        bundle.putLong("plant_id", plant_id);
        navController.navigate(R.id.nav_plant_bio, bundle);
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        final_request = "";
    }

    private void updatePage() {
//        max_pages = (totalResults / 20) + 1;
//        pageNum.setText("Page " + currentPage + " out of " + max_pages);
    }

    private void clearRecycler() {
        plant_list.clear();
        adapter.notifyDataSetChanged();
    }

    private void updateRecycler() {
//        try {
//            if (final_request.isEmpty())
//                final_request = url + token + pagination + currentPage + sort + (isAscendingOrder ? "asc" : "desc");
            clearRecycler();
//            new GetPlants().execute().get();
//            System.out.println("updateRecycler:: " + final_request);
//            updatePage();
//            progressDialog.dismiss();
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
    }



//    private class GetPlants extends AsyncTask<Void, Void, Void> {
//        final HTTPHandler handler = new HTTPHandler();
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(root.getContext());
//            progressDialog.setMessage("Loading Data...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            String jsonStr = handler.makeServiceCall(final_request);
//            if (jsonStr != null) {
//                try {
//                    JSONObject jsonObject = new JSONObject(jsonStr);
//                    JSONArray plantObjects = jsonObject.getJSONArray("data");
//                    totalResults = jsonObject.getJSONObject("meta").getInt("total");
//                    for (int i = 0; i < plantObjects.length(); ++i) {
//                        JSONObject plantObject = plantObjects.getJSONObject(i);
//                        plant_list.add( new Plant(
//                                plantObject.getInt("id"),
//                                plantObject.getString("common_name"),
//                                plantObject.getString("scientific_name"),
//                                plantObject.getString("family_common_name"),
//                                plantObject.getString("image_url")));
//                    }
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
//            adapter = new PlantRecyclerAdapter(plant_list, root.getContext(), onPlantListener);
//            recyclerView.setAdapter(adapter);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
//            progressDialog.dismiss();
//        }
//    }
}