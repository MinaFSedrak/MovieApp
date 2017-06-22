package com.example.android.moviephase1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovov on 4/23/2016.
 */
public class ReviewParser implements Parser {
    JSONObject allPage;
    JSONArray results;
    String[] reviewTexts;
    public int size;

    @Override
    public String[] parse(String JSONCode) throws JSONException {
        allPage = new JSONObject(JSONCode);
        size= allPage.getInt("total_results");
        results= allPage.getJSONArray("results");
        reviewTexts = new String[size];
        for (int i = 0; i < size ; i++) {
            reviewTexts[i] = results.getJSONObject(i).getString("content");
            Log.d("getVideoKey: ", reviewTexts[i]);
        }

        return reviewTexts;
    }
}
