package com.example.myfirstapp.Entity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserThemeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserTheme userTheme);

    @Query("DELETE FROM userTheme_table")
    void deleteAll();

    @Query("SELECT * from userTheme_table ORDER BY title ASC")
    LiveData<List<UserTheme>> getAlphabetizedUserThemes();

}
