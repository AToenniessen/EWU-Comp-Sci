package com.toenniessen.alex.atoenniessenlab6;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailedList extends Fragment {


    public DetailedList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detailed_list, container, false).getRootView();
        CarModel model = ((MainActivity) getActivity()).getmCurModel();
        if(model != null){
            TextView tModel = rootView.findViewById(R.id.model);
            tModel.setText(model.getName());
            TextView tProduction = rootView.findViewById(R.id.production);
            tProduction.setText(model.getYears_produced());
            TextView tDisplacement = rootView.findViewById(R.id.displacement);
            tDisplacement.setText(model.getEngine_sizes());
            ImageView tVehicle = rootView.findViewById(R.id.vehicle);
            tVehicle.setImageResource(model.getDrawable_id());
        }
        return rootView;
    }

}
