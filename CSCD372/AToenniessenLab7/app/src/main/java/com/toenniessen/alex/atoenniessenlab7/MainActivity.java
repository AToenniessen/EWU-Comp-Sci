package com.toenniessen.alex.atoenniessenlab7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private ClockView.ViewableClock time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClockView view = findViewById(R.id.Analog_Clock);
        time = view.getmTime();
        time.addObserver(this);
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
            Toast.makeText(this,getResources().getString(R.string.about_data),
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.action_settings){
            MainActivity.this.startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable o, Object arg) {
        TextView digital = findViewById(R.id.Digital_Clock);
        digital.setText(time.toString());
    }
}
