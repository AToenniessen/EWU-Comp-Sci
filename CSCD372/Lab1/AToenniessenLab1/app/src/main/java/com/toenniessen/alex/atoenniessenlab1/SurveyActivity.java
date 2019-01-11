package com.toenniessen.alex.atoenniessenlab1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        TextView greeting = (TextView)findViewById(R.id.Survey_Greeting);
        Intent intent = getIntent();
        if(intent != null){
            String name = intent.getStringExtra("name");
            if (name != null) {
                greeting.setText(greeting.getText() + " " + name);
            }
        }
    }
    protected void onButtonClick(View item) {
        int id = item.getId();
        if(id == R.id.Submit){
            Toast.makeText(this,"Submit pressed",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
