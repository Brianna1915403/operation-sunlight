package com.example.operationsunlight.ui.weather;

public class FutureWeather {

    private int datetime;
    private double temp;
    private int pop;
    private String icon;

    public FutureWeather(int datetime, double temp, int pop, String icon) {
        this.datetime = datetime;
        this.temp = temp;
        this.pop = pop;
        this.icon = icon;
    }

    public int getDatetime() {
        return datetime;
    }

    public double getTemp() {
        return temp;
    }

    public int getPop() {
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
