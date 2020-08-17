package com.example.myfirstapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.Activity.SettingsActivity;
import com.example.myfirstapp.Entity.UserTheme;
import com.example.myfirstapp.Entity.UserThemeViewModel;
import com.example.myfirstapp.R;
import com.example.myfirstapp.UserThemeListAdapter;
import com.example.myfirstapp.UserThemeListInterface;

import java.util.List;


public class UserThemeListFragment extends Fragment implements UserThemeListInterface {

    private RecyclerView recyclerView;
    private UserThemeListAdapter adapter;
    public UserThemeViewModel mUserThemeViewModel;
    private FragmentUserThemeListListener listener;


    public interface FragmentUserThemeListListener {
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
        adapter = new UserThemeListAdapter((Context) listener, this);
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
    public void onThemeSelected(UserTheme userTheme, boolean isSelected) {
        if (isSelected) {
            listener.onThemeSelected(userTheme);
        } else {
            UserTheme defaultTheme = mUserThemeViewModel.getUserThemeByTitle(UserThemeViewModel.DEFAULT_USERTHEME_TITLE);
            if (defaultTheme == null) throw new RuntimeException("Can't find default userTheme");
            listener.onThemeSelected(defaultTheme);

            if (listener instanceof SettingsActivity) {
                SettingsActivity.loadDataNull(((SettingsActivity) listener).findViewById(R.id.text_nodes_layout));
            }
        }
    }

    public void onThemeDeleted() {
        adapter.unselectedActiveItem();
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