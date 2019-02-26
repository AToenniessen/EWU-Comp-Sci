package com.toenniessen.alex.atoenniessenlab6;

import java.io.Serializable;

class CarModel implements Serializable {
    private String name, years_produced, engine_sizes;
    private int drawable_id;
    CarModel(String n, String yp, String es, int di){
        name = n;
        years_produced = yp;
        engine_sizes = es;
        drawable_id = di;
    }

    String getName() {
        return name;
    }

    public String getEngine_sizes() {
        return engine_sizes;
    }

    public int getDrawable_id() {
        return drawable_id;
    }

    public String getYears_produced() {
        return years_produced;
    }
}
