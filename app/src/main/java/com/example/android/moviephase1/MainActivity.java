package com.example.android.moviephase1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MainFragment.Callback {

boolean mTwoPane ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if(findViewById(R.id.a) != null)
        {
         mTwoPane=true;
        }
        else {
            mTwoPane=false;
            getSupportFragmentManager().beginTransaction().add(R.id.activity_main, new MainFragment()).commit();
        }


    }


    @Override
    public void onItemSelected(MovieInfo pos) {

        if(mTwoPane)
        {
         Bundle bundle = new Bundle();
            bundle.putString("data1", pos.getTitle());
            bundle.putString("data2", pos.getImageURL());
            bundle.putString("data3", pos.getYear());
            bundle.putString("data4", pos.getRating());
            bundle.putString("data5", pos.getOverview());
            bundle.putInt("data6", pos.getId());
            DetailsFragment detailsFragment=new DetailsFragment();
            detailsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.b,detailsFragment).commit();
        }
        else {

            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("data1", pos.getTitle());
            intent.putExtra("data2", pos.getImageURL());
            intent.putExtra("data3", pos.getYear());
            intent.putExtra("data4", pos.getRating());
            intent.putExtra("data5", pos.getOverview());
            intent.putExtra("data6", pos.getId());
            startActivity(intent);
        }
    }
}
