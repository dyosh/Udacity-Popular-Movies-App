package com.example.yoshi.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieDataModel implements Serializable {
    private String posterPath;
    private String movieTitle;
    private String movieRating;
    private String movieLength;
    private String movieOverview;
    public String movieReleaseDate;
    public String movieId;
    public String movieTrailerUrl;
    public String moviePopularity;

    private static final String TAG = MovieDataModel.class.getSimpleName();
    private final String API_BASE_PHOTO_URL = "http://image.tmdb.org/t/p/w185/";

    public String getPosterPath() {
        return API_BASE_PHOTO_URL + posterPath;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getMovieTrailerUrl() { return movieTrailerUrl; }

    public String getMoviePopularity() { return moviePopularity; }

    public static MovieDataModel fromJson(JSONObject jsonObject) {
        MovieDataModel movieData = new MovieDataModel();
        try {
            // Deserialize the json into object fields
            movieData.posterPath = jsonObject.getString("poster_path");
            movieData.movieTitle = jsonObject.getString("original_title");
            movieData.movieOverview = jsonObject.getString("overview");
            movieData.movieRating = jsonObject.getString("vote_average");
            movieData.movieReleaseDate = jsonObject.getString("release_date");
            movieData.movieId = jsonObject.getString("id");
            movieData.moviePopularity = jsonObject.getString("popularity");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return movieData;
    }

    public static ArrayList<MovieDataModel> fromJson(JSONArray jsonArray) {
        ArrayList<MovieDataModel> movies = new ArrayList<MovieDataModel>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject moviesJson = null;
            try {
                moviesJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            MovieDataModel movie = MovieDataModel.fromJson(moviesJson);
            if (movie != null) {
                movies.add(movie);
            }
        }
        return movies;
    }

}




