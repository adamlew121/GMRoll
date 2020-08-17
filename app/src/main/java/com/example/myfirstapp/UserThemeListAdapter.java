package com.example.myfirstapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.Entity.UserTheme;
import com.example.myfirstapp.Entity.UserThemeViewModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserThemeListAdapter extends RecyclerView.Adapter<UserThemeListAdapter.UserThemeViewHolder> {

    class UserThemeViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private CircleImageView image;
        private CardView cardView;
        private boolean selected;

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
    private String currentTitle;
    private UserThemeListInterface userThemeListInterface;
    private UserThemeViewHolder selectedUserThemeHolder;

    public UserThemeListAdapter(Context context, UserThemeListInterface userThemeListInterface) {
        inflater = LayoutInflater.from(context);
        this.userThemeListInterface = userThemeListInterface;
    }

    @NonNull
    @Override
    public UserThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.horizontal_recyclerview_item, parent, false);
        UserThemeViewHolder holder = new UserThemeViewHolder(itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedUserThemeHolder == null) {
                    holder.selected = true;
                    changeSelectedThemeStyle(holder);
                    selectedUserThemeHolder = holder;
                } else {
                    selectedUserThemeHolder.selected = false;
                    changeSelectedThemeStyle(selectedUserThemeHolder);
                    if (!selectedUserThemeHolder.title.equals(holder.title)) {
                        holder.selected = true;
                        changeSelectedThemeStyle(holder);
                        selectedUserThemeHolder = holder;
                    } else {
                        selectedUserThemeHolder = null;
                    }
                }
                System.out.println("oi");
                userThemeListInterface.onThemeSelected(mUserThemes.get(holder.getAdapterPosition()), !(selectedUserThemeHolder == null));
                System.out.println("oi2");


                notifyDataSetChanged();
            }
        });
        // userThemeListInterface.onThemeSelected(mUserThemes.get(currentIndex));
        // changeSelectedThemeStyle(holder, currentIndex);

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

        // changeSelectedThemeStyle(holder, position);
    }

    public void setUserThemes(List<UserTheme> userThemes) {
        mUserThemes = userThemes;
        notifyDataSetChanged();
    }

    public void changeSelectedThemeStyle(@NonNull UserThemeViewHolder holder) {
        if (holder.selected) {
            holder.cardView.setBackgroundColor(Color.parseColor("#7D6464"));
            holder.title.setTextColor(Color.parseColor("#ffffff"));
        } else {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                holder.cardView.setBackgroundColor(Color.parseColor("#424242"));
                holder.title.setTextColor(Color.parseColor("#c3c3c3"));
            } else {
                holder.cardView.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.title.setTextColor(Color.parseColor("#0B0909"));
            }
        }
    }

    public void unselectedActiveItem() {
        selectedUserThemeHolder.selected = false;
        changeSelectedThemeStyle(selectedUserThemeHolder);
        selectedUserThemeHolder = null;
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
