package com.example.movie;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class NetworkUtils {

    // Base URL
    final static String GITHUB_BASE_URL =
            "https://api.themoviedb.org/3/movie?api_key=";

    final static String PARAM_append = "append_to_response";
    final static String language_append = "language";
    final static String append_responce = "videos";
    final static String append_responce_language = "en-US";
    final static String RESPONSE = "reviews";
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();


    // fetch movies data
    public static ArrayList<Movies> fetchMovieData(String requestUrl) {
// build our url from the sort by popular or top rated from the main activity
        URL url = buildUrl(requestUrl);
// we get the complete Url for us to pass to HTTP
        String jsonResponse = null;
        try {
            jsonResponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
// We get the result of the HTTP and pass it to JSon method
        ArrayList<Movies> movies = extractFeatureFromJson(jsonResponse);

        return movies;
    }

    // This is called to fetch the trailer data
    public static ArrayList<Trailers> fetchTrailerData(String requestId) {
// build our url from the sort by popular or top rated from the main activity
        URL url = TrailerBuildUrl(requestId);
// we get the complete Url for us to pass to HTTP
        String jsonResponse = null;
        try {
            jsonResponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
// We get the result of the HTTP and pass it to JSon method
        ArrayList<Trailers> trailers = extractTrailerFeatureFromJson(jsonResponse);
        return trailers;
    }


    // This is called to fetch the review data
    public static ArrayList<Reviews> fetchReviewrData(String requestId) {
// build our url from the sort by popular or top rated from the main activity
        URL url = ReviewBuildUrl(requestId);
// we get the complete Url for us to pass to HTTP
        String jsonResponse = null;
        try {
            jsonResponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
// We get the result of the HTTP and pass it to JSon method
        ArrayList<Reviews> reviews = extractReviewFeatureFromJson(jsonResponse);
        return reviews;
    }

    // build URI for the movie data
    public static URL buildUrl(String SearchQuery) {

        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendPath(SearchQuery)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    // trailer build url
    public static URL TrailerBuildUrl(String idQuery) {

        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendPath(idQuery)
                .appendQueryParameter(language_append, append_responce_language)
                .appendQueryParameter(PARAM_append, append_responce)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    // reviews URL
    public static URL ReviewBuildUrl(String idQuery) {

        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendPath(idQuery)
                .appendPath(RESPONSE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream in = urlConnection.getInputStream();

        Scanner scanner = new Scanner(in);
        try {

            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                scanner.close();
                return null;
            }
        } finally {
            scanner.close();
            urlConnection.disconnect();
        }
    }

    // movie JSONResponse
    public static ArrayList<Movies> extractFeatureFromJson(String movieJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }
        // we initiate our arraylist of Movies
        ArrayList<Movies> movies = new ArrayList<>();
        try {
            // we get our base JSONobject
            JSONObject baseJSONResponse = new JSONObject(movieJSON);
            // we get the jasonArray results
            JSONArray movieArray = baseJSONResponse.getJSONArray("results");
            // we loop to get all the relevant information for our Movies details
            for (int i = 0; i < movieArray.length(); i++) {
                // we get the JSONObject from the inicial position
                JSONObject results = movieArray.getJSONObject(i);
                // get the poster path
                String moviePoster = results.getString("poster_path");
                // get movie ID
                int id = results.getInt("id");
                // get the title
                String tittle = results.getString("title");
                // get the vote average
                String voteAverage = results.getString("vote_average");
                // get the description
                String plotSynopsis = results.getString("overview");
                String releaseDate = results.getString("release_date");
                // we add all to the newly constructed Movies
                Movies newMovies = new Movies(moviePoster, id, tittle, voteAverage, plotSynopsis, releaseDate);
                movies.add(newMovies);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("NetworkUtil", "Problem parsing the movie JSON results", e);
        }
        return movies;
    }

    // Trailer JSONResponse
    public static ArrayList<Trailers> extractTrailerFeatureFromJson(String trailerJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(trailerJSON)) {
            return null;
        }
        // we initiate our arraylist of Movies
        ArrayList<Trailers> trailers = new ArrayList<>();
        try {
            // we get our base JSONobject
            JSONObject baseJSONResponse = new JSONObject(trailerJSON);
            JSONObject video = baseJSONResponse.getJSONObject("videos");
            JSONArray videoArray = video.getJSONArray("results");
                JSONObject results = videoArray.getJSONObject(0);
                String id = results.getString("id");
                String youtube = "https://www.youtube.com/watch?v=";
                String key = youtube + results.getString("key");
                String name = results.getString("name");
                Trailers newTrailer = new Trailers(id, key, name);
                trailers.add(newTrailer);


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("NetworkUtil", "Problem parsing the trailer JSON results", e);
        }
        return trailers;

    }

    // Reviews JSONResponse
    public static ArrayList<Reviews> extractReviewFeatureFromJson(String reviewJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(reviewJSON)) {
            return null;
        }
        // we initiate our array's of reviews
        ArrayList<Reviews> reviews = new ArrayList<>();
        try {
            // we get our base JSONObject
            JSONObject baseJSONResponse = new JSONObject(reviewJSON);
            JSONArray arrays = baseJSONResponse.getJSONArray("results");
            for (int i = 0; i < arrays.length(); i++) {
                JSONObject results = arrays.getJSONObject(i);
                String author = results.getString("author");
                String content = results.getString("content");
                String id = results.getString("id");
                Reviews mReviews = new Reviews(author, content, id);
                reviews.add(mReviews);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("NetworkUtil", "Problem parsing the review JSON results", e);
        }
        return reviews;
    }
}



