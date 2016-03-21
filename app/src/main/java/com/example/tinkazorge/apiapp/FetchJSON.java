package com.example.tinkazorge.apiapp;

/**
 * Goes to JSON through URL, saves the data and returns it as an object.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.StrictMode;
import android.Manifest.permission;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;

import static android.os.StrictMode.ThreadPolicy.*;

public class FetchJSON extends AsyncTask {

    //  fields
    public String city;
    public String open_weather_map_api;
    public StringBuffer json;
    public Context fetch_context;

    // constructor
    public FetchJSON (String cityArg, Context context){
        city = cityArg;
    }

    protected void onPreExecute(String city) {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        // get URL as string
        String url_total = fetch_context.getString(R.string.url);

       // split string so the user can type their own city
        String url_first_bit = url_total.split("amsterdam")[0];
        String url_var = city;
        String url_last_split[] = url_total.split("%2C");
        String url_last_bit = url_last_split[url_last_split.length - 1];
        String open_weather_map_api = url_first_bit + url_var + url_last_bit;
        String returnValue = "";

        // open URL
        URL url = null;
        try {
            url = new URL(open_weather_map_api);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        // make a connection
        HttpURLConnection connection;
        if(url!= null){
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // get responsecode
                Integer response = connection.getResponseCode();

                // check if responsecode is in 200's
                if (200<=response && response <=299){

                    // create bufferdreader to read JSON
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    // read in data
                    String line;
                    while ((line = reader.readLine())!= null){
                        returnValue = returnValue + line;
                    }
                }
                else {

                    // get errorstream and return null
                    BufferedReader reader = new BufferedReader (new InputStreamReader(connection.getErrorStream()));
                    return null;
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        // return value
        return returnValue;
    }


    protected static Object onPostExecute(StringBuffer json) throws JSONException {
        // save data in jsonObject
        JSONObject data = new JSONObject(json.toString());

        // if data is not retrieved succesfully, return null
        if (data.getInt("cod") != 200) {
            return null;
        }

        // return read data
        return data;
    }
}

