package com.example.tinkazorge.apiapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class takes the datastring from fetchJSON and turns it in to a JSONObject
 * in which a JSONExeption can be thrown (this is not possible in the main thread).
 */
public class Compare{

    // fields
    Context coatContext;
    String datastring;

    // constructors
    public  Compare(String datastringArg){
    datastring = datastringArg;
    }

    // this method takes a datastring and turns it into an object with JSONExecption
    public JSONObject getData(java.lang.String datastring) throws JSONException {
        JSONObject respObj = new JSONObject(datastring.substring(datastring.indexOf("{"), datastring.lastIndexOf("}")));

        // return JSONObject
        return respObj;
    }
}