package com.toenniessen.alex.atoenniessenlab1;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
            Toast.makeText(this,"Lab 1, Winter 2019, Alexander Toenniessen",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    protected void onButtonClick(View item) {
        int id = item.getId();
        if(id == R.id.Take_Survey){
            EditText input = findViewById(R.id.Name_Input);
            String name = input.getText().toString();
            if(name.isEmpty()){
                Toast.makeText(this, "Please input a name before continuing",
                        Toast.LENGTH_LONG).show();
            }
            else{
                Intent surveyIntent = new Intent(this, SurveyActivity.class);
                surveyIntent.putExtra("name", name);
                startActivityForResult(surveyIntent, 1);
            }
        }
        else if(id == R.id.Goto_Website){
            Toast.makeText(this,"goto website pressed",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
