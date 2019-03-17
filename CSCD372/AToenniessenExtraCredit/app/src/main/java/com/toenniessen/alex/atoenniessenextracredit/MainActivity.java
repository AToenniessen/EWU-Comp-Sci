package com.toenniessen.alex.atoenniessenextracredit;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.dropbox.chooser.android.DbxChooser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener, Serializable {
    private ArrayList<Manufacturer> mManufacturer = new ArrayList<>();
    static final int DBX_CHOOSER_REQUEST = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(data != null){
                try {
                    mManufacturer.clear();
                    parseFile(this.getContentResolver().openInputStream((new DbxChooser.Result(data)).getLink()));
                    ((ExpandableListView)findViewById(R.id.customList)).setAdapter(new MyListAdapter(this, mManufacturer));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, getResources().getString(R.string.parse_fail),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DbxChooser dbxChooser = new DbxChooser("qd0abbxpwivg6eu");

        ExpandableListView listView = findViewById(R.id.customList);
        listView.setOnChildClickListener(this);
        MyListAdapter adapter = null;

        if(savedInstanceState == null) {
            try {
                parseFile(getResources().getAssets().open("input.txt"));
                adapter = new MyListAdapter(this, mManufacturer);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, getResources().getString(R.string.parse_fail),
                        Toast.LENGTH_SHORT).show();
            }
        }
        else{
            mManufacturer = (ArrayList) savedInstanceState.getSerializable("Manufacturer");
            adapter = new MyListAdapter(this, mManufacturer);
        }

        listView.setAdapter(adapter);

        findViewById(R.id.drop_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbxChooser.forResultType(DbxChooser.ResultType.FILE_CONTENT).launch(MainActivity.this, DBX_CHOOSER_REQUEST);
            }
        });

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
    private void parseFile(InputStream input){
        try {
            ArrayList<Character> letters = new ArrayList<>();
            String manufacturer = "";
            ArrayList<String> model = new ArrayList<>();
            int cur;
                while((cur = input.read()) != -1){
                    while (cur != ',' && cur != '\r' && cur != '\n' && cur != -1) {
                        letters.add((char) cur);
                        cur = input.read();
                    }
                    if(manufacturer.length() < 1){
                        manufacturer = convertWord(letters);
                    }
                    else{
                        model.add(convertWord(letters));
                    }
                    if(cur == '\n' || cur == '\r'){
                        mManufacturer.add(new Manufacturer(manufacturer, model));
                        manufacturer = "";
                        model = new ArrayList<>();
                        cur = input.read();
                    }
                }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private String convertWord(ArrayList<Character> letters){
        StringBuilder word = new StringBuilder();
        for(char c : letters){
            word.append(c);
        }
        letters.clear();
        return word.toString();
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
