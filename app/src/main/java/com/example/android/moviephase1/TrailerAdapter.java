package com.example.android.moviephase1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by lenovov on 11/22/2016.
 */
public class TrailerAdapter  extends ArrayAdapter<String> {
    Context context;
    String[] imageURLS;

    public TrailerAdapter(Context context, String[] imageURLS) {
        super(context,R.layout.trailer_item,imageURLS);
        this.context = context;
        this.imageURLS = imageURLS;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItem = convertView;
        if(gridItem == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridItem = inflater.inflate(R.layout.trailer_item,parent,false);
        }

        ImageView image = (ImageView) gridItem.findViewById(R.id.trailer_imageview);
        TextView textView = (TextView) gridItem.findViewById(R.id.trailer_textview);
        textView.setText("Trailer "+(position+1));
        Picasso.with(context).load(imageURLS[position]).placeholder(R.mipmap.ic_launcher).into(image);


        return gridItem;
    }
}
