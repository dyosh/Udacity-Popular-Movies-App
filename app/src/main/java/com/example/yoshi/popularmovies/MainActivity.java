package com.example.yoshi.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

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

    public static ArrayList<MovieDataModel> movies = new ArrayList<>();
    public static final String MOVIE_DETAIL_KEY = "movie";

    protected boolean controlRefresh = false;

    static boolean hasFetchedData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gvMovies = (GridView) findViewById(R.id.gvMovies);
        ArrayList<MovieDataModel> aMovies = new ArrayList<MovieDataModel>();
        adapterMovies = new MovieAdapter(this, aMovies);
        gvMovies.setAdapter(adapterMovies);

        if (!hasFetchedData) {
            fetchMovieData(client, adapterMovies);
            hasFetchedData = true;
        }

        populateMovieData(adapterMovies);

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
                    movies = MovieDataModel.fromJson(items);
                    sortMovies(adapterMovies, "movieReleaseDate");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void populateMovieData(ArrayAdapter<MovieDataModel> adapterMovies) {
        // Load model objects into the adapter
        for (MovieDataModel movie : movies) {
            adapterMovies.add(movie); // add movie through the adapter
        }
        adapterMovies.notifyDataSetChanged();
    }


    public void sortMovies(ArrayAdapter<MovieDataModel> adapterMovies, String sortType) {

        switch(sortType){
            case "movieRating":
                Collections.sort(movies, new Comparator<MovieDataModel>() {
                    @Override
                    public int compare(MovieDataModel lhs, MovieDataModel rhs) {
                        return (rhs.getMovieRating().compareTo(lhs.getMovieRating()));
                    }
                });
                break;
            case "movieTitle":
                Collections.sort(movies, new Comparator<MovieDataModel>() {
                    @Override
                    public int compare(MovieDataModel lhs, MovieDataModel rhs) {
                        return (lhs.getMovieTitle().compareTo(rhs.getMovieTitle()));
                    }
                });
                break;
            case "movieReleaseDate":
                Collections.sort(movies, new Comparator<MovieDataModel>() {
                    @Override
                    public int compare(MovieDataModel lhs, MovieDataModel rhs) {
                        return (rhs.getMovieReleaseDate().compareTo(lhs.getMovieReleaseDate()));
                    }
                });
                break;
            default:
                Collections.sort(movies, new Comparator<MovieDataModel>() {
                    @Override
                    public int compare(MovieDataModel lhs, MovieDataModel rhs) {
                        return (rhs.getMovieRating().compareTo(lhs.getMovieRating()));
                    }
                });
                 break;
        }
        populateMovieData(adapterMovies);
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

        MenuItem item = menu.findItem(R.id.sortMoviesSpinner);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        spinner.setAdapter(ArrayAdapter.createFromResource(this,
                R.array.sortOptions, android.R.layout.simple_spinner_dropdown_item));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // TODO: USE SHAREDPREFERENCES TO KEEP TRACK OF WHICH SELECTION WAS PRESSED
                // TODO: ALSO, FIX SO THAT POSITION 0 DOESN'T AUTOMATICALLY GET SELECTED WHEN VIEW IS LOADED
                if (position == 0) {
//                     sortMovies(adapterMovies, "movieTitle");
//                     startActivity(new Intent(MainActivity.this, MainActivity.class));
                } else if (position == 1) {
                    sortMovies(adapterMovies, "movieRating");
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                } else if (position == 2) {
                    sortMovies(adapterMovies, "movieReleaseDate");
                    startActivity(new Intent(MainActivity.this, MainActivity.class));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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

        return super.onOptionsItemSelected(item);
    }
}
