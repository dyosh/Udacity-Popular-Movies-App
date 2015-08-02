package com.example.yoshi.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieCastDataModel implements Serializable {
    private String movieCastMember;

    public String getMovieCastMember() { return movieCastMember; };

    public static MovieCastDataModel fromJson(JSONObject jsonObject) {
        MovieCastDataModel castData = new MovieCastDataModel();
        try {
            // Deserialize the json into object fields
            castData.movieCastMember = jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return castData;
    }

    public static ArrayList<MovieCastDataModel> fromJson(JSONArray jsonArray) {
        ArrayList<MovieCastDataModel> movies = new ArrayList<MovieCastDataModel>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject moviesJson = null;
            try {
                moviesJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            MovieCastDataModel movie = MovieCastDataModel.fromJson(moviesJson);
            if (movie != null) {
                movies.add(movie);
            }
        }
        return movies;
    }



}
