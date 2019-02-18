package com.toenniessen.alex.atoenniessenlab6;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener, Serializable {
    private ArrayList<Manufacturer> mManufacturer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandableListView listView = findViewById(R.id.customList);
        listView.setOnChildClickListener(this);
        MyListAdapter adapter = null;

        if(savedInstanceState == null) {
            if (parseFile("input.txt")) {
                adapter = new MyListAdapter(this, mManufacturer);
            } else {
                Toast.makeText(this, getResources().getString(R.string.parse_fail),
                        Toast.LENGTH_SHORT).show();
            }
        }
        else{
            mManufacturer = (ArrayList<Manufacturer>) savedInstanceState.getSerializable("Manufacturer");
            adapter = new MyListAdapter(this, mManufacturer);
        }
        listView.setAdapter(adapter);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// This adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Toast.makeText(this, getResources().getString(R.string.about_data),
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putSerializable("Manufacturer", mManufacturer);
        super.onSaveInstanceState(savedInstanceState);
    }
    private boolean parseFile(String fname){
        AssetManager manager = getResources().getAssets();
        BufferedReader input;
        try {
            input = new BufferedReader(new InputStreamReader(manager.open(fname)));
            while(input.ready()){
                ArrayList<String> lines = new ArrayList<>(Arrays.asList(input.readLine().split(",")));
                String manufacturer = lines.remove(0);
                mManufacturer.add(new Manufacturer(manufacturer, new ArrayList<>(lines)));
            }
        }catch(Exception e){
            return false;
        }
        return true;
    }
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Manufacturer temp = mManufacturer.get(groupPosition);
        if(temp != null){
            Toast.makeText(this, "Manufacturer: " + temp.getManufacturer() +
                            "\nModel: " + temp.getModel(childPosition),
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
