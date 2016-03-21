package com.example.tinkazorge.apiapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.Manifest.permission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This class takes the datastring from fetchJSON and turns it in to a JSONObject
 * in which a JSONExeption can be thrown (this is not possible in the main thread).
 */

public class CoatActivity extends AppCompatActivity {

    TextView coatText;

    Context coatContext;

    ArrayAdapter<String> arrayAdapterMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.print("1000");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coat);

        // find widgets by id
        TextView coatText = (TextView) findViewById(R.id.coat_text);

        // get the intent that started the activity
        Intent coatactivity = getIntent();

        // get context
        coatContext = CoatActivity.this;

        // retrieve string from last activity
        Bundle city_inputs = coatactivity.getExtras();
        String city_input = (String) city_inputs.get(getString(R.string.city_input));

        // call FetchJSON to get JSON as an Object
        Object dataObject = new FetchJSON(city_input, coatContext).execute(city_input, coatContext);

        // convert object to string
        String datastring = dataObject.toString();

        // check for null
        if (datastring == null){
           coatText.setText(R.string.no_cities);
       }
        else {

            // parse json
            // create arraylist
            ArrayList<Integer> dataArray = new ArrayList<Integer>();
            try {

                // call class Compare
                Compare newObj = new Compare(datastring);

                // create JSONobject through getData
                JSONObject respObj = newObj.getData(datastring);

                // parse JSON for forecast
                JSONObject forecastObject = respObj.getJSONObject(getString(R.string.forecast));
                JSONArray forecast = forecastObject.getJSONArray(getString(R.string.forecast_elements));

                // find low in forecast object
                for (int i = 0; i < forecast.length(); i++) {
                    JSONObject unit =forecast.getJSONObject(i);
                    int tempValue = unit.getInt("low");
                    dataArray.add(new Integer(tempValue));

                    // Compare values (in fahrenheit) to Coatneeds
                    if (tempValue < 53) {
                        coatText.setText(R.string.wintercoat_need);
                    }
                    if (tempValue > 53 && tempValue < 66) {
                        coatText.setText(R.string.light_jacket_need);
                    }
                    if (tempValue > 66) {
                        coatText.setText(R.string.no_coat_need);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}