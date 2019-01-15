package com.toenniessen.alex.atoenniessenlab1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
        EditText input = findViewById(R.id.Age_Input);
        String age = input.getText().toString();
        if(age.isEmpty()){
            Toast.makeText(this, R.string.no_age,
                    Toast.LENGTH_LONG).show();
        }
        else{
            Intent result = new Intent();
            result.putExtra("age", Integer.parseInt(age));
            setResult(Activity.RESULT_OK, result);
            finish();
        }
    }
}
