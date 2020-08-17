package com.example.myfirstapp.Entity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {UserTheme.class}, version = 1, exportSchema = false)
public abstract class UserThemeRoomDatabase extends RoomDatabase {

    public abstract UserThemeDao userThemeDao();

    private static volatile UserThemeRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static UserThemeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserThemeRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserThemeRoomDatabase.class, "userTheme_database")
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                UserThemeDao dao = INSTANCE.userThemeDao();
               // dao.deleteAll();

                UserTheme userTheme = new UserTheme("DEFAULT");
                dao.insert(userTheme);

            });
        }
    };


}
