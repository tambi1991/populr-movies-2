package com.example.movie;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


// this is the movies class for storing information about the MOvies
// the table name of the database
@Entity(tableName = "movie_table")
public class Movies implements Parcelable {
    public boolean isFavorite = false;
    // variable for id
    @PrimaryKey(autoGenerate =true)
    private int id;
    // variable for title
    @ColumnInfo(name ="title")
    private String title;
    // variable for release date
    @ColumnInfo(name ="release_date")
    private String releaseDate;
    // variable for movie poster
    @ColumnInfo(name = "movie_poster")
    private String moviePoster;
    // variable for voter average
    @ColumnInfo(name ="vote_average")
    private String voteAverage;
    // variable for movie description
    @ColumnInfo(name ="plot_synopsis")
    private String plotSynopsis;


    public Movies(String moviePoster,int id,String title,String voteAverage,String plotSynopsis,String releaseDate) {
        this.title = title;
        this.id = id;
        this.releaseDate = releaseDate;
        this.moviePoster = moviePoster;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
    }

    // The get method for
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

// setter methods


    public void setId(int id) {
        this.id = id;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(moviePoster);
        dest.writeString(voteAverage);
        dest.writeString(plotSynopsis);
        dest.writeInt(id);
    }
@Ignore
    public Movies(Parcel parcelable) {
        title = parcelable.readString();
        releaseDate = parcelable.readString();
        moviePoster = parcelable.readString();
        voteAverage = parcelable.readString();
        plotSynopsis = parcelable.readString();
        id = parcelable.readInt();
    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {

        @Override
        public Movies createFromParcel(Parcel parcel) {
            return new Movies(parcel);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[0];
        }
    };
}


