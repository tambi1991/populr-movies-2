package com.example.movie;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;


public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();
    // variable for the live data
    private LiveData<List<Movies>> movies;
    // variable for the MovieRepository class
    private MovieRepository movieRepository;

    // constructor for MainViewModel to handle the MovieRepository and initializing it
    public MainViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        movies = movieRepository.loadAllMovies();
    }

    // get the movies as a live data
    public LiveData<List<Movies>> getMovies() {
        return movies;
    }

    // load data for the sort criteria Movies
    public MutableLiveData<List<Movies>> loadData(String sort) {
        return movieRepository.retrieveMovies(sort);
    }

    // load data for the sort criteria Trailer
    public MutableLiveData<List<Trailers>> loadTrailerData(String sort) {
        return movieRepository.retrieveTrailers(sort);
    }

    // load data for the sort criteria Reviews
    public MutableLiveData<List<Reviews>> loadReviewData(String sort) {
        return movieRepository.retrieveReviews(sort);
    }
}
