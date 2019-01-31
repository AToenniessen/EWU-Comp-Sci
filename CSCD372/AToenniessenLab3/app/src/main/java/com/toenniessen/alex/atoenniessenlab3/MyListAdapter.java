package com.toenniessen.alex.atoenniessenlab3;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MyListAdapter extends BaseExpandableListAdapter implements View.OnClickListener{
    private Activity activity;
    private ArrayList<Manufacturer> manufacturers;

    MyListAdapter(Activity act, ArrayList<Manufacturer> manufacturerArrayList) {
        activity = act;
        manufacturers = manufacturerArrayList;
    }

    @Override
    public int getGroupCount() {
        return manufacturers.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (checkGroup(groupPosition))
            return manufacturers.get(groupPosition).modelCount();
        return -1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (checkGroup(groupPosition))
            return manufacturers.get(groupPosition);
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (checkGroup(groupPosition)) {
            Manufacturer temp = manufacturers.get(groupPosition);
            if (checkChild(groupPosition, childPosition)) {
                return temp.getModel(childPosition);
            }
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        if (checkGroup(groupPosition))
            return groupPosition;
        return -1;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        if (checkGroup(groupPosition)) {
            if(checkChild(groupPosition, childPosition)){
                return childPosition;
            }
        }
        return -1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layout = activity.getLayoutInflater();
            convertView = layout.inflate(R.layout.manufacturer, null);
        }
        TextView temp = (TextView) convertView.findViewById(R.id.manufacturer);
        Manufacturer manufacturer = (Manufacturer) getGroup(groupPosition);
        temp.setText(manufacturer.getManufacturer());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layout = activity.getLayoutInflater();
            convertView = layout.inflate(R.layout.model, null);
        }

        TextView temp = (TextView) convertView.findViewById(R.id.model);
        temp.setText((String) getChild(groupPosition, childPosition));
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        delete.setTag(R.id.group_num, groupPosition);
        delete.setTag(R.id.posn_num, childPosition);
        delete.setOnClickListener(this);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private boolean checkGroup(int groupPos) {
        return (groupPos > -1 && groupPos < manufacturers.size());
    }

    private boolean checkChild(int groupPos, int childPos) {
        Manufacturer temp = manufacturers.get(groupPos);
        return (childPos > -1 && childPos < temp.modelCount());
    }

    @Override
    public void onClick(View v) {
        final int group = (int) v.getTag(R.id.group_num);
        final int child = (int) v.getTag(R.id.posn_num);

        Snackbar snackbar = Snackbar.make(v, "Are you sure you want to delete?", Snackbar.LENGTH_LONG);
        snackbar.setAction("CONFIRM", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Manufacturer manufacturer = (Manufacturer) getGroup(group);
                        manufacturer.removeModel(manufacturer.getModel(child));
                        notifyDataSetChanged();
                    }
                });

        snackbar.show();


    }
}
