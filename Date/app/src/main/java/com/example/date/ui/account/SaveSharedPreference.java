package com.example.date.ui.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;

import com.example.date.MainActivity;
import com.example.date.R;

public class SaveSharedPreference {
    static final String PREF_USER_ID = "userID";
    static final String PREF_LEVEL = "level";
    static final String PREF_CITY = "city";
    static final String PREF_NAME = "name";
    static final String PREF_EMAIL = "email";
    static final String PREF_IMG = "img";

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setUserID(Context context, String userID) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_ID, userID);
        editor.apply();
    }

    public static String getUserID(Context context) {
        return getSharedPreferences(context).getString(PREF_USER_ID, "");
    }

    public static void clearUserName(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
    }
    public static void setLevel(Context context, String level) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_LEVEL, level);
        editor.apply();
    }

    public static String getLevel(Context context) {
        return getSharedPreferences(context).getString(PREF_LEVEL, "");
    }
    public static void setCity(Context context, String city) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_CITY, city);
        editor.apply();
    }

    public static String getCity(Context context) {
        return getSharedPreferences(context).getString(PREF_CITY, "");
    }
    public static void setName(Context context, String name) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_NAME, name);
        editor.apply();
    }
    public static String getName(Context context) {
        return getSharedPreferences(context).getString(PREF_NAME, "");
    }
    public static void setEmail(Context context, String email) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_EMAIL, email);
        editor.apply();
    }
    public static String getEmail(Context context) {
        return getSharedPreferences(context).getString(PREF_EMAIL, "");
    }
    public static void setImg(Context context, String img) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_IMG, img);
        editor.apply();
    }
    public static String getImg(Context context) {
        return getSharedPreferences(context).getString(PREF_IMG, "");
    }


}
