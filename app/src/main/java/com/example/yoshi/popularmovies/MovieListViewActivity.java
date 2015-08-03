package com.example.yoshi.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class MovieListViewActivity extends AppCompatActivity {
    MainActivity main = new MainActivity();
    private MovieListViewAdapter adapterMovies;
    MovieDBClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ListView lvMovies = (ListView)findViewById(R.id.lvMovies);
        ArrayList<MovieDataModel> aMovies = new ArrayList<MovieDataModel>();
        adapterMovies = new MovieListViewAdapter(this, aMovies);
        lvMovies.setAdapter(adapterMovies);

        main.sortMovies(adapterMovies, "movieTitle");
//        main.fetchMovieData(client, adapterMovies);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspecti on SimplifiableIfStatement
        if (id == R.id.movieGridView) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
