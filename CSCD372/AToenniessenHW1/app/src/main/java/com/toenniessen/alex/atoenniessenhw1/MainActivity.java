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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View x = findViewById(R.id.IV_x);
        View o = findViewById(R.id.IV_o);
        x.setOnTouchListener(this);
        x.setTag(R.drawable.x);
        o.setOnTouchListener(this);
        o.setTag(R.drawable.o);
        View tl, tm, tr, ml, mm, mr, bl, bm, br;
        tl = findViewById(R.id.IV_topleft);
        tm = findViewById(R.id.IV_topmid);
        tr = findViewById(R.id.IV_topright);
        ml = findViewById(R.id.IV_midleft);
        mm = findViewById(R.id.IV_midmid);
        mr = findViewById(R.id.IV_midright);
        bl = findViewById(R.id.IV_botleft);
        bm = findViewById(R.id.IV_botmid);
        br = findViewById(R.id.IV_botright);
        tl.setOnDragListener(this);
        tm.setOnDragListener(this);
        tr.setOnDragListener(this);
        ml.setOnDragListener(this);
        mm.setOnDragListener(this);
        mr.setOnDragListener(this);
        bl.setOnDragListener(this);
        bm.setOnDragListener(this);
        br.setOnDragListener(this);
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
    public void onToggleClick(View view) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
            v.startDrag(null, shadow, v, 0);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        if(event.getAction() == DragEvent.ACTION_DROP){
            int tag = (int) ((View) event.getLocalState()).getTag();
            ((ImageView) v).setImageResource(tag);
            v.setTag(tag);
        }
        return true;
    }
}
