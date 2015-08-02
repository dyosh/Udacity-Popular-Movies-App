package com.example.yoshi.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends AppCompatActivity {
    private GridView gvMovies;
    private MovieAdapter adapterMovies;
    MovieDBClient client;
    MovieDBClient castClient;

    public static final String MOVIE_DETAIL_KEY = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gvMovies = (GridView) findViewById(R.id.gvMovies);
        ArrayList<MovieDataModel> aMovies = new ArrayList<MovieDataModel>();
        adapterMovies = new MovieAdapter(this, aMovies);
        gvMovies.setAdapter(adapterMovies);
        fetchMovieData(client, adapterMovies);
        setupMovieSelectedListener();
    }

    public void fetchMovieData(MovieDBClient client, final ArrayAdapter<MovieDataModel> adapterMovies) {
        client = new MovieDBClient();
        client.getMovies(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray items = null;
                try {
                    // Get the movies json array
                    items = response.getJSONArray("results");
                    // Parse json array into array of model objects
                    ArrayList<MovieDataModel> movies = MovieDataModel.fromJson(items);

                    // TODO: have fetchMovieData return the movies ArrayList to be used in a separate method
                    // TODO: this new method will populate the adapterMovies adapter
                    // TODO: this method should take a parameter that specifies which JSON result to sort by
                    // Sort by rating in descending order, *NOTE* rhs comparedTo lhs for descending in this case.
                    Collections.sort(movies, new Comparator<MovieDataModel>() {
                        @Override
                        public int compare(MovieDataModel lhs, MovieDataModel rhs) {
                            return (rhs.getMovieRating().compareTo(lhs.getMovieRating()));
                        }
                    });

                    // Load model objects into the adapter
                    for (MovieDataModel movie : movies) {
                        adapterMovies.add(movie); // add movie through the adapter
                    }

                    adapterMovies.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setupMovieSelectedListener() {
        gvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, MovieDetailActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.moviePosterListView) {
            startActivity(new Intent(this, MovieListViewActivity.class));
            return true;
        }

        if (id == R.id.sortMovies) {

        }

        return super.onOptionsItemSelected(item);
    }
}
