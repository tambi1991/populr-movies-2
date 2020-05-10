package com.example.movie;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    // variable for MovieDoa class
    private MovieDao movieDao;
    // variable for MovieDatabase class
    private MovieDatabase mDb;
    // variable for live data
    private LiveData<List<Movies>> loadAllMovies;
    // initiating the variable for mutable live data movies
    private MutableLiveData<List<Movies>> networkData = new MutableLiveData<>();
    // initiating the variable for mutable live data Trailers
    private MutableLiveData<List<Trailers>> trailerData = new MutableLiveData<>();
    // initiating the variable for mutable live data Reviews
    private MutableLiveData<List<Reviews>> reviewData = new MutableLiveData<>();

    // constructor for the that will handle the database and initializes it.
    MovieRepository(Application application) {
        mDb = MovieDatabase.getInstance(application);
        movieDao = mDb.movieDao();
        loadAllMovies = movieDao.loadAllMovies();
    }
    // get methods to retrieve all data from the data base
    LiveData<List<Movies>> loadAllMovies() {
        return loadAllMovies;
    }

    // get methods for the movie data
    public MutableLiveData<List<Movies>> retrieveMovies(String sort) {
        new githubQueryTask().execute(sort);
        return networkData;
    }

    // get methods for the trailer data
    public MutableLiveData<List<Trailers>> retrieveTrailers(String id) {
        new TrailerSearchQuery().execute(id);
        return trailerData;
    }

    // get methods for the review data
    public MutableLiveData<List<Reviews>> retrieveReviews(String id) {
        new ReviewSearchQuery().execute(id);
        return reviewData;
    }

    public class githubQueryTask extends AsyncTask<String, Void, ArrayList<Movies>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Movies> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            ArrayList<Movies> result = NetworkUtils.fetchMovieData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Movies> movies) {
            if (movies != null) {
                networkData.setValue(movies);
            }
            super.onPostExecute(movies);
        }
    }

    public class TrailerSearchQuery extends AsyncTask<String, Void, ArrayList<Trailers>> {

        @Override
        protected ArrayList<Trailers> doInBackground(String... strings) {
            if (strings.length < 1 || strings[0] == null) {
                return null;
            }

            ArrayList<Trailers> results = NetworkUtils.fetchTrailerData(strings[0]);
            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<Trailers> trailers) {
            if (trailers != null) {
                trailerData.setValue(trailers);
            }
            super.onPostExecute(trailers);
        }
    }

    public class ReviewSearchQuery extends AsyncTask<String, Void, ArrayList<Reviews>> {

        @Override
        protected ArrayList<Reviews> doInBackground(String... strings) {
            if (strings.length < 1 || strings[0] == null) {
                return null;
            }
            ArrayList<Reviews> results = NetworkUtils.fetchReviewrData(strings[0]);
            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<Reviews> reviews) {
            if (reviews != null) {
                reviewData.setValue(reviews);
            }
            super.onPostExecute(reviews);
        }
    }
}