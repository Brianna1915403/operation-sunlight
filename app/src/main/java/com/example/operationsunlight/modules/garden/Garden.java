package com.example.operationsunlight.modules.garden;

import com.example.operationsunlight.modules.plant.Plant;

import java.util.List;

public class Garden {
    List<Plant> plants;

    public Garden() {    }

    public Garden(List<Plant> plants) {
        this.plants = plants;
    }

    @Override
    public String toString() {
        return "Garden{" +
                "plants=" + plants +
                '}';
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }
}
