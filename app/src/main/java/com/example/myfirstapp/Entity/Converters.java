package com.example.myfirstapp.Entity;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public static int[] fromString(String str) {
        String[] strArr = str.split(",");
        int[] intArr = new int[12];
        for (int i = 0; i < strArr.length; i++) {
            intArr[i] = Integer.parseInt(strArr[i]);
        }
        return intArr;
    }

    @TypeConverter
    public static String fromIntArray(int[] intArr) {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (int i = 0; i < intArr.length; i++) {
            sb.append(prefix).append(intArr[i]);
            prefix = ",";
        }
        return sb.toString();
    }
}
