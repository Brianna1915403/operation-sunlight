package com.example.operationsunlight;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.Date;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class WeatherForecast extends AppCompatActivity implements LocationListener {

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

    TextView textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11, textView12, textView13;

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    public Weather currentWeather;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        currentWeather = new Weather();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        // Set to current lat/lon through gps later.

        final_request = url + apiKey + lat + lon;

        try {
            new WeatherForecast.GetCurrentWeather().execute().get();
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

        long timestamp = currentWeather.datetime;
        Date date = new Date((long)timestamp*1000);

        textView.setText(date.toString());
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lat = "&lat=";
        lat += location.getLatitude();

        lon = "&lon=";
        lon += location.getLongitude();
    }


    private class GetCurrentWeather extends AsyncTask<Void, Void, Void> {

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
                    currentWeather.datetime = current.getInt("dt");
                    currentWeather.sunrise = current.getInt("sunrise");
                    currentWeather.sunset = current.getInt("sunset");
                    currentWeather.temp = current.getDouble("temp");
                    currentWeather.feels = current.getDouble("feels_like");
                    currentWeather.humidity = current.getInt("humidity");
                    currentWeather.uvi = current.getDouble("uvi");
                    currentWeather.clouds = current.getInt("clouds");
                    currentWeather.wind_speed = current.getDouble("wind_speed");
                    currentWeather.wind_deg = current.getInt("wind_deg");
                    currentWeather.main = current_weather.getJSONObject(0).getString("main");
                    currentWeather.desc = current_weather.getJSONObject(0).getString("description");
                    currentWeather.icon = current_weather.getJSONObject(0).getString("icon");

                    if(current.has("rain")) {
                        JSONObject current_rain = current.getJSONObject("rain");
                        currentWeather.rain = current_rain.getDouble("1h");
                    } else {
                        currentWeather.rain = 0;
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

