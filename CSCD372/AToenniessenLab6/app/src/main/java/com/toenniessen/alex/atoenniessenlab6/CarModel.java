package com.toenniessen.alex.atoenniessenlab6;

import java.io.Serializable;

public class CarModel implements Serializable {
    private String name, years_produced, engine_sizes;
    private int drawable_id;
    CarModel(String n, String yp, String es, int di){
        name = n;
        years_produced = yp;
        engine_sizes = es;
        drawable_id = di;
    }

    public String getName() {
        return name;
    }
}
