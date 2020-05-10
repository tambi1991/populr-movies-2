package com.example.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder> {
    private Context mContext;
    private ArrayList<Reviews> mReviews;

    public ReviewsAdapter(Context contennt, ArrayList<Reviews> reviews) {
        mContext = contennt;
        mReviews = reviews;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ReviewHolder viewHolder = new ReviewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        Reviews reviews = mReviews.get(position);
        holder.mAuthor.setText(reviews.getAuthor());
        holder.mContent.setText(reviews.getContent());
    }

    @Override
    public int getItemCount() {
        if (mReviews == null) return 0;
        return mReviews.size();
    }


    public void addAll(ArrayList<Reviews> reviews) {
        mReviews = reviews;
        notifyDataSetChanged();
    }

    class ReviewHolder extends RecyclerView.ViewHolder {
        private TextView mAuthor;
        private TextView mContent;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            mAuthor = (TextView) itemView.findViewById(R.id.author_id);
            mContent = (TextView) itemView.findViewById(R.id.content_id);
        }
    }
}
