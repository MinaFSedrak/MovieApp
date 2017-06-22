package com.example.android.moviephase1;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONException;

/**
 * Created by lenovov on 4/21/2016.
 */
public class MainFragment extends Fragment {
    View rootView;
    StringLoader imageLoader;
    GridView gridView;
    public interface Callback
    {
         void onItemSelected (MovieInfo pos ) ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_main,container,false);
         gridView = (GridView) rootView.findViewById(R.id.gridView);
        setHasOptionsMenu(true);
        // Click On GridItem Send Item informations  To the Detail Activity by intent
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    //Parsing Info for the selected movie
                    MovieInfo movie = MovieParser.parseAll(imageLoader.getJSONCode(), position);
                    ((Callback) getActivity()).onItemSelected(movie);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               /* Intent intent = new Intent(getContext(),DetailsActivity.class);
                startActivity(intent);*/

            }
        });
        return rootView;
    }



    @Override
    public void onStart(){

        super.onStart();
            //Grid Images
            String mainUrl = "http://api.themoviedb.org/3/movie/popular?api_key=" + "c72c2d0bc776e2adf2212228f509f08b";

           /* By Creating imageLoader : The MainUrl go to the ASyncTask At StringLoader Then Parser(ImageParser in this case) return
           imageUrls Arrays To the DoInBackgournd then Here To The Specific OnPostExecute */

            imageLoader = new StringLoader(mainUrl, new ImageParser()) {
                @Override
                protected void onPostExecute(String[] strings) {
                    super.onPostExecute(strings);
                    CustomAdapter customAdapter = new CustomAdapter(getActivity(), strings);
                    GridView gridview = (GridView) rootView.findViewById(R.id.gridView);
                    gridview.setAdapter(customAdapter);

                }
            };
            imageLoader.execute();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // If user's Choice Top Rated Go Fetch and Parse Data As /top_rated
        if (item.getItemId() == R.id.action_top_rated) {

            String mainUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key=c72c2d0bc776e2adf2212228f509f08b";
            imageLoader = new StringLoader(mainUrl,new ImageParser()){
                @Override
                protected void onPostExecute(String[] strings) {
                    super.onPostExecute(strings);
                    CustomAdapter customAdapter = new CustomAdapter(getActivity(),strings);
                    GridView gridview = (GridView) rootView.findViewById(R.id.gridView);
                    gridview.setAdapter(customAdapter);

                }
            };
            imageLoader.execute();

            return true;
        }

        // If user's Choice most popular Go Fetch and Parse Data As /popular
        if (item.getItemId() == R.id.action_popular) {
            String mainUrl = "http://api.themoviedb.org/3/movie/popular?api_key=c72c2d0bc776e2adf2212228f509f08b";
            imageLoader = new StringLoader(mainUrl,new ImageParser()){
                @Override
                protected void onPostExecute(String[] strings) {
                    super.onPostExecute(strings);
                    CustomAdapter customAdapter = new CustomAdapter(getActivity(),strings);
                    GridView gridview = (GridView) rootView.findViewById(R.id.gridView);
                    gridview.setAdapter(customAdapter);

                }
            };
            imageLoader.execute();

            return true;
        }

        if(item.getItemId() == R.id.action_favorite){

            DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
            Cursor cursor = databaseHelper.getAllData();


            if(cursor.getCount() == 0){
                Toast.makeText(getContext(),"Error nothing found in DB",Toast.LENGTH_SHORT).show();
            }
            else {
                MovieInfo tempMovieInfos[] = new MovieInfo[40];



                for (int i = 0; i < 40; i++) {
                    tempMovieInfos[i] = new MovieInfo();
                    // tempFavImg[i] = new String();
                }
                int i = 0;
                if (cursor.moveToFirst()) {
                    do {
                        tempMovieInfos[i].setId(cursor.getInt(0));
                        tempMovieInfos[i].setTitle(cursor.getString(1));
                        tempMovieInfos[i].setImageURL(cursor.getString(2));
                        tempMovieInfos[i].setReleaseDate(cursor.getString(3));
                        tempMovieInfos[i].setOverview(cursor.getString(4));
                        tempMovieInfos[i].setRating(cursor.getDouble(5));
                        Log.i("Habibie: ", String.valueOf(tempMovieInfos[i].getId()));
                        Log.i("Habibie: ", tempMovieInfos[i].getTitle());
                        Log.i("Habibie: ", tempMovieInfos[i].getImageURL());
                        Log.i("Habibie: ", tempMovieInfos[i].getReleaseDate());
                        Log.i("Habibie: ", tempMovieInfos[i].getOverview());
                        Log.i("Habibie: ", String.valueOf(tempMovieInfos[i].getRating()));



                        i++;


                    } while (cursor.moveToNext());

                    final MovieInfo movies[] = new MovieInfo[i];
                    String FavImgUrls[] = new String[i];

                    for (int m = 0; m < i; m++) {
                        movies[m] = new MovieInfo();
                    }

                    for (int k = 0; k < i; k++) {
                        movies[k].setId(tempMovieInfos[k].getId());
                        movies[k].setTitle(tempMovieInfos[k].getTitle());
                        movies[k].setImageURL(tempMovieInfos[k].getImageURL());
                        movies[k].setReleaseDate(tempMovieInfos[k].getReleaseDate());
                        movies[k].setOverview(tempMovieInfos[k].getOverview());
                        movies[k].setRating(Double.parseDouble(tempMovieInfos[k].getRating()));
                        FavImgUrls[k] = tempMovieInfos[k].getImageURL();
                    }



                    CustomAdapter customAdapter = new CustomAdapter(getActivity(), FavImgUrls);
                    GridView FavView = (GridView) rootView.findViewById(R.id.gridView);
                    FavView.setAdapter(customAdapter);
                    FavView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            ((Callback) getActivity()).onItemSelected(movies[position]);


                        }
                    });


                }
            }
            // Content_Provider DB
            /*movieInfo = new MovieInfo();// Max 40 Films topRated + Popular
            String favimagesURl[] = new String[40];
            int counter =0;
            Cursor c = getContext().getContentResolver().query(UserProvider.CONTENT_URI,null,null,null,null);
            if(c.moveToFirst()){
                do {
                        movieInfo.setId(c.getInt(c.getColumnIndex(UserProvider.MDB_ID)));
                        movieInfo.setTitle(c.getString(c.getColumnIndex(UserProvider.MDB_ORIGINAL_TITLE)));
                        movieInfo.setImageURL(c.getString(c.getColumnIndex(UserProvider.MDB_POSTER_PATH)));
                        movieInfo.setOverview(c.getString(c.getColumnIndex(UserProvider.MDB_OVERVIEW)));
                        movieInfo.setRating(c.getDouble(c.getColumnIndex(UserProvider.MDB_VOTE_AVERAGE)));
                        movieInfo.setReleaseDate(c.getString(c.getColumnIndex(UserProvider.MDB_RELEASE_DATE)));
                    Log.i("Habibie: ", movieInfo.getTitle());
                    Log.i("Habibie: ", String.valueOf(movieInfo.getId()));
                    Log.i("Habibie: ", movieInfo.getImageURL());
                    favimagesURl[counter] = movieInfo.getImageURL();
                    counter = counter +1;

                }while(c.moveToNext());
            }
            CustomAdapter customAdapter = new CustomAdapter(getActivity(),favimagesURl);
            GridView gridview = (GridView) rootView.findViewById(R.id.gridView);
            gridview.setAdapter(customAdapter);*/



        }


        return super.onOptionsItemSelected(item);

    }

}

