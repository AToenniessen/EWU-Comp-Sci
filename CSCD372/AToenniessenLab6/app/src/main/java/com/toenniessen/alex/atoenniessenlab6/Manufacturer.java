package com.toenniessen.alex.atoenniessenlab6;

import java.util.ArrayList;

public class Manufacturer extends MainActivity {
    private String manufacturer;
    private ArrayList<CarModel> models;

    public Manufacturer() {
        manufacturer = getResources().getString(R.string.default_manufacturer);
        models = new ArrayList<>();
        models.add(new CarModel("New Model", "No Years", "No Engine", -1));
    }

    public Manufacturer(String make, ArrayList<CarModel> model) {
        manufacturer = make;
        models = model;
    }

    String getManufacturer() {
        return manufacturer;
    }

    CarModel getModel(CarModel model) {
        if (model != null) {
            if (models.contains(model))
                return models.get(models.indexOf(model));
        }
        return null; //getResources().getString(R.string.model_not_found);
    }

    CarModel getModel(int pos) {
        if (pos >= 0 && pos < models.size()) {
            return models.get(pos);
        }
        return null;//getResources().getString(R.string.model_not_found);
    }

    boolean removeModel(CarModel model){
        if(model != null){
            return models.remove(model);
        }
        return false;
    }
    int modelCount(){
        return models.size();
    }
    boolean addModel(CarModel model){
        if(model != null){
            return models.add(model);
        }
        return false;
    }

}
