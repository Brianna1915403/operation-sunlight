package com.example.operationsunlight.modules.weather;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.operationsunlight.HTTPHandler;
import com.example.operationsunlight.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class WeatherFragment extends Fragment {
    private View root;
    SharedPreferences preferences;
    private RecyclerView dailyRecycler, hourlyRecycler;

    private ProgressDialog progressDialog;

    // Data to get from api:
    // Current: dt, sunrise, sunset, temp, feels like, humidity, uvi, clouds, wind speed, wind deg, main, desc, icon, pop
    // Hourly: dt, icon, pop, temp
    // Daily: dt, icon, pop, temp

    private static String url = "https://api.openweathermap.org/data/2.5/onecall?units=metric&exclude=minutely";
    private final static String apiKey = "&appid=b5aef5f8fe1f3f70de5080d2b9b58e9f";
    // Currently set to MTL lat/long by default.
    private String lat = "";
    private String lon = "";

    private String final_request = "";

    ImageView image;

    TextView dateTime, temperature, feels, main, description, humidity,
            uvi, clouds, sunrise, sunset, windSpeed, windDegree, rain;

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    private static final String TAG = null;
    private int REQUEST_CODE_LOCATION_PERMISSION = 1;
    FusedLocationProviderClient fusedLocationProviderClient;

    public Weather currentWeather;
    public FutureWeather futureWeather;

    public ArrayList<FutureWeather> hourly = new ArrayList<>();
    public ArrayList<FutureWeather> daily = new ArrayList<>();

    private WeatherRecyclerAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        root = inflater.inflate(R.layout.fragment_no_connection, container, false);
        preferences = getActivity().getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        if (!HTTPHandler.hasInternetConnection(getActivity(), root.getContext()))
            return root;
        else if (preferences.getString("USERNAME", null) == null) {
            root = inflater.inflate(R.layout.fragment_need_sign_in, container, false);
            return root;
        } else
            root = inflater.inflate(R.layout.fragment_weather, container, false);

        dailyRecycler = root.findViewById(R.id.dailyRecycler);
        hourlyRecycler = root.findViewById(R.id.hourlyRecycler);

//        final_request = url + apiKey + lat + lon;

//        try {
//            new GetWeather().execute().get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        image = root.findViewById(R.id.image);

        dateTime = root.findViewById(R.id.dateTimeTextView);
        temperature = root.findViewById(R.id.temperatureTextView);
        feels = root.findViewById(R.id.feelsLikeTextView);
        main = root.findViewById(R.id.mainTextView);
        description = root.findViewById(R.id.descriptionTextView);
        humidity = root.findViewById(R.id.humidityTextView);
        uvi = root.findViewById(R.id.uviTextView);
        clouds = root.findViewById(R.id.cloudsTextView);
        sunrise = root.findViewById(R.id.sunriseTextView);
        sunset = root.findViewById(R.id.sunsetTextView);
        windSpeed = root.findViewById(R.id.windSpeedTextView);
        windDegree = root.findViewById(R.id.windDegreeTextView);
        rain = root.findViewById(R.id.rainTextView);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!HTTPHandler.hasInternetConnection(getActivity(), root.getContext()) || preferences.getString("USERNAME", null) == null)
            return;
//        SimpleDateFormat currentDateFormat = new SimpleDateFormat("E MMM d HH:mm a");
//        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
//
//        Glide.with(root.getContext())
//                .asBitmap()
//                .load(WeatherIcon.getWeatherIcon(currentWeather.getIcon()))
//                .placeholder(R.drawable.loading_icon)
//                .error(R.drawable.error_file)
//                .into(image);
//
//        dateTime.setText("" + currentDateFormat.format(new Date((long) currentWeather.getDatetime() * 1000)));
//        temperature.setText((int) Math.round(currentWeather.getTemp()) + "\u2103");
//        feels.setText("Feels Like: " + (int) Math.round(currentWeather.getFeels()) + "\u2103");
//        main.setText(currentWeather.getMain());
//        description.setText("Specification: " + currentWeather.getDesc());
//        humidity.setText("Humidity: " + currentWeather.getHumidity() + "%");
//        uvi.setText("uvIndex: " + currentWeather.getUvi());
//        clouds.setText("Clouds: " + currentWeather.getClouds() + "%");
//        sunrise.setText("Sunrise: " + timeFormat.format(new Date((long) currentWeather.getSunrise() * 1000)));
//        sunset.setText("Sunset: " + timeFormat.format(new Date((long) currentWeather.getSunset() * 1000)));
//        windSpeed.setText("Wind Speed: " + currentWeather.getWind_speed() + "m/s");
//        windDegree.setText("Wind Degree: " + currentWeather.getWind_deg() + "\u00B0");
//        rain.setText("Rain (Past Hour): " + currentWeather.getRain() + "mm");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(root.getContext().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        } else {
            askLocationPermission();
        }
    }

    private void getLastLocation() {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (preferences.getString("USERNAME", null) != null) {
            LocationServices.getFusedLocationProviderClient(root.getContext())
                    .requestLocationUpdates(locationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(@NonNull LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                    LocationServices.getFusedLocationProviderClient(root.getContext())
                            .removeLocationUpdates(this);
                    if (locationResult != null && locationResult.getLocations().size() > 0) {
                        int latestLocationIndex = locationResult.getLocations().size() - 1;
                        double latitude =
                                locationResult.getLocations().get(latestLocationIndex).getLatitude();
                        double longitude =
                                locationResult.getLocations().get(latestLocationIndex).getLongitude();

                        lat = "&lat=" + latitude;
                        lon = "&lon=" + longitude;
                        System.out.println("Current lat/lon: " + lat + " " + lon);

                        final_request = url + apiKey + lat + lon;
                        System.out.println(final_request);
                        try {
                            new GetWeather().execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        SimpleDateFormat currentDateFormat = new SimpleDateFormat("E MMM d HH:mm a");
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

                        Glide.with(root.getContext())
                                .asBitmap()
                                .load(WeatherIcon.getWeatherIcon(currentWeather.getIcon()))
                                .placeholder(R.drawable.loading_icon)
                                .error(R.drawable.error_file)
                                .into(image);

                        dateTime.setText("" + currentDateFormat.format(new Date((long) currentWeather.getDatetime() * 1000)));
                        temperature.setText((int) Math.round(currentWeather.getTemp()) + "\u2103");
                        feels.setText("Feels Like: " + (int) Math.round(currentWeather.getFeels()) + "\u2103");
                        main.setText(currentWeather.getMain());
                        description.setText("Specification: " + currentWeather.getDesc());
                        humidity.setText("Humidity: " + currentWeather.getHumidity() + "%");
                        uvi.setText("uvIndex: " + currentWeather.getUvi());
                        clouds.setText("Clouds: " + currentWeather.getClouds() + "%");
                        sunrise.setText("Sunrise: " + timeFormat.format(new Date((long) currentWeather.getSunrise() * 1000)));
                        sunset.setText("Sunset: " + timeFormat.format(new Date((long) currentWeather.getSunset() * 1000)));
                        windSpeed.setText("Wind Speed: " + currentWeather.getWind_speed() + "m/s");
                        windDegree.setText("Wind Degree: " + currentWeather.getWind_deg() + "\u00B0");
                        rain.setText("Rain (Past Hour): " + currentWeather.getRain() + "mm");
                    }
                }
            }, Looper.getMainLooper());
        }

    }

    private void askLocationPermission(){
        if(ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)){
                Log.d(TAG, "askLocationPermission: you should show an alert dialog...");
                ActivityCompat.requestPermissions(this.getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
            } else {
                ActivityCompat.requestPermissions(this.getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // Permission granted
                getLastLocation();
            } else {
                // Permission not granted
            }
        }
    }

    private class GetWeather extends AsyncTask<Void, Void, Void> {

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
                    Double currentRain;
                    Double currentSnow;
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONObject current = jsonObject.getJSONObject("current");
                    JSONArray current_weather = current.getJSONArray("weather");

                    if(current.has("rain")) {
                        JSONObject current_rain = current.getJSONObject("rain");
                        currentRain = current_rain.getDouble("1h");
                    } else {
                        currentRain = 0.0;
                    }

                    if(current.has("snow")) {
                        JSONObject current_snow = current.getJSONObject("snow");
                        currentSnow = current_snow.getDouble("1h");
                    } else {
                        currentSnow = 0.0;
                    }

                    currentWeather = new Weather(
                                current.getInt("dt"),
                                current.getInt("sunrise"),
                                current.getInt("sunset"),
                                current.getDouble("temp"),
                                current.getDouble("feels_like"),
                                current.getInt("humidity"),
                                current.getDouble("uvi"),
                                current.getInt("clouds"),
                                current.getDouble("wind_speed"),
                                current.getInt("wind_deg"),
                                current_weather.getJSONObject(0).getString("main"),
                                current_weather.getJSONObject(0).getString("description"),
                                current_weather.getJSONObject(0).getString("icon"),
                                currentRain,
                                currentSnow
                    );
                    SimpleDateFormat dayFormat = new SimpleDateFormat("E MMM d");
                    SimpleDateFormat hourFormat = new SimpleDateFormat("E HH:mm");
                    JSONArray hourlyWeather = jsonObject.getJSONArray("hourly");
                    for(int i = 0; i < hourlyWeather.length(); i++) {
                        JSONObject hour = hourlyWeather.getJSONObject(i);
                        futureWeather = new FutureWeather(
                                    hourFormat.format(new Date((long)hour.getInt("dt")*1000)),
                                (int) Math.round(hour.getDouble("temp")) + "\u2103",
                                "pop: " + (int) (hour.getDouble("pop") * 100) + "%",
                                 hour.getJSONArray("weather").getJSONObject(0).getString("icon")
                        );
                        hourly.add(futureWeather);
                    }

                    JSONArray dailyWeather = jsonObject.getJSONArray("daily");
                    for(int i = 0; i < dailyWeather.length(); i++) {
                        JSONObject day = dailyWeather.getJSONObject(i);

                        futureWeather = new FutureWeather(
                                    dayFormat.format(new Date((long)day.getInt("dt")*1000)),
                                (int) Math.round(day.getJSONObject("temp").getDouble("day")) + "\u2103",
                                    "pop: " + (int) (day.getDouble("pop") * 100) + "%",
                                day.getJSONArray("weather").getJSONObject(0).getString("icon")
                        );
                        daily.add(futureWeather);
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

            adapter = new WeatherRecyclerAdapter(daily, root.getContext());
            dailyRecycler.setAdapter(adapter);
            dailyRecycler.setHasFixedSize(true);
            dailyRecycler.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));

            adapter = new WeatherRecyclerAdapter(hourly, root.getContext());
            hourlyRecycler.setAdapter(adapter);
            hourlyRecycler.setHasFixedSize(true);
            hourlyRecycler.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));


        }
    }
}