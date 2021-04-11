package com.example.operationsunlight;

public class PlantInfo {
    int plant_id;
    String common_name;
    String scientific_name;
    String family_common_name;
    String image_url;
    boolean isVegetable;
    int daysToHarvest;
    String rowSpacing;
    String spread;
    int min_ph;
    int max_ph;
    int light;
    int min_precipitation;
    int max_precipitation;
    int min_root_depth;
    int min_temperature;
    int max_temperature;

    public PlantInfo(int plant_id, String common_name, String scientific_name,
                     String family_common_name, String image_url,
                     boolean isVegetable, int daysToHarvest,
                     String rowSpacing, String spread, int min_ph, int max_ph,
                     int light, int min_precipitation, int max_precipitation,
                     int min_root_depth, int min_temperature, int max_temperature) {

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

    public int getDaysToHarvest() {
        return daysToHarvest;
    }

    public String getRowSpacing() {
        return rowSpacing;
    }

    public String getSpread() {
        return spread;
    }

    public int getMin_ph() {
        return min_ph;
    }

    public int getMax_ph() {
        return max_ph;
    }

    public int getLight() {
        return light;
    }

    public int getMin_precipitation() {
        return min_precipitation;
    }

    public int getMax_precipitation() {
        return max_precipitation;
    }

    public int getMin_root_depth() {
        return min_root_depth;
    }

    public int getMin_temperature() {
        return min_temperature;
    }

    public int getMax_temperature() {
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
