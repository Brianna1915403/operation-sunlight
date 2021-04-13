package com.example.operationsunlight.modules.weather;

public class Weather {

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
    private double rain;
    private double snow;

    public Weather(int datetime, int sunrise, int sunset, double temp, double feels, int humidity, double uvi, int clouds, double wind_speed, int wind_deg, String main, String desc, String icon, double rain, double snow) {
        this.datetime = datetime;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.temp = temp;
        this.feels = feels;
        this.humidity = humidity;
        this.uvi = uvi;
        this.clouds = clouds;
        this.wind_speed = wind_speed;
        this.wind_deg = wind_deg;
        this.main = main;
        this.desc = desc;
        this.icon = icon;
        this.rain = rain;
        this.snow = snow;
    }

    public int getDatetime() {
        return datetime;
    }

    public int getSunrise() {
        return sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public double getTemp() {
        return temp;
    }

    public double getFeels() {
        return feels;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getUvi() {
        return uvi;
    }

    public int getClouds() {
        return clouds;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public int getWind_deg() {
        return wind_deg;
    }

    public String getMain() {
        return main;
    }

    public String getDesc() {
        return desc;
    }

    public String getIcon() {
        return icon;
    }

    public double getRain() {
        return rain;
    }

    public double getSnow() {
        return snow;
    }
}
