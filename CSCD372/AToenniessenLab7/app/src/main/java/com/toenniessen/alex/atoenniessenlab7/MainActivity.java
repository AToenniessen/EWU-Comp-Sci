package com.toenniessen.alex.atoenniessenlab7;

import android.animation.TimeAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private ClockView mClock;
    private TimeAnimator mTimer;


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences temp = PreferenceManager.getDefaultSharedPreferences(this);
        mClock.updatePreferences(temp.getBoolean("clock_format", false),
                temp.getBoolean("partial_seconds", false),
                temp.getString("clock_face", getResources().getStringArray(R.array.clock_types)[0]));
        mTimer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClock = findViewById(R.id.Analog_Clock);
        mClock.getmTime().addObserver(this);
        initializeTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTimer.pause();
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
        digital.setText(mClock.getmTime().toString());
    }
    private void initializeTimer() {
        mTimer = new TimeAnimator();
        mTimer.setTimeListener(new TimeAnimator.TimeListener() {
            @Override
            public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
                if (totalTime % 100 == 0) {
                    mClock.invalidate();
                    if(totalTime % 1000 == 0) {
                        mClock.playClip();
                    }
                }
            }
        });
        mTimer.start();
    }
}
