package com.example.myfirstapp.Entity;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserThemeRepository {

    private UserThemeDao userThemeDao;
    private LiveData<List<UserTheme>> allUserThemes;


    UserThemeRepository(Application application) {
        UserThemeRoomDatabase db = UserThemeRoomDatabase.getDatabase(application);
        userThemeDao = db.userThemeDao();
        allUserThemes = userThemeDao.getAlphabetizedUserThemes();
    }

    LiveData<List<UserTheme>> getAllUserThemes() {
        return allUserThemes;
    }

    void insert(UserTheme userTheme) {
        UserThemeRoomDatabase.databaseWriteExecutor.execute(() -> {
            userThemeDao.insert(userTheme);
        });
    }

    void delete(UserTheme userTheme) {
        UserThemeRoomDatabase.databaseWriteExecutor.execute(() -> {
            userThemeDao.delete(userTheme);
        });
    }

    UserTheme getUserThemeByTitle(String title) {
        return userThemeDao.getUserThemeByTitle(title);
    }
}
