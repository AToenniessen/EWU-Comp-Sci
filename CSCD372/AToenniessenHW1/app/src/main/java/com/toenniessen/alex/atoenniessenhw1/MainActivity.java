package com.toenniessen.alex.atoenniessenhw1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {

    View[] mBoard;
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        for(View v : mBoard){
            outState.putSerializable(((Integer)v.getId()).toString(), (int) v.getTag());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBoard = new View[] {findViewById(R.id.IV_topleft), findViewById(R.id.IV_topmid), findViewById(R.id.IV_topright),
                findViewById(R.id.IV_midleft), findViewById(R.id.IV_midmid), findViewById(R.id.IV_midright),
                findViewById(R.id.IV_botleft), findViewById(R.id.IV_botmid), findViewById(R.id.IV_botright)};
        if(savedInstanceState != null){
            for(View v : mBoard){
                int tag = (int) savedInstanceState.getSerializable(((Integer) v.getId()).toString());
                ((ImageView) v).setImageResource(tag);
                createDragListener(v, tag);
            }
        }
        else {
            initDragListeners();
        }
        initTouchListeners();
    }

    private void initTouchListeners() {
        View x = findViewById(R.id.IV_x);
        View o = findViewById(R.id.IV_o);

        x.setOnTouchListener(this);
        o.setOnTouchListener(this);

        x.setTag(R.drawable.x);
        o.setTag(R.drawable.o);
    }

    private void initDragListeners() {
        for (View v : mBoard) {
            createDragListener(v, R.drawable.blank);
        }
    }

    private void createDragListener(View v, int tag){
        v.setOnDragListener(this);
        v.setTag(tag);
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

    public void onToggleClick(View view) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
            v.startDrag(null, shadow, v, 0);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            if ((int) v.getTag() != R.drawable.blank) {
                Toast.makeText(this, getResources().getString(R.string.invalid),
                        Toast.LENGTH_SHORT).show();
            } else {
                int tag = (int) ((View) event.getLocalState()).getTag();
                ((ImageView) v).setImageResource(tag);
                v.setTag(tag);
            }
        }
        return true;
    }
}
