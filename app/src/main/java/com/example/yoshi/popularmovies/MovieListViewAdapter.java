package com.example.yoshi.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieListViewAdapter extends ArrayAdapter<MovieDataModel> {

    public MovieListViewAdapter(Context context, ArrayList<MovieDataModel> aMovie) {
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
            convertView = inflater.inflate(R.layout.activity_movie_list_view, parent, false);
        }

        TextView movieTitle = (TextView) convertView.findViewById(R.id.movieTitle);
        TextView movieRating = (TextView) convertView.findViewById(R.id.movieRating);
        TextView movieCast = (TextView) convertView.findViewById(R.id.movieCast);

        movieTitle.setText(movie.getMovieTitle());
        movieRating.setText(movie.getMovieRating());
//        movieCast.setText(movie.getMovieCast());

        ImageView moviePosterImage = (ImageView) convertView.findViewById(R.id.moviePosterImage);
        Picasso.with(getContext()).load(movie.getPosterPath()).into(moviePosterImage);

        return convertView;
    }
}
