package com.github.sigute.demo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

import javax.inject.Inject;

public class Preferences {
    private static final String LAST_REFRESHED = "LAST_REFRESHED";
    @NonNull
    private SharedPreferences preferences;

    @Inject
    public Preferences(@NonNull Context context) {
        preferences = context.getSharedPreferences("com.github.sigute.demo.utils.default_preferences", Context.MODE_PRIVATE);
    }

    @Nullable
    public Date getLastRefreshedDate() {
        long lastRefreshedMillis = preferences.getLong(LAST_REFRESHED, -1);
        if (lastRefreshedMillis == -1) {
            return null;
        }

        return new Date(lastRefreshedMillis);
    }

    public void setRefreshed() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(LAST_REFRESHED, new Date().getTime());
        editor.apply();
    }
}
