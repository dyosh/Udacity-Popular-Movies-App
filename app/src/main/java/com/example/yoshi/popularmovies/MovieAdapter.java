package com.example.yoshi.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<MovieDataModel> {

    public MovieAdapter(Context context, ArrayList<MovieDataModel> aMovie) {
        super(context, 0, aMovie);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MovieDataModel movie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_items, parent, false);
        }

        ImageView ivPosterImage = (ImageView) convertView.findViewById(R.id.imageView);
        Picasso.with(getContext()).load(movie.getPosterPath()).into(ivPosterImage);

        return convertView;
    }
}
