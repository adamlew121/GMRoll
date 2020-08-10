package com.example.myfirstapp.Entity;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserThemeViewModel extends AndroidViewModel {

    private UserThemeRepository repository;

    private LiveData<List<UserTheme>> allUserThemes;

    public UserThemeViewModel (Application application) {
        super(application);
        repository = new UserThemeRepository(application);
        allUserThemes = repository.getAllUserThemes();
    }

    public LiveData<List<UserTheme>> getAllUserThemes() {
        return allUserThemes;
    }

    public void insert(UserTheme userTheme) {
        repository.insert(userTheme);
    }

}
