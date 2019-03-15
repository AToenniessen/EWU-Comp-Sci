package com.toenniessen.alex.breakout;

import android.animation.TimeAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    GameView mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGame = findViewById(R.id.gameView);
        mGame.onInit(20, 4, 1, 5);
        mGame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //v.performClick();
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!mGame.isStarted() || mGame.isPaused())
                        mGame.startTimer();
                    else
                        mGame.pauseTimer();
                }
                return true;
            }
        });
        findViewById(R.id.left).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //v.performClick();
                if(!mGame.isStarted() || mGame.isPaused())
                    mGame.startTimer();
                return mGame.changeDirection(R.id.left, event);
            }
        });
        findViewById(R.id.right).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //v.performClick();
                if(!mGame.isStarted() || mGame.isPaused())
                    mGame.startTimer();
                return mGame.changeDirection(R.id.right, event);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences temp = PreferenceManager.getDefaultSharedPreferences(this);
//        mClock.updatePreferences(temp.getBoolean("clock_format", false),
//                temp.getBoolean("partial_seconds", false),
//                temp.getString("clock_face", getResources().getStringArray(R.array.clock_types)[0]));
        //mTimer.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //mGame.pause();
        mGame.pauseTimer();
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
        mGame.pauseTimer();
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Toast.makeText(this,getResources().getString(R.string.about_data),
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
