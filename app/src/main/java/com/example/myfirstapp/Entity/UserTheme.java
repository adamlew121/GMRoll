package com.example.myfirstapp.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Entity(tableName = "userTheme_table")
public class UserTheme {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    public final static int ID_HEAD = 0;
    public final static int ID_NECK = 1;
    public final static int ID_ARM_LEFT = 2;
    public final static int ID_ARM_RIGHT = 3;
    public final static int ID_HAND_LEFT = 4;
    public final static int ID_HAND_RIGHT = 5;
    public final static int ID_TORSO = 6;
    public final static int ID_STOMACH = 7;
    public final static int ID_THIGH_LEFT = 8;
    public final static int ID_THIGH_RIGHT = 9;
    public final static int ID_FOOT_LEFT = 10;
    public final static int ID_FOOT_RIGHT = 11;

    /*
    [0]  - head
    [1]  - neck
    [2]  - arm_left
    [3]  - arm_right
    [4]  - hand_left
    [5]  - hand_right
    [6]  - torso
    [7]  - stomach
    [8]  - thigh_left
    [9]  - thigh_right
    [10] - foot_left
    [11] - foot right
     */
    private int[] chanceList;


    @Ignore
    public UserTheme(@NonNull String title, int head, int neck, int arm_left, int arm_right, int hand_left, int hand_right, int torso, int stomach, int thigh_left, int thigh_right, int foot_left, int foot_right) {
        this.title = title;
        this.chanceList = new int[]{head, neck, arm_left, arm_right, hand_left, hand_right, torso, stomach, thigh_left, thigh_right, foot_left, foot_right};
    }

    @Ignore
    public UserTheme(@NonNull String title, @NonNull int[] chances) {
        if (chances.length != 12) return;
        this.title = title;
        this.chanceList = chances;
    }

    public UserTheme(@NonNull String title) {
        this.title = title;
        this.chanceList = new int[12];
        Arrays.fill(chanceList, 100);
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setChanceList(int[] chanceList) {
        this.chanceList = chanceList;
    }

    public int[] getChanceList() {
        return chanceList;
    }

    public int calculateSum() {
        int sum = 0;
        for (int value : chanceList) {
            sum += value;
        }
        return sum;
    }
}
