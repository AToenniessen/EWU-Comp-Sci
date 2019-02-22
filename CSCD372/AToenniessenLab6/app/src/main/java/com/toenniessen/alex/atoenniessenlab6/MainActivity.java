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
            String line;
            String manufacturer = "";
            while(input.ready()) {
                ArrayList<CarModel> models = new ArrayList<>();
                while ((line = input.readLine()).compareTo("END") != 0) {
                    ArrayList<String> nextModel = new ArrayList<>(Arrays.asList(line.split(",")));
                    if(nextModel.size() == 1)
                        manufacturer = nextModel.remove(0);
                    else
                        models.add(new CarModel(nextModel.get(0), nextModel.get(1), nextModel.get(2), getResources().getIdentifier(nextModel.get(0).toLowerCase().replaceAll(" ",""),
                                "drawable", getPackageName() )));
                }
                mManufacturer.add(new Manufacturer(manufacturer, models));
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
            String name = temp.getModel(childPosition) != null ? temp.getModel(childPosition).getName() : getResources().getString(R.string.model_not_found);
            Toast.makeText(this, "Manufacturer: " + temp.getManufacturer() +
                            "\nModel: " + name,
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
