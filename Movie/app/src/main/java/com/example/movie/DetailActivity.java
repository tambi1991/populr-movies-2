package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler {
    public static ArrayList<Movies> mMoviesArrayList = new ArrayList<>();
    // variable for movie poster
    ImageView mPoster;
    // variable for title
    TextView mDescriptionTitle;
    // variable for release date
    TextView mReleaseDate;
    // variable for voter average
    TextView mUserRating;
    // variable for movie description
    TextView mSynopsis;
    // recyclerView for trailer
    RecyclerView mTrailerRecyclerview;
    // recycler for  review
    RecyclerView mReviewRecyclerView;
    //the adapter for review
    ReviewsAdapter mReviewAdapter;
    // the adapter for the trailer
    TrailerAdapter mTrailerAdapter;
    // variable for the favorite button
    private ToggleButton favoriteBtn;
    // variable for the database
    private MovieDatabase mDb;
    // variable for the default id
    private static final int DEFAULT_MOVIE_ID = -1;
    // variable for the movie class
    private Movies mMovies;
    // boolean for favorite
    private boolean isFavorie = false;
    // variable for the MainViewModel class
    private MainViewModel mainViewModel;

    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_MOVIE_ID = "instanceMovieId";
    private int mMovieId = DEFAULT_MOVIE_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // reference for poster image
        mPoster = (ImageView) findViewById(R.id.image_iv);
        // reference for release date
        mReleaseDate = (TextView) findViewById(R.id.release_date);
        // reference for  user rating
        mUserRating = (TextView) findViewById(R.id.user_rating);
        // reference for  description
        mSynopsis = (TextView) findViewById(R.id.synopsis);
        // reference for  title
        mDescriptionTitle = (TextView) findViewById(R.id.detail_title);
        // reference for the favorite button
        favoriteBtn = (ToggleButton) findViewById(R.id.favorite_button);
        // reference for the trailer adapter
        mTrailerRecyclerview = (RecyclerView) findViewById(R.id.trailer_recycle_view);
        // set the fixed to true
        mTrailerRecyclerview.setHasFixedSize(true);
        // set the layout manager
        LinearLayoutManager TrailerManager = new LinearLayoutManager(this);
        // set the recycleview to the layout manager
        mTrailerRecyclerview.setLayoutManager(TrailerManager);
        // reference for the review adapter
        mTrailerAdapter = new TrailerAdapter(this, this, new ArrayList<>());
        // finally set the adapter to the recycleview
        mTrailerRecyclerview.setAdapter(mTrailerAdapter);

        // RecycleView Adapter, reviewRecycle
        mReviewRecyclerView = (RecyclerView) findViewById(R.id.review_recycle_view);
        // set the reviews to the size
        mReviewRecyclerView.setHasFixedSize(true);
        // set the layout manager for the review
        LinearLayoutManager reviewable = new LinearLayoutManager(this);
        // set the reviewAdapter to the layout manager
        mReviewRecyclerView.setLayoutManager(reviewable);
        //reference to adapter
        mReviewAdapter = new ReviewsAdapter(this, new ArrayList<>());
        mReviewRecyclerView.setAdapter(mReviewAdapter);
// we get the intent that started this activity from the main activity class
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity == null) {
            closeOnError();
        }
        // we get reference from the Movie which will convert the extra intent of Movies
        mMovies = intentThatStartedThisActivity.getParcelableExtra("Movie");
        isFavorie=intentThatStartedThisActivity.getBooleanExtra("isfavorite",false);
        // publish to the UI
        PopulateTheUi(mMovies);
        // populate the search trailers
        SearchTrailers(mMovies);
        // populate for the reviews
        SearchReviews(mMovies);
// initiating the database
        mDb = MovieDatabase.getInstance(getApplicationContext());
        // setting up the favorite button
        favoriteBtn.setChecked(false);
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFAVwithRoom();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        favoriteBtn = (ToggleButton) findViewById(R.id.favorite_button);
        for (int i = 0; i < mMoviesArrayList.size(); i++) {


            if (mMoviesArrayList.get(i).getMoviePoster().equals(mMovies.getMoviePoster())) {
                favoriteBtn.setChecked(false);

                break;

            }
        }
    }
    // The trailer methods to fetch the the trailers using a viewModel
    private void SearchTrailers(Movies movies) {
        int trailers = movies.getId();
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.loadTrailerData(String.valueOf(trailers)).observe(this, new Observer<List<Trailers>>() {
            @Override
            public void onChanged(List<Trailers> trailers) {
                mTrailerAdapter.addAll((ArrayList<Trailers>) trailers);
            }
        });
    }

    // The review methods to fetch the reviews using a viewModel
    private void SearchReviews(Movies movies) {
        int reviews = movies.getId();
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.loadReviewData(String.valueOf(reviews)).observe(this, new Observer<List<Reviews>>() {
            @Override
            public void onChanged(List<Reviews> reviews) {
                mReviewAdapter.addAll((ArrayList<Reviews>) reviews);
            }
        });

    }

    // This is when the trailer is click and an intent is sent to the youtube application.
    @Override
    public void onClick(Trailers trailerOfDay) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(trailerOfDay.getKey()));
        startActivity(intent);
    }

    // the error message for method
    private void closeOnError() {
        finish();
        Toast.makeText(this, " no image available", Toast.LENGTH_SHORT).show();
    }

    private void PopulateTheUi(Movies movies) {
        // get, build the url of the image into an image view
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w185" + movies.getMoviePoster())
                .into(mPoster);
        // get the release date
        mReleaseDate.setText(movies.getReleaseDate());
        // get the description
        mSynopsis.setText(movies.getPlotSynopsis());
        // get the tittle
        mDescriptionTitle.setText(movies.getTitle());
        // get the vote average on /10
        String userRatingText = (movies.getVoteAverage()) + "/10";
        mUserRating.setText(userRatingText);
    }

    private void saveFAVwithRoom() {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!isFavorie) {
                    // insert new task
                    mMovies.isFavorite = true;
                    mDb.movieDao().insertMovie(mMovies);
                } else {
                    //task delete from favorites
                    mDb.movieDao().deleteMovie(mMovies);
                }
                finish();
            }
        });


    }
}