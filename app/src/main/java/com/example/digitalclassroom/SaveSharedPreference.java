package com.example.digitalclassroom;

import android.content.Context;
import android.content.SharedPreferences;

import android.preference.PreferenceManager;


public class SaveSharedPreference {
    static final String PREF_USER_email= "email";
    static final String PREF_USER_pass= "password";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String email, String password)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_email, email);
        editor.putString(PREF_USER_pass, password);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_email, "");

    }


    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }
}
