package com.example.movie;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// this interface is for reading writing updating and deleting a movie in the database
// returning a list of task Movie objects
@Dao
public interface MovieDao {
    // the query to select and populate on the UI
    @Query("SELECT * FROM movie_table")
    LiveData<List<Movies>> loadAllMovies();

    // inserting into our database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movies movies);

    // this is to update the database
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movies movies);

    // this is to delete from the database
    @Delete
    void deleteMovie(Movies movies);



}
