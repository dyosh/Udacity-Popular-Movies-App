package com.example.yoshi.popularmovies;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieDetailActivity extends Activity {
    private TextView movieTitle;
    private TextView movieRating;
    private TextView movieOverview;
    private TextView movieReleaseDate;
    private ImageView movieImagePoster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        // Fetch views
        movieTitle = (TextView) findViewById(R.id.movieTitle);
        movieRating = (TextView) findViewById(R.id.movieRating);
        movieOverview = (TextView) findViewById(R.id.movieOverview);
        movieReleaseDate = (TextView) findViewById(R.id.movieReleaseDate);
        movieImagePoster = (ImageView) findViewById(R.id.movieImagePoster);

        // Use the movie to populate teh data into our views
        MovieDataModel movie = (MovieDataModel)
                getIntent().getSerializableExtra(MainActivity.MOVIE_DETAIL_KEY);
        movieTitle.setText(movie.getMovieTitle());
        movieRating.setText(movie.getMovieRating() + " / 10");
        movieOverview.setText("Summary:" + movie.getMovieOverview());
        movieReleaseDate.setText(movie.getMovieReleaseDate());

        Picasso.with(this).load(movie.getPosterPath()).into(movieImagePoster);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
