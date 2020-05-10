package com.example.movie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    private static final String TAG = MainActivity.class.getSimpleName();

    // variable for recyclerView
    RecyclerView mRecyclerView;
    // variable for the MovieAdapter.class
    MovieAdapter mAdapter;
    // variable for number of column our gridView will display
    private int mNumberOFColumn = 2;
    // variable for our message
    private TextView mErrorMessage;
    // variable for the progress bar or loading bar
    ProgressBar mProgresBar;
    // variable for the sort by popularity
    final static String mPopular = "popular";
    // variable for the sort by top rated
    final static String mTopRated = "top_rated";
    // variable for the sort by top rated
    final static String mFavorites = "favorites";
    //initialized with mPopular sorting
    public  String CURRENT_SORT_CRITERIA=mPopular;
    // variable for the MainViewModel class
     MainViewModel mainViewModel;
     // variable for MovieDatabase class
    private MovieDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // reference for the recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.text_recycle_view);
        // reference for our text message
        mErrorMessage = (TextView) findViewById(R.id.tv_error_message_display);
        // reference for loading bar
        mProgresBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        // Creating our GridViewManager and initiating it with the content and number of column
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, mNumberOFColumn);
        // setting the recyclerView to a layoutManager
        mRecyclerView.setLayoutManager(gridLayoutManager);
        // setting its size
        mRecyclerView.setHasFixedSize(true);
        // Initiating our adapter and passing our source content and clicklistiner to this
        mAdapter = new MovieAdapter(this, new ArrayList<>(), this);
        // set our adapter to the recycler view
        mRecyclerView.setAdapter(mAdapter);
        // we publish our data to the UI
        mDb = MovieDatabase.getInstance(getApplicationContext());
// check for previous instance state if exist and check the last saved sort criteria
        if (savedInstanceState != null) {
            CURRENT_SORT_CRITERIA = savedInstanceState.getString("sortCriteria");
        }
        //we call the appropriate method according to last sort criteria that's now in CURRENT_SORT_CRITERIA
        switch (CURRENT_SORT_CRITERIA) {
            case mPopular:
                searchByPopular();
                break;
            case mTopRated:
                searchByTopRated();
                break;
            case mFavorites:
                setUpViewModel();
                break;
            default:
                searchByPopular();
                break;
        }
    }
// saving the current state of the sort criteria
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sortCriteria", CURRENT_SORT_CRITERIA);
        super.onSaveInstanceState(outState);
    }
// method for the favorite list of movies stored in the database
    public void setUpViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getMovies().observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(List<Movies> movies) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                mAdapter.addAll((ArrayList<Movies>) movies);
            }
        });
    }


    // search by popular method
    public void searchByPopular() {
        String popularSearch = mPopular;
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.loadData(popularSearch).observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(List<Movies> movies) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                mAdapter.addAll((ArrayList<Movies>) movies);
            }
        });
    }

    // search by top rated
    public void searchByTopRated() {
        String topRatedSearch = mTopRated;
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.loadData(topRatedSearch).observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(List<Movies> movies) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                mAdapter.addAll((ArrayList<Movies>) movies);
            }
        });
    }

    // make the error message method for invisible
    private void showMovieDataView() {
        // First, make sure the error is invisible method
        mErrorMessage.setVisibility(View.INVISIBLE);
    }

    // make the error message visible method
    private void showErrorMessage() {
        // Then, show the error
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    // the clickListener method from our adapter
    @Override
    public void onClick(Movies movieOfTheDay) {
        Context context = this;
        Class destination = DetailActivity.class;
        Intent intent = new Intent(context, destination);
        intent.putExtra("Movie", movieOfTheDay);
        intent.putExtra("isfavorite",movieOfTheDay.isFavorite);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.popular) {
            searchByPopular();
            CURRENT_SORT_CRITERIA=mPopular;
            return true;
        }
        if (itemThatWasClickedId == R.id.top_rated) {
            searchByTopRated();
            CURRENT_SORT_CRITERIA=mTopRated;
            return true;
        }
        if (itemThatWasClickedId == R.id.favorite) {
            setUpViewModel();
            CURRENT_SORT_CRITERIA=mFavorites;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
