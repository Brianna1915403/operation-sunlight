package com.example.operationsunlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.Date;

public class WeatherForecast extends AppCompatActivity {

    private ProgressDialog progressDialog;

    // Data to get from api:
    // Current: dt, sunrise, sunset, temp, feels like, humidity, uvi, clouds, wind speed, wind deg, main, desc, icon, pop
    // Hourly: dt, icon, pop, temp
    // Daily: dt, icon, pop, temp

    private static String url = "https://api.openweathermap.org/data/2.5/onecall?units=metric&exclude=minutely";
    private final static String apiKey = "&appid=b5aef5f8fe1f3f70de5080d2b9b58e9f";
    private String lat = "&lat=";
    private String lon = "&lon=";

    private String final_request = "";

    private int datetime;
    private int sunrise;
    private int sunset;
    private double temp;
    private double feels;
    private int humidity;
    private double uvi;
    private int clouds;
    private double wind_speed;
    private int wind_deg;
    private String main;
    private String desc;
    private String icon;
    private double rain = 0;

    TextView textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11, textView12, textView13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        // Set to current lat/lon through gps later.
        lat += "45.5017";
        lon += "-73.5673";

        final_request = url + apiKey + lat + lon;

        try {
            new WeatherForecast.GetWeather().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);
        textView11 = findViewById(R.id.textView11);
        textView12 = findViewById(R.id.textView12);
        textView13 = findViewById(R.id.textView13);

        long timestamp = datetime;
        Date date = new Date((long)timestamp*1000);

        textView.setText(date.toString());
    }

    private class GetWeather extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(WeatherForecast.this);
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
                    JSONObject current = jsonObject.getJSONObject("current");
                    JSONArray current_weather = current.getJSONArray("weather");
                    datetime = current.getInt("dt");
                    sunrise = current.getInt("sunrise");
                    sunset = current.getInt("sunset");
                    temp = current.getDouble("temp");
                    feels = current.getDouble("feels_like");
                    humidity = current.getInt("humidity");
                    uvi = current.getDouble("uvi");
                    clouds = current.getInt("clouds");
                    wind_speed = current.getDouble("wind_speed");
                    wind_deg = current.getInt("wind_deg");
                    main = current_weather.getJSONObject(0).getString("main");
                    desc = current_weather.getJSONObject(0).getString("description");
                    icon = current_weather.getJSONObject(0).getString("icon");

                    if(current.has("rain")) {
                        JSONObject current_rain = current.getJSONObject("rain");
                        rain = current_rain.getDouble("1h");
                    } else {

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
            if (progressDialog.isShowing())
                progressDialog.dismiss();

//            adapter = new RecyclerViewAdapter(plant_list, CatalogueActivity.this);
//            recyclerView.setAdapter(adapter);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setLayoutManager(new LinearLayoutManager(CatalogueActivity.this));


        }
    }
}

