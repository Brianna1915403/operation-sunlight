package com.example.operationsunlight.ui.plant;

public class Plant {
    int plant_id;
    String common_name;
    String scientific_name;
    String family_common_name;
    String image_url;

    public Plant(int plant_id, String common_name, String scientific_name, String family_common_name, String image_url){
        this.plant_id = plant_id;
        this.common_name = common_name;
        this.scientific_name = scientific_name;
        this.family_common_name = family_common_name;
        this.image_url = image_url;
        if(this.common_name.equals("null")){
            this.common_name = "Unknown";
        }
        if(this.scientific_name.equals("null")){
            this.scientific_name = "Unknown";
        }
        if(this.family_common_name.equals("null")){
            this.family_common_name = "Unknown";
        }
    }

    public String getCommon_name() {
        return common_name;
    }

    public String getScientific_name() {
        return scientific_name;
    }

    public String getImage_url() {
        return image_url;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "plant_id=" + plant_id +
                ", common_name='" + common_name + '\'' +
                ", scientific_name='" + scientific_name + '\'' +
                ", family_common_name='" + family_common_name + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
