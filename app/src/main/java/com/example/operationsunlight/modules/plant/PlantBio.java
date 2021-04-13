package com.example.operationsunlight.modules.plant;

public class PlantBio extends Plant{
    private String isVegetable;
    private String daysToHarvest;
    private String rowSpacing;
    private String spread;
    private String min_ph;
    private String max_ph;
    private String light;
    private String min_precipitation;
    private String max_precipitation;
    private String min_root_depth;
    private String min_temperature;
    private String max_temperature;

    public PlantBio(Plant plant, String isVegetable, String daysToHarvest, String rowSpacing, String spread, String min_ph, String max_ph, String light, String min_precipitation, String max_precipitation, String min_root_depth, String min_temperature, String max_temperature) {
        super(plant);
        this.isVegetable = isVegetable.equals("null")? "Unknown" : isVegetable.equalsIgnoreCase("true")? "Yes" : "No";
        this.daysToHarvest = daysToHarvest.equals("null")? "Unknown" : daysToHarvest;
        this.rowSpacing = rowSpacing.equals("null")? "Unknown" : rowSpacing;
        this.spread = spread.equals("null")? "Unknown" : spread;
        this.min_ph = min_ph.equals("null")? "Unknown" : min_ph;
        this.max_ph = max_ph.equals("null")? "Unknown" : max_ph;
        this.light = light.equals("null")? "Unknown" : light;
        this.min_precipitation = min_precipitation.equals("null")? "Unknown" : min_precipitation;
        this.max_precipitation = max_precipitation.equals("null")? "Unknown" : max_precipitation;
        this.min_root_depth = min_root_depth.equals("null")? "Unknown" : min_root_depth;
        this.min_temperature = min_temperature.equals("null")? "Unknown" : min_temperature;
        this.max_temperature = max_temperature.equals("null")? "Unknown" : max_temperature;
    }

    @Override
    public String toString() {
        return "PlantBio{" +
                ", isVegetable=" + isVegetable +
                ", daysToHarvest=" + daysToHarvest +
                ", rowSpacing='" + rowSpacing + '\'' +
                ", spread='" + spread + '\'' +
                ", min_ph=" + min_ph +
                ", max_ph=" + max_ph +
                ", light=" + light +
                ", min_precipitation=" + min_precipitation +
                ", max_precipitation=" + max_precipitation +
                ", min_root_depth=" + min_root_depth +
                ", min_temperature=" + min_temperature +
                ", max_temperature=" + max_temperature +
                '}';
    }

    public String isVegetable() {
        return isVegetable;
    }

    public String getDaysToHarvest() {
        return daysToHarvest;
    }

    public String getRowSpacing() {
        return rowSpacing;
    }

    public String getSpread() {
        return spread;
    }

    public String getMin_ph() {
        return min_ph;
    }

    public String getMax_ph() {
        return max_ph;
    }

    public String getLight() {
        return light;
    }

    public String getMin_precipitation() {
        return min_precipitation;
    }

    public String getMax_precipitation() {
        return max_precipitation;
    }

    public String getMin_root_depth() {
        return min_root_depth;
    }

    public String getMin_temperature() {
        return min_temperature;
    }

    public String getMax_temperature() {
        return max_temperature;
    }
}
