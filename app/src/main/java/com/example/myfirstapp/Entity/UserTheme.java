package com.example.myfirstapp.Entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.myfirstapp.Activity.SettingsActivity;
import com.example.myfirstapp.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Entity(tableName = "userTheme_table")
public class UserTheme {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "head")
    private int head;

    @ColumnInfo(name = "neck")
    private int neck;

    @ColumnInfo(name = "arm_left")
    private int arm_left;

    @ColumnInfo(name = "arm_right")
    private int arm_right;

    @ColumnInfo(name = "hand_left")
    private int hand_left;

    @ColumnInfo(name = "hand_right")
    private int hand_right;

    @ColumnInfo(name = "torso")
    private int torso;

    @ColumnInfo(name = "stomach")
    private int stomach;

    @ColumnInfo(name = "thigh_left")
    private int thigh_left;

    @ColumnInfo(name = "thigh_right")
    private int thigh_right;

    @ColumnInfo(name = "foot_left")
    private int foot_left;

    @ColumnInfo(name = "foot_right")
    private int foot_right;

    @Ignore
    public UserTheme(@NonNull String title, int head, int neck, int arm_left, int arm_right, int hand_left, int hand_right, int torso, int stomach, int thigh_left, int thigh_right, int foot_left, int foot_right) {
        this.title = title;
        this.head = head;
        this.neck = neck;
        this.arm_left = arm_left;
        this.arm_right = arm_right;
        this.hand_left = hand_left;
        this.hand_right = hand_right;
        this.torso = torso;
        this.stomach = stomach;
        this.thigh_left = thigh_left;
        this.thigh_right = thigh_right;
        this.foot_left = foot_left;
        this.foot_right = foot_right;
    }

    @Ignore
    public UserTheme(@NonNull String title, @NonNull HashMap<String, Integer> chances) {
        this.title = title;

        Iterator it = chances.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String name = (String) pair.getKey();
            switch (name) {
                case "head":
                    this.head = (int) pair.getValue();
                    break;
                case "neck":
                    this.neck = (int) pair.getValue();
                    break;
                case "torso":
                    this.torso = (int) pair.getValue();
                    break;
                case "stomach":
                    this.stomach = (int) pair.getValue();
                    break;
                case "arm_left":
                    this.arm_left = (int) pair.getValue();
                    break;
                case "hand_left":
                    this.hand_left = (int) pair.getValue();
                    break;
                case "arm_right":
                    this.arm_right = (int) pair.getValue();
                    break;
                case "hand_right":
                    this.hand_right = (int) pair.getValue();
                    break;
                case "thigh_left":
                    this.thigh_left = (int) pair.getValue();
                    break;
                case "foot_left":
                    this.foot_left = (int) pair.getValue();
                    break;
                case "thigh_right":
                    this.thigh_right = (int) pair.getValue();
                    break;
                case "foot_right":
                    this.foot_right = (int) pair.getValue();
                    break;
            }
        }
    }

    public UserTheme(@NonNull String title) {
        this.title = title;
        this.head = 100;
        this.neck = 100;
        this.arm_left = 100;
        this.arm_right = 100;
        this.hand_left = 100;
        this.hand_right = 100;
        this.torso = 100;
        this.stomach = 100;
        this.thigh_left = 100;
        this.thigh_right = 100;
        this.foot_left = 100;
        this.foot_right = 100;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public int getHead() {
        return head;
    }

    public int getNeck() {
        return neck;
    }

    public int getArm_left() {
        return arm_left;
    }

    public int getArm_right() {
        return arm_right;
    }

    public int getHand_left() {
        return hand_left;
    }

    public int getHand_right() {
        return hand_right;
    }

    public int getTorso() {
        return torso;
    }

    public int getStomach() {
        return stomach;
    }

    public int getThigh_left() {
        return thigh_left;
    }

    public int getThigh_right() {
        return thigh_right;
    }

    public int getFoot_left() {
        return foot_left;
    }

    public int getFoot_right() {
        return foot_right;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public void setNeck(int neck) {
        this.neck = neck;
    }

    public void setArm_left(int arm_left) {
        this.arm_left = arm_left;
    }

    public void setArm_right(int arm_right) {
        this.arm_right = arm_right;
    }

    public void setHand_left(int hand_left) {
        this.hand_left = hand_left;
    }

    public void setHand_right(int hand_right) {
        this.hand_right = hand_right;
    }

    public void setTorso(int torso) {
        this.torso = torso;
    }

    public void setStomach(int stomach) {
        this.stomach = stomach;
    }

    public void setThigh_left(int thigh_left) {
        this.thigh_left = thigh_left;
    }

    public void setThigh_right(int thigh_right) {
        this.thigh_right = thigh_right;
    }

    public void setFoot_left(int foot_left) {
        this.foot_left = foot_left;
    }

    public void setFoot_right(int foot_right) {
        this.foot_right = foot_right;
    }

    public HashMap<String, Integer> getHashMap() {

        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("head", getHead());
        hashMap.put("neck", getNeck());
        hashMap.put("arm_left", getArm_left());
        hashMap.put("arm_right", getArm_right());
        hashMap.put("hand_left", getHand_left());
        hashMap.put("hand_right", getHand_right());
        hashMap.put("torso", getTorso());
        hashMap.put("stomach", getStomach());
        hashMap.put("thigh_left", getThigh_left());
        hashMap.put("thigh_right", getThigh_right());
        hashMap.put("foot_left", getFoot_left());
        hashMap.put("foot_right", getFoot_right());

        return hashMap;

    }
}
