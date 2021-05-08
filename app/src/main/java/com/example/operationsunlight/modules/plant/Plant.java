package com.example.operationsunlight.modules.plant;

public class Plant {
//    int plant_id;
    long plant_id;
    String common_name;
    String scientific_name;
    String family_common_name;
    String image_url;

    public Plant() {    }

    public Plant(long plant_id, String common_name, String scientific_name, String family_common_name, String image_url){
        this.plant_id = plant_id;
        this.common_name = common_name.equals("null")? "Unknown" : common_name;
        this.scientific_name = scientific_name.equals("null")? "Unknown" : scientific_name;
        this.family_common_name = family_common_name.equals("null")? "Unknown" : family_common_name;
        this.image_url = image_url;
    }

    public Plant(Plant other) {
        this.plant_id = other.plant_id;
        this.common_name = other.common_name.equals("null")? "Unknown" : other.common_name;
        this.scientific_name = other.scientific_name.equals("null")? "Unknown" : other.scientific_name;
        this.family_common_name = other.family_common_name.equals("null")? "Unknown" : other.family_common_name;
        this.image_url = other.image_url;
    }

    public String getCommon_name() {
        return common_name;
    }

    public String getScientific_name() {
        return scientific_name;
    }

    public String getFamily_common_name() { return family_common_name; }

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
