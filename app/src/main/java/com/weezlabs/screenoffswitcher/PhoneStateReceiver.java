package com.weezlabs.screenoffswitcher;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.weezlabs.screenoffswitcher.util.Utils;

import java.util.Timer;
import java.util.TimerTask;


public class PhoneStateReceiver extends WakefulBroadcastReceiver {
    private static final String LOG_TAG = PhoneStateReceiver.class.getSimpleName();

    public PhoneStateReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Utils.isPhoneStateReceiverWork(context)) {
            return;
        }

        final DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        final ComponentName componentName = new ComponentName(context, AdminReceiver.class);

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            Log.i(LOG_TAG, "Phone is ringing");

            lockScreen(devicePolicyManager, componentName);

        }
        if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            Log.i(LOG_TAG, "Call received");
        }
        if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            Log.i(LOG_TAG, "Phone is Idle");

        }
    }

    private void lockScreen(final DevicePolicyManager devicePolicyManager, final ComponentName componentName) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (devicePolicyManager.isAdminActive(componentName)) {
                    devicePolicyManager.lockNow();
                }
            }
        };
        timer.schedule(timerTask, 100);
    }

}
