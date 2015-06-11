package com.weezlabs.screenoffswitcher.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public final class Utils {
    private static final String STATE_RECEIVER_KEY = "com.weezlabs.screenoffswitcher.STATE_RECEIVER";
    private static final String TIME_TO_LOCK_KEY = "com.weezlabs.screenoffswitcher.TIME_TO_LOCK_KEY";
    private static final String STATE_KEY = "com.weezlabs.screenoffswitcher.STATE";

    public static final int DEFAULT_TIME = 100;

    private Utils() {

    }

    public static void setPhoneStateReceiverWork(Context context, boolean isOn) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext()).edit();
        editor.putBoolean(STATE_RECEIVER_KEY, isOn);
        editor.apply();
    }

    public static boolean isPhoneStateReceiverWork(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        return prefs.getBoolean(STATE_RECEIVER_KEY, false);
    }

    public static void setTimeToLock(Context context, int time) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext()).edit();
        editor.putInt(TIME_TO_LOCK_KEY, time);
        editor.apply();
    }

    public static int getTimeToLock(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        return prefs.getInt(TIME_TO_LOCK_KEY, DEFAULT_TIME);
    }

    public static void setPhoneState(Context context, String state) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext()).edit();
        editor.putString(STATE_KEY, state);
        editor.apply();
    }

    public static String getPreviousState(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        return prefs.getString(STATE_KEY, null);
    }

}
