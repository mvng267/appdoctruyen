package com.example.appdoctruyen;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    private static final String PREF_NAME = "MyApp_Pref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_ID = "user_id";
    private static final String PREF_PHAN_QUYEN = "phan_quyen";
    private static final String PREF_DOC_ID = "doc_id";
    private static final String PREF_EMAIL = "email";
    private static final String KEY_LOGGED_IN = "logged_in";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PreferenceHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void setUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public void setLoggedIn(boolean loggedIn) {
        editor.putBoolean(KEY_LOGGED_IN, loggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false);
    }

    public void clearPreferences() {
        editor.clear();
        editor.apply();
    }

    public void setUserId(String userId) {
        editor.putString(KEY_USER_ID, userId);
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public int getPhanQuyen() {
        return sharedPreferences.getInt(PREF_PHAN_QUYEN, 0);
    }

    public void setPhanQuyen(int phanQuyen) {
        editor.putInt(PREF_PHAN_QUYEN, phanQuyen);
        editor.apply();
    }

    public String getDocId() {
        return sharedPreferences.getString(PREF_DOC_ID, null);
    }

    public void setDocId(String docId) {
        editor.putString(PREF_DOC_ID, docId);
        editor.apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(PREF_EMAIL, null);
    }

    public void setEmail(String email) {
        editor.putString(PREF_EMAIL, email);
        editor.apply();
    }

    public void clear() {
        editor.clear();
        editor.apply();
    }
}