package com.example.android.moviephase1;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lenovov on 4/21/2016.
 */

public class StringLoader extends AsyncTask<Void,Void,String[]> {

    String mainURL;
    Parser parser;
    String JSONCode;


    public StringLoader(String mainUrl , Parser parser){
        this.mainURL = mainUrl;
        this.parser = parser;
    }

    public StringLoader(){

    }


    @Override
    protected String[] doInBackground(Void... params) {
           try {

               URL url = new URL(mainURL);
               HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
               urlConnection.setRequestMethod("GET");
               urlConnection.connect();
               InputStream inputStream = urlConnection.getInputStream();
               BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
               StringBuilder JSONCodeBuilder = new StringBuilder();
               String line;
               while ((line = bufferedReader.readLine()) != null){
                   JSONCodeBuilder.append(line + '\n');
               }

               JSONCode = JSONCodeBuilder.toString();
               Log.v("TestData",JSONCode);

                return parser.parse(JSONCode);

           } catch (MalformedURLException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           } catch (JSONException e) {
               e.printStackTrace();
           }

       return null;
    }


    public String getJSONCode(){
        return JSONCode;
    }

}
