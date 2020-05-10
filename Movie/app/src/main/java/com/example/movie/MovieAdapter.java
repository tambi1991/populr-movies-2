package com.example.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    // The source of data Movies
    private ArrayList<Movies> mMovies;
    // content
    private Context mContent;
    // variable for the clickAdapter
    private final MovieAdapterOnClickHandler mClickHandler;

    // constructor for the clickListener
    public interface MovieAdapterOnClickHandler {
        void onClick(Movies movieOfTheDay);
    }
// Constructor for the adapter

    /**
     * @param content       this
     * @param movies        source of data
     * @param mClickHandler
     */
    public MovieAdapter(Context content, ArrayList<Movies> movies, MovieAdapterOnClickHandler mClickHandler) {
        mMovies = movies;
        mContent = content;
        this.mClickHandler = mClickHandler;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.image_item_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movies type = mMovies.get(position);

        Picasso.get()
                .load("https://image.tmdb.org/t/p/w185" + type.getMoviePoster())
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        if (null == mMovies) return 0;
        return mMovies.size();
    }


    public void addAll(ArrayList<Movies> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    // the class for our view holder
    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // variable to the title
        TextView mTitle;
        // variable to the image
        ImageView mImage;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.image_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movies movieOfTheDay = mMovies.get(adapterPosition);
            mClickHandler.onClick(movieOfTheDay);
        }
    }

}
