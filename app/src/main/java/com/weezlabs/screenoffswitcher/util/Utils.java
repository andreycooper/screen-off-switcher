package com.weezlabs.screenoffswitcher.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Andrey Bondarenko on 09.06.15.
 */
public final class Utils {
    private static final String STATE_RECEIVER_KEY = "com.weezlabs.screenoffswitcher.STATE_RECEIVER";

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

}
