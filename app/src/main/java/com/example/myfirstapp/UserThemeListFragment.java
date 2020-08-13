package com.example.myfirstapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfirstapp.Entity.UserTheme;
import com.example.myfirstapp.Entity.UserThemeViewModel;

import java.util.List;


public class UserThemeListFragment extends Fragment implements UserThemeListInterface {

    private RecyclerView recyclerView;
    private UserThemeListAdapter adapter;
    public UserThemeViewModel mUserThemeViewModel;
    private FragmentUserThemeListListener listener;


    public interface  FragmentUserThemeListListener {
        void onThemeSelected(UserTheme theme);
    }

    public UserThemeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_theme_list, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerview);
        final UserThemeListAdapter adapter = new UserThemeListAdapter((Context) listener, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((Context) listener, LinearLayoutManager.HORIZONTAL, false));

        mUserThemeViewModel = new ViewModelProvider(this).get(UserThemeViewModel.class);

        mUserThemeViewModel.getAllUserThemes().observe(getViewLifecycleOwner(), new Observer<List<UserTheme>>() {
            @Override
            public void onChanged(List<UserTheme> userThemes) {
                adapter.setUserThemes(userThemes);
            }
        });

        return v;
    }

    @Override
    public void onThemeSelected(UserTheme userTheme) {
        listener.onThemeSelected(userTheme);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentUserThemeListListener) {
            listener = (FragmentUserThemeListListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FragmentUserThemeListListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


}