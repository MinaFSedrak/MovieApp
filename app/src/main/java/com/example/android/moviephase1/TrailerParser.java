package com.example.android.moviephase1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovov on 4/23/2016.
 */
public class TrailerParser implements  Parser {

    JSONObject allPage;
    JSONArray results;
    String[] videokeys;

    @Override
    public String[] parse(String JSONCode) throws JSONException {
        allPage = new JSONObject(JSONCode);
        results = allPage.getJSONArray("results");
        videokeys = new String[20];
        for(int i=0 ; i<results.length(); i++){
            videokeys[i] = results.getJSONObject(i).getString("key");
            Log.d("getVideoKey:",videokeys[i]);
        }

        return  videokeys;
    }
}
