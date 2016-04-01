package com.praxisgs.emergencysms.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

import com.praxisgs.emergencysms.model.SettingModel;

public class EmergencySMSReceiver extends BroadcastReceiver {
    private static int mCountPowerButtonClick = 0;
    private SettingModel mSettings;
    private long mPreviousTimeStamp;
    private final int mRequiredInterval = 2000;

    public EmergencySMSReceiver() {
    }

    public EmergencySMSReceiver(SettingModel settings) {
        this.mSettings = settings;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF) ||
                intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            long currentTimeStamp = System.currentTimeMillis();
            Log.e("EmergencySMSReceiver", "@@@@@@@@@@@@ BEFORE (currentTimeStamp - mPreviousTimeStamp): " + (currentTimeStamp - mPreviousTimeStamp));
            if (currentTimeStamp - mPreviousTimeStamp > mRequiredInterval) {
                mCountPowerButtonClick = 0;
                mPreviousTimeStamp = 0;
            }
            powerButtonTriggered(context, currentTimeStamp);
        }
    }

    private void powerButtonTriggered(Context context, long currentTimeStamp) {
        Log.e("EmergencySMSReceiver", "@@@@@@@@@@@@ powerButtonTriggered called @@@@@@@@@@@@@@@@@@");
        if (mPreviousTimeStamp == 0) {
            Log.e("EmergencySMSReceiver", "@@@@@@@@@@@@ mPreviousTimeStamp is 0");
            mPreviousTimeStamp = currentTimeStamp;
        }
        Log.e("EmergencySMSReceiver", "@@@@@@@@@@@@ mCountPowerButtonClick before: " + mCountPowerButtonClick);
        Log.e("EmergencySMSReceiver", "@@@@@@@@@@@@ (currentTimeStamp - mPreviousTimeStamp): " + (currentTimeStamp - mPreviousTimeStamp));
        if (currentTimeStamp - mPreviousTimeStamp <= mRequiredInterval) {
            mCountPowerButtonClick++;
            Log.e("EmergencySMSReceiver", "@@@@@@@@@@@@ mCountPowerButtonClick incremented by 1: " + mCountPowerButtonClick);
            mPreviousTimeStamp = currentTimeStamp;
        } else {
            mCountPowerButtonClick = 1;
        }

        if (mCountPowerButtonClick >= 3) {
            mCountPowerButtonClick = 0;
            mPreviousTimeStamp = 0;

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(mSettings.getContactModels().getMobileNumber(), null, mSettings.getMessage(), null, null);
        }
        Log.e("EmergencySMSReceiver", "@@@@@@@@@@@@ mCountPowerButtonClick After: " + mCountPowerButtonClick);
    }

}
