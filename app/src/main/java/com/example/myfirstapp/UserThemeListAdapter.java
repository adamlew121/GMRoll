package com.example.myfirstapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.Activity.SettingsActivity;
import com.example.myfirstapp.Entity.UserTheme;
import com.example.myfirstapp.Entity.UserThemeViewModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserThemeListAdapter extends RecyclerView.Adapter<UserThemeListAdapter.UserThemeViewHolder> {

    class UserThemeViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private CircleImageView image;
        private CardView cardView;

        private UserThemeViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recyclerview_title);
            image = itemView.findViewById(R.id.recyclerview_image);
            cardView = itemView.findViewById(R.id.horizontal_item);
        }
    }

    private final LayoutInflater inflater;
    private List<UserTheme> mUserThemes;
    private UserThemeViewModel mUserThemeViewModel;
    private int currentIndex = -1;
    private String currentTitle;
    private UserThemeListInterface userThemeListInterface;

    public UserThemeListAdapter(Context context, UserThemeListInterface userThemeListInterface) {
        inflater = LayoutInflater.from(context);
        this.userThemeListInterface = userThemeListInterface;
        currentIndex = 0;
    }

    @NonNull
    @Override
    public UserThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.horizontal_recyclerview_item, parent, false);
        UserThemeViewHolder holder = new UserThemeViewHolder(itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("position = " + holder.getAdapterPosition());
                System.out.println("idk? = " + holder.title.getText().toString());
                currentIndex = holder.getAdapterPosition();
                userThemeListInterface.onThemeSelected(mUserThemes.get(holder.getAdapterPosition()));

                notifyDataSetChanged();
            }
        });
        userThemeListInterface.onThemeSelected(mUserThemes.get(currentIndex));
        changeSelectedThemeStyle(holder, currentIndex);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserThemeViewHolder holder, int position) {
        if (mUserThemes != null) {
            UserTheme current = mUserThemes.get(position);
            holder.title.setText(current.getTitle());
        } else {
            holder.title.setText("No Theme");
        }

        changeSelectedThemeStyle(holder, position);
    }

    public void setUserThemes(List<UserTheme> userThemes) {
        mUserThemes = userThemes;
        notifyDataSetChanged();
    }

    public void changeSelectedThemeStyle(@NonNull UserThemeViewHolder holder, int position) {
        if (currentIndex == position) {
            holder.cardView.setBackgroundColor(Color.parseColor("#7D6464"));
            holder.title.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.cardView.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.title.setTextColor(Color.parseColor("#0B0909"));
        }
    }

    @Override
    public int getItemCount() {
        if (mUserThemes != null) {
            return mUserThemes.size();
        } else {
            return 0;
        }
    }
}
