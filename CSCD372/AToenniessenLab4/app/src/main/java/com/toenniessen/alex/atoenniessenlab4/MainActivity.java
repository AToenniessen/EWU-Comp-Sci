package com.toenniessen.alex.atoenniessenlab4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.toenniessen.alex.atoenniessenlab4.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        return super.onOptionsItemSelected(item);
    }
    public void onClick(View view){
        SevenSegment one = findViewById(R.id.sevenSegment1);
        SevenSegment two = findViewById(R.id.sevenSegment2);
        SevenSegment three = findViewById(R.id.sevenSegment3);
        SevenSegment four = findViewById(R.id.sevenSegment4);
        SevenSegment five = findViewById(R.id.sevenSegment5);

        int n = one.getmCurrentDisplay();

        one.setmCurrentDisplay(++n%11);
        two.setmCurrentDisplay(++n%11);
        three.setmCurrentDisplay(++n%11);
        four.setmCurrentDisplay(++n%11);
        five.setmCurrentDisplay(++n%11);
    }
}
