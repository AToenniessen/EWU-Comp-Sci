package com.toenniessen.alex.atoenniessenlab2;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private DrawerLayout mDrawer;
    private ListView mNav;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            ImageView cur = (ImageView) findViewById(R.id.cur_image);
            cur.setImageResource(savedInstanceState.getInt("cur_image"));
            cur.setTag(savedInstanceState.getInt("cur_image"));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.nav_list_row, R.id.photo_name,
                getResources().getStringArray(R.array.names));
        mNav = (ListView) findViewById(R.id.nav_list_view);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNav.setAdapter(adapter);
        mNav.setOnItemClickListener(this);

        final String hidden = this.getTitle().toString();
        final String exposed = getResources().getString(R.string.exposed_nav);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer,
                0, 0){
            @Override
            public void onDrawerOpened(View v){
                super.onDrawerOpened(v);
                setTitle(exposed);
                invalidateOptionsMenu();
            }
            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
                setTitle(hidden);
                invalidateOptionsMenu();
            }
        };
        mDrawer.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true) ;
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        if(mDrawer.isDrawerOpen(mNav)){
            menu.findItem(R.id.action_about).setVisible(false);
        }
        return true;
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
        if (mDrawerToggle.onOptionsItemSelected(item)) return true ;
        if (id == R.id.action_about) {
            Toast.makeText(this, R.string.about_output,
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onToggleClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView cur = (ImageView) findViewById(R.id.cur_image);
        TypedArray tempIds = getResources().obtainTypedArray(R.array.ids);
        int[] imageIds = new int[tempIds.length()];
        for(int i = 0; i < tempIds.length(); i ++){
            imageIds[i] = tempIds.getResourceId(i, 0);
        }
        tempIds.recycle();
        cur.setImageResource(imageIds[position]);
        cur.setTag(imageIds[position]);
    }
    @Override
    public void onSaveInstanceState(Bundle onSaveInstanceState){
        super.onSaveInstanceState(onSaveInstanceState);
        onSaveInstanceState.putInt("cur_image", (int) findViewById(R.id.cur_image).getTag());
    }
}
