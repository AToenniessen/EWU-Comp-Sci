package com.toenniessen.alex.atoenniessenlab6;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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

public class MainActivity extends AppCompatActivity implements Serializable {
    private SectionPagerAdapter mSectionPagerAdapter;
    private ViewPager mViewPager;
    private ArrayList<Manufacturer> mManufacturer;
    private CarModel mCurModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null){
            mManufacturer = (ArrayList<Manufacturer>) savedInstanceState.getSerializable("Manufacturer");
            mCurModel = (CarModel) savedInstanceState.getSerializable("Model");
        }
        else{
            mManufacturer = new ArrayList<>();
            if(!parseFile("input.txt"))
                Toast.makeText(this, getResources().getString(R.string.parse_fail),
                        Toast.LENGTH_SHORT).show();
        }
        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.main_activity_view);
        mViewPager.setAdapter(mSectionPagerAdapter);
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
        savedInstanceState.putSerializable("Model", mCurModel);
        super.onSaveInstanceState(savedInstanceState);
    }
    public CarModel getmCurModel(){
        return mCurModel;
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
    public void onBackPressed() {
        if(mViewPager.getCurrentItem() == 1)
            mViewPager.setCurrentItem(0);
        else
            super.onBackPressed();
    }

    public void changePage(CarModel model){
        mCurModel = model;
        mSectionPagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(1);
    }
    class SectionPagerAdapter extends FragmentPagerAdapter {
        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int i) {
            if(i == 0)
                return ListFragment.newInstance(mManufacturer);
            else
                return new DetailedList();

        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
