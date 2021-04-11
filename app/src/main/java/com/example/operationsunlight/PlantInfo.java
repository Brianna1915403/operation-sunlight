package com.example.operationsunlight;

public class PlantInfo {
    int plant_id;
    String common_name;
    String scientific_name;
    String family_common_name;
    String image_url;
    boolean isVegetable;
    String daysToHarvest;
    String rowSpacing;
    String spread;
    String min_ph;
    String max_ph;
    String light;
    String min_precipitation;
    String max_precipitation;
    String min_root_depth;
    String min_temperature;
    String max_temperature;

    public PlantInfo(int plant_id, String common_name, String scientific_name,
                     String family_common_name, String image_url,
                     boolean isVegetable, String daysToHarvest,
                     String rowSpacing, String spread, String min_ph, String max_ph,
                     String light, String min_precipitation, String max_precipitation,
                     String min_root_depth, String min_temperature, String max_temperature) {

        this.plant_id = plant_id;
        this.common_name = common_name;
        this.scientific_name = scientific_name;
        this.family_common_name = family_common_name;
        this.image_url = image_url;
        this.isVegetable = isVegetable;
        this.daysToHarvest = daysToHarvest;
        this.rowSpacing = rowSpacing;
        this.spread = spread;
        this.min_ph = min_ph;
        this.max_ph = max_ph;
        this.light = light;
        this.min_precipitation = min_precipitation;
        this.max_precipitation = max_precipitation;
        this.min_root_depth = min_root_depth;
        this.min_temperature = min_temperature;
        this.max_temperature = max_temperature;

        if(this.common_name.equals("null"))
            this.common_name = "N/A";
        if(this.scientific_name.equals("null"))
            this.scientific_name = "N/A";
        if(this.family_common_name.equals("null"))
            this.family_common_name = "N/A";
        if(this.daysToHarvest.equals("null"))
            this.daysToHarvest = "N/A";
        if(this.rowSpacing.equals("null"))
            this.rowSpacing = "N/A";
        if(this.spread.equals("null"))
            this.spread = "N/A";
        if(this.min_ph.equals("null"))
            this.min_ph = "N/A";
        if(this.max_ph.equals("null"))
            this.max_ph = "N/A";
        if(this.light.equals("null"))
            this.light = "N/A";
        if(this.min_precipitation.equals("null"))
            this.min_precipitation = "N/A";
        if(this.max_precipitation.equals("null"))
            this.max_precipitation = "N/A";
        if(this.min_root_depth.equals("null"))
            this.min_root_depth = "N/A";
        if(this.min_temperature.equals("null"))
            this.min_temperature = "N/A";
        if(this.max_temperature.equals("null"))
            this.max_temperature = "N/A";


    }

    public int getPlant_id() {
        return plant_id;
    }

    public String getCommon_name() {
        return common_name;
    }

    public String getScientific_name() {
        return scientific_name;
    }

    public String getFamily_common_name() {
        return family_common_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public boolean isVegetable() {
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

    @Override
    public String toString() {
        return "PlantInfo{" +
                "plant_id=" + plant_id +
                ", common_name='" + common_name + '\'' +
                ", scientific_name='" + scientific_name + '\'' +
                ", family_common_name='" + family_common_name + '\'' +
                ", image_url='" + image_url + '\'' +
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
}
