package com.toenniessen.alex.atoenniessenlab3;

import java.util.ArrayList;

public class Manufacturer extends MainActivity {
    private String manufacturer;
    private ArrayList<String> models;

    Manufacturer() {
        manufacturer = getResources().getString(R.string.default_manufacturer);
        models = new ArrayList<>();
        models.add(getResources().getString(R.string.default_model));
    }

    Manufacturer(String make, ArrayList<String> model) {
        manufacturer = make;
        models = model;
    }

    String getManufacturer() {
        return manufacturer;
    }

    String getModel(String model) {
        if (model != null) {
            if (models.contains(model))
                return models.get(models.indexOf(model));
        }
        return getResources().getString(R.string.model_not_found);
    }

    String getModel(int pos) {
        if (pos > 0 && pos < models.size()) {
            return models.get(pos);
        }
        return getResources().getString(R.string.model_not_found);
    }

    boolean removeModel(String model){
        if(model != null){
            return models.remove(model);
        }
        return false;
    }
    int modelCount(){
        return models.size();
    }
    boolean addModel(String model){
        if(model != null){
            return models.add(model);
        }
        return false;
    }

}
