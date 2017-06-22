package com.example.android.moviephase1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovov on 4/23/2016.
 */
public class MovieParser {
    int movieNum;

    public MovieParser(int movieNum){
        this.movieNum = movieNum;
    }

    public static MovieInfo parseAll(String JSONCode , int movieNum) throws JSONException {
        MovieInfo movie = new MovieInfo();
        JSONObject allPage = new JSONObject(JSONCode);
        JSONArray results= allPage.getJSONArray("results");
        JSONObject movieInfo = results.getJSONObject(movieNum);
        movie.setTitle(movieInfo.getString("title"));
        movie.setImageURL("http://image.tmdb.org/t/p/w300/" + movieInfo.getString("poster_path"));
        movie.setOverview(movieInfo.getString("overview"));
        movie.setReleaseDate(movieInfo.getString("release_date"));
        movie.setRating(movieInfo.getDouble("vote_average"));
        movie.setId(movieInfo.getInt("id"));
        return movie;
    }
}
