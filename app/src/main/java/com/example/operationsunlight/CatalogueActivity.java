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

//    private ProgressDialog progressDialog;
//    private ListView listView;
//    private static String url = "https://api.androidhive.info/contacts/";
//    ArrayList<HashMap<String, String>> contactList;

   ArrayList<Plant> plant_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plant_list.add(new Plant("Honey mesquite", "Prosopis glandulosa", "Something",
                "https://bs.plantnet.org/image/o/d164296148c0d29f46b2f29fb38cffcc1469b83c"));

        plant_list.add(new Plant("Honey mesquite", "Prosopis glandulosa", "Something Else",
                ""));




        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(plant_list, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        listView = findViewById(R.id.list);
//        contactList = new ArrayList<>();
//        new GetContacts().execute();
    }

//    private class GetContacts extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(MainActivity.this);
//            progressDialog.setMessage("Retrieving Data...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            HTTPHandler handler = new HTTPHandler();
//            String jsonStr = handler.makeServiceCall(url);
//            if (jsonStr != null) {
//                try {
//                    JSONObject jsonObject = new JSONObject(jsonStr);
//                    JSONArray contacts = jsonObject.getJSONArray("contacts");
//                    for (int i = 0; i < contacts.length(); ++i) {
//                        JSONObject object = contacts.getJSONObject(i);
//
//                        String id = object.getString("id");
//                        String name = object.getString("name");
//                        String email = object.getString("email");
//
//                        HashMap<String, String> contactMap = new HashMap<>();
//                        contactMap.put("id", id);
//                        contactMap.put("name", name);
//                        contactMap.put("email", email);
//
//                        contactList.add(contactMap);
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
//            if (progressDialog.isShowing())
//                progressDialog.dismiss();
//            ListAdapter listAdapter = new SimpleAdapter(MainActivity.this, contactList, R.layout.list_item, new String[] {"id", "name", "email"}, new int[] {R.id.id, R.id.name, R.id.email});
//            listView.setAdapter(listAdapter);
//        }
//    }
}