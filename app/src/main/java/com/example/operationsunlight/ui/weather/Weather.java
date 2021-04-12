package com.example.operationsunlight.ui.weather;

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

    private String iconURL;

    public Weather(int datetime, int sunrise, int sunset, double temp, double feels, int humidity, double uvi, int clouds, double wind_speed, int wind_deg, String main, String desc, String icon, double rain) {
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
    }

    public int getDatetime() {
        return datetime;
    }

    public void setDatetime(int datetime) {
        this.datetime = datetime;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeels() {
        return feels;
    }

    public void setFeels(double feels) {
        this.feels = feels;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getUvi() {
        return uvi;
    }

    public void setUvi(double uvi) {
        this.uvi = uvi;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public int getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(int wind_deg) {
        this.wind_deg = wind_deg;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public String getIconURL() {
        iconURL = "http://openweathermap.org/img/wn/" + this.icon + "@2x.png";
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }
}
