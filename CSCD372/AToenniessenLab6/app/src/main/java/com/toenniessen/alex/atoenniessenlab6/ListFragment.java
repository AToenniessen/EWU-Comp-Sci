package com.toenniessen.alex.atoenniessenlab6;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements ExpandableListView.OnChildClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String Arg_List = "Manufacturer";

    // TODO: Rename and change types of parameters
    private ArrayList<Manufacturer> mManufacturerList;


    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(ArrayList<Manufacturer> param1) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putSerializable(Arg_List, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mManufacturerList = (ArrayList<Manufacturer>) getArguments().getSerializable(Arg_List);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list, container, false).getRootView();

        ExpandableListView list = rootView.findViewById(R.id.fragment_list);
        MyListAdapter adapter = new MyListAdapter(this.getActivity(), mManufacturerList);
        list.setAdapter(adapter);
        list.setOnChildClickListener(this);
        return rootView;
    }
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Manufacturer temp = mManufacturerList.get(groupPosition);
        if(temp != null){
            MainActivity tempActivity = (MainActivity)getActivity();
            if (tempActivity != null) {
                tempActivity.changePage(temp.getModel(childPosition));
            }
            else {
                String name = temp.getModel(childPosition) != null ? temp.getModel(childPosition).getName() : getResources().getString(R.string.model_not_found);
                Toast.makeText(getContext(), "Manufacturer: " + temp.getManufacturer() +
                                "\nModel: " + name,
                        Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

}
