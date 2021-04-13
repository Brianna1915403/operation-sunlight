package com.example.operationsunlight.modules.weather;

import com.example.operationsunlight.R;

public class WeatherIcon {

    public static int getWeatherIcon(String iconString) {
        int weather_icon;
        switch (iconString) {
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
        return weather_icon;
    }
}
