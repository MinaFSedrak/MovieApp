package com.example.android.moviephase1;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by lenovov on 4/23/2016.
 */

public class DetailsFragment extends Fragment implements View.OnClickListener{
    View rootView;
    Intent intent;
    Bundle arg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        arg=args;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        RelativeLayout header = (RelativeLayout) inflater.inflate(R.layout.details_header, null, false);
        ListView lv = (ListView) rootView.findViewById(R.id.listView_trailers);
        lv.addHeaderView(header);
        ImageView image = (ImageView) rootView.findViewById(R.id.imageView);
        TextView year = (TextView) rootView.findViewById(R.id.details_year);
        TextView rating = (TextView) rootView.findViewById(R.id.details_rating);
        TextView description = (TextView)rootView.findViewById(R.id.details_description);

        Button review = (Button) rootView.findViewById(R.id.button_reviews);
        review.setOnClickListener(this);

        Button favorite = (Button) rootView.findViewById(R.id.button_favorite);
        favorite.setOnClickListener(this);

        if(arg != null)
        {
            getActivity().setTitle(arg.getString("data1"));
            Picasso.with(getActivity()).load(arg.getString("data2")).into(image);
            year.setText(arg.getString("data3"));
            rating.setText(arg.getString("data4")+"/10");
            description.setText(arg.getString("data5"));

        }
        // Getting intent Sent from MainActivity
        else {
            intent = getActivity().getIntent();
            getActivity().setTitle(intent.getStringExtra("data1"));
            Picasso.with(getActivity()).load(intent.getStringExtra("data2")).into(image);
            year.setText(intent.getStringExtra("data3"));
            rating.setText(intent.getStringExtra("data4") + "/10");
            description.setText(intent.getStringExtra("data5"));
        }
        return rootView;

    }
    @Override
    public void onStart(){

        super.onStart();
        int movieID;
        // Trailer
        if(arg != null)
        {
            movieID = arg.getInt("data6",0);
        }
        else{
            movieID = intent.getIntExtra("data6",0);
        }

        String trailerUrl = "http://api.themoviedb.org/3/movie/"+ movieID + "/videos?api_key=" + "c72c2d0bc776e2adf2212228f509f08b";


        StringLoader trailerLoader = new StringLoader(trailerUrl, new TrailerParser()){
            @Override
            protected void onPostExecute(String[] strings) {
                super.onPostExecute(strings);
                int counter=0;

                for(int i=0; i<strings.length; i++) {
                    if (strings[i] == null) {
                        break;
                    } else {
                        counter = counter + 1;
                    }
                }

                final String youtubeUrlImages[] = new String[strings.length];
                final String youtubeUrlVideos[] = new String[strings.length];
                for(int i=0; i<strings.length; i++){
                    youtubeUrlImages[i] = ("http://img.youtube.com/vi/"+strings[i]+"/0.jpg");
                    youtubeUrlVideos[i] = ("https://www.youtube.com/watch?v="+strings[i]);
                }
                Log.i(":", String.valueOf(youtubeUrlImages.length));

                TrailerAdapter customAdapter = new TrailerAdapter(getActivity(),youtubeUrlImages    );
                ListView trailers = (ListView) rootView.findViewById(R.id.listView_trailers);
                trailers.setAdapter(customAdapter);

                trailers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrlVideos[position])));

                    }


                });

            }

        };
        trailerLoader.execute();



    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_reviews){
            int movieID;
            if(arg != null)
            {
                movieID = arg.getInt("data6",0);
            }
                else{
                movieID = intent.getIntExtra("data6",0);
            }

            // Reviews
            String reviewUrl = "http://api.themoviedb.org/3/movie/" + movieID + "/reviews?api_key=" + "c72c2d0bc776e2adf2212228f509f08b";
            StringLoader reviewLoader = new StringLoader(reviewUrl,new ReviewParser()) {
                @Override
                protected void onPostExecute(String[] strings) {
                    super.onPostExecute(strings);
                    String stringOfReviews = new String("");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Reviews");
                    for(int i=0; i<strings.length; i++){
                        if(strings[i]== null){
                            break;
                        }
                        else {
                            stringOfReviews += ("Review #"+(i+1)+"\n-------------------- \n"+strings[i]+"\n\n\n");
                        }
                    }

                    builder.setMessage(stringOfReviews);
                    builder.setPositiveButton("Ok", null);
                    builder.show();
                }
            };
            reviewLoader.execute();
        }


        if(v.getId() == R.id.button_favorite){
            MovieInfo movieInfo = new MovieInfo();
            movieInfo.setRating(intent.getDoubleExtra("data4", 0));
            movieInfo.setId(intent.getIntExtra("data6", 0));
            movieInfo.setOverview(intent.getStringExtra("data5"));
            movieInfo.setTitle(intent.getStringExtra("data1"));
            movieInfo.setImageURL(intent.getStringExtra("data2"));
            movieInfo.setReleaseDate(intent.getStringExtra("data3"));
            DatabaseHelper dataBaseHelper = new DatabaseHelper(getContext());
            boolean isInserted = dataBaseHelper.insertRow(movieInfo);

            if(isInserted){
                Toast.makeText(getContext(),"Data Inserted",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getContext(),"Data not Inserted",Toast.LENGTH_LONG).show();

            }
            //Content_Provider DB
          /*  ContentValues values = new ContentValues();
            if(arg != null)
            {
                values.put(UserProvider.MDB_ID,arg.getInt("data6", 0));
                values.put(UserProvider.MDB_OVERVIEW,arg.getString("data5"));
                values.put(UserProvider.MDB_ORIGINAL_TITLE,arg.getString("data1"));
                values.put(UserProvider.MDB_POSTER_PATH,arg.getString("data2"));
                values.put(UserProvider.MDB_RELEASE_DATE,arg.getString("data3"));
                values.put(UserProvider.MDB_VOTE_AVERAGE,arg.getString("data4"));
            }
            else{
                values.put(UserProvider.MDB_ID,intent.getIntExtra("data6", 0));
                values.put(UserProvider.MDB_OVERVIEW,intent.getStringExtra("data5"));
                values.put(UserProvider.MDB_ORIGINAL_TITLE,intent.getStringExtra("data1"));
                values.put(UserProvider.MDB_POSTER_PATH,intent.getStringExtra("data2"));
                values.put(UserProvider.MDB_RELEASE_DATE,intent.getStringExtra("data3"));
                values.put(UserProvider.MDB_VOTE_AVERAGE,intent.getStringExtra("data4"));
            }

            Uri uri = getContext().getContentResolver().insert(UserProvider.CONTENT_URI,values);
            Toast.makeText(getContext(),uri.toString(),Toast.LENGTH_LONG).show();*/




        }

    }
}
