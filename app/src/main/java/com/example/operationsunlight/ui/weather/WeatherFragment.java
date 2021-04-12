package com.example.operationsunlight.ui.weather;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.operationsunlight.HTTPHandler;
import com.example.operationsunlight.R;
import com.example.operationsunlight.ui.plant.PlantRecyclerAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class WeatherFragment extends Fragment {
    private boolean isConnected;
    private View root;
    private RecyclerView dailyRecycler, hourlyRecycler;

    private ProgressDialog progressDialog;

    // Data to get from api:
    // Current: dt, sunrise, sunset, temp, feels like, humidity, uvi, clouds, wind speed, wind deg, main, desc, icon, pop
    // Hourly: dt, icon, pop, temp
    // Daily: dt, icon, pop, temp

    private static String url = "https://api.openweathermap.org/data/2.5/onecall?units=metric&exclude=minutely";
    private final static String apiKey = "&appid=b5aef5f8fe1f3f70de5080d2b9b58e9f";
    // Currently set to MTL lat/long until gps is working.
    private String lat = "&lat=45.5017";
    private String lon = "&lon=-73.5673";

    private String final_request = "";

    ImageView image;

    TextView dateTime, temperature, feels, main, description, humidity,
    uvi, clouds, sunrise, sunset, windSpeed, windDegree, rain;

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    public Weather currentWeather;
    public FutureWeather futureWeather;

    public ArrayList<FutureWeather> hourly = new ArrayList<>();
    public ArrayList<FutureWeather> daily = new ArrayList<>();

    private WeatherRecyclerAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_weather, container, false);
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(root.getContext().CONNECTIVITY_SERVICE);
        isConnected = (manager.getNetworkInfo(manager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                manager.getNetworkInfo(manager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
        if (!isConnected)
            return root;

        dailyRecycler = root.findViewById(R.id.dailyRecycler);
        hourlyRecycler = root.findViewById(R.id.hourlyRecycler);

//        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, root.getContext());
        // Set to current lat/lon through gps later.

        final_request = url + apiKey + lat + lon;

        try {
            new GetWeather().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        if (!isConnected)
            return;
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("E yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        int weather_icon;
        switch (currentWeather.getIcon()) {
            case "01d": weather_icon = R.drawable.w01d_2x; break;
            case "01n": weather_icon = R.drawable.w01n_2x; break;
            case "02d": weather_icon = R.drawable.w02d_2x; break;
            case "02n": weather_icon = R.drawable.w02n_2x; break;
            case "03d": weather_icon = R.drawable.w03d_2x; break;
            case "03n": weather_icon = R.drawable.w03n_2x; break;
            case "04n": weather_icon = R.drawable.w04n_2x; break;
            case "04d": weather_icon = R.drawable.w04d_2x; break;
            case "09d": weather_icon = R.drawable.w09d_2x; break;
            case "09n": weather_icon = R.drawable.w09n_2x; break;
            case "10d": weather_icon = R.drawable.w10d_2x; break;
            case "10n": weather_icon = R.drawable.w10n_2x; break;
            case "11d": weather_icon = R.drawable.w11d_2x; break;
            case "11n": weather_icon = R.drawable.w11n_2x; break;
            case "13d": weather_icon = R.drawable.w13d_2x; break;
            case "13n": weather_icon = R.drawable.w13n_2x; break;
            case "50d": weather_icon = R.drawable.w50d_2x; break;
            case "50n": weather_icon = R.drawable.w50n_2x; break;
            default: weather_icon = R.drawable.ic_error_outline; break;
        }

        Glide.with(root.getContext())
                .asBitmap()
                .load(weather_icon)
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.error_file)
                .into(image);

        dateTime.setText("" + currentDateFormat.format(new Date((long)currentWeather.getDatetime()*1000)));
        temperature.setText("Current Temperature: " + currentWeather.getTemp() + "\u2103");
        feels.setText("Feels Like: " + currentWeather.getFeels() + "\u2103");
        main.setText("Condition: " + currentWeather.getMain());
        description.setText("Specification: " + currentWeather.getDesc());
        humidity.setText("Humidity: " + currentWeather.getHumidity() + "%");
        uvi.setText("uvIndex: " + currentWeather.getUvi());
        clouds.setText("Clouds: " + currentWeather.getClouds() + "%");
        sunrise.setText("Sunrise: " + timeFormat.format(new Date((long)currentWeather.getSunrise()*1000)));
        sunset.setText("Sunset: " + timeFormat.format(new Date((long)currentWeather.getSunset()*1000)));
        windSpeed.setText("Wind Speed: " + currentWeather.getWind_speed() + "m/s");
        windDegree.setText("Wind Degree: " + currentWeather.getWind_deg() + "\u00B0");
        rain.setText("Rain (Past Hour): " + currentWeather.getRain() + "mm");
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
                    SimpleDateFormat dayFormat = new SimpleDateFormat("E");
                    SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
                    JSONArray hourlyWeather = jsonObject.getJSONArray("hourly");
                    for(int i = 0; i < hourlyWeather.length(); i++) {
                        JSONObject hour = hourlyWeather.getJSONObject(i);
                        futureWeather = new FutureWeather(
                                    hourFormat.format(new Date((long)hour.getInt("dt")*1000)),
                                    hour.getDouble("temp") + "\u2103",
                                "pop: " + hour.getInt("pop") + "%",
                                 hour.getJSONArray("weather").getJSONObject(0).getString("icon")
                        );
                        hourly.add(futureWeather);
                    }

                    JSONArray dailyWeather = jsonObject.getJSONArray("daily");
                    for(int i = 0; i < dailyWeather.length(); i++) {
                        JSONObject day = dailyWeather.getJSONObject(i);

                        futureWeather = new FutureWeather(
                                    dayFormat.format(new Date((long)day.getInt("dt")*1000)),
                                    day.getJSONObject("temp").getDouble("day") + "\u2103",
                                    "pop: " + day.getInt("pop") + "%",
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