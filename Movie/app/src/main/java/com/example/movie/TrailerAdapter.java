package com.example.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private final TrailerAdapterOnClickHandler mClickHandler;

    public interface TrailerAdapterOnClickHandler {
        void onClick(Trailers trailerOfDay);
    }

    // The source of data trailerMovies
    private ArrayList<Trailers> mTrailers;
    // content
    private Context content;


    public TrailerAdapter(TrailerAdapterOnClickHandler mClickHandler, Context context, ArrayList<Trailers> trailers) {
        this.mClickHandler = mClickHandler;
        mTrailers = trailers;
        content = context;

    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.trailer_item_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Trailers type = mTrailers.get(position);
        holder.mText.setText(type.getName());
    }

    @Override
    public int getItemCount() {
        if (null == mTrailers) return 0;
        return mTrailers.size();
    }

    public void addAll(ArrayList<Trailers> trailers) {
        mTrailers = trailers;
        notifyDataSetChanged();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mText;


        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.text_trailer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Trailers trailerOfDay = mTrailers.get(adapterPosition);
            mClickHandler.onClick(trailerOfDay);

        }
    }
}
