package com.example.android.moviephase1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovov on 4/21/2016.
 */
public class ImageParser implements Parser {

    JSONObject allPage;
    JSONArray results;
    String[] imageURLS;


    @Override
    public String[] parse(String JSONCode) throws JSONException {
        allPage = new JSONObject(JSONCode);
        results = allPage.getJSONArray("results");
        imageURLS = new String[results.length()];
        for(int i=0 ; i<results.length(); i++){
            imageURLS[i]= "http://image.tmdb.org/t/p/w185/"+ results.getJSONObject(i).getString("poster_path");
        }

        return imageURLS;
    }
}
