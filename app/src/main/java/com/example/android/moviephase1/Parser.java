package com.example.android.moviephase1;

import org.json.JSONException;

/**
 * Created by lenovov on 4/21/2016.
 */
public interface Parser {
    String[] parse(String JSONCode) throws JSONException;
}
