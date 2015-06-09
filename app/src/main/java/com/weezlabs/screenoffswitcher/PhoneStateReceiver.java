package com.weezlabs.screenoffswitcher;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.weezlabs.screenoffswitcher.util.Utils;


public class PhoneStateReceiver extends WakefulBroadcastReceiver {
    private static final String LOG_TAG = PhoneStateReceiver.class.getSimpleName();

    public PhoneStateReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Utils.isPhoneStateReceiverWork(context)) {
            return;
        }

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            Log.i(LOG_TAG, "Phone is ringing");
        }
        if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            Log.i(LOG_TAG, "Call received");
        }
        if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            Log.i(LOG_TAG, "Phone is Idle");
        }
    }
}
