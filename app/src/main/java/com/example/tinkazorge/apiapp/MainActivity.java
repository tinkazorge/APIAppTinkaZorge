package com.example.tinkazorge.apiapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.Manifest.permission;

/**
 * This class takes the input from the user and pushes it to the next activity.
 */

public class MainActivity extends AppCompatActivity {
    Button okButton;
    EditText findOutEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find widgets by ID
        Button okButton = (Button) findViewById(R.id.ok_button);
        final EditText findOutText = (EditText) findViewById(R.id.find_out_edit);

        //if okButton is clicked
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // retrieve text from EditText
                String city_input = findOutText.getText().toString();

                //define next activity
                Intent coatactivity = new Intent(MainActivity.this, CoatActivity.class);

                //push  input to next activity
                coatactivity.putExtra("city_input", city_input);

                //start next activity
                startActivity(coatactivity);
            }
        });
    }
}
