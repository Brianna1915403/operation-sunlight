package com.example.operationsunlight.modules.weather;

public class FutureWeather {

    private String datetime;
    private String temp;
    private String pop;
    private String icon;

    public FutureWeather(String datetime, String temp, String pop, String icon) {
        this.datetime = datetime;
        this.temp = temp;
        this.pop = pop;
        this.icon = icon;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getTemp() {
        return temp;
    }

    public String getPop() {
        return pop;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "FutureWeather{" +
                "datetime=" + datetime +
                ", temp=" + temp +
                ", pop=" + pop +
                ", icon='" + icon + '\'' +
                '}';
    }
}
