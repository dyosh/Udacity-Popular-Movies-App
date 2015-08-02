package com.example.yoshi.popularmovies;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MovieDBClient {
    private final String API_KEY = "YOUR API KEY HERE";
    private final String API_BASE_URL = "http://api.themoviedb.org/3/";
    private final String API_CAST_URL = "http://api.themoviedb.org/3/movie/";

    MovieDataModel extraRequests = new MovieDataModel();

//    String movieId = extraRequests.getMovieId();

    private AsyncHttpClient client;

    public MovieDBClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    public void getMovies(JsonHttpResponseHandler handler) {
        String url = getApiUrl("discover/movie?sort_by=popularity.desc");
        RequestParams params = new RequestParams("api_key", API_KEY);
        client.get(url, params, handler);
    }

    public void getMovieCast(JsonHttpResponseHandler handler, String movieId) {
        String url = API_CAST_URL + movieId + "/credits";
        RequestParams params = new RequestParams("api_key", API_KEY);
        client.get(url,params, handler);
    }



}
