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
    private final int mRequiredInterval = 1000;

    public EmergencySMSReceiver(){}

    public EmergencySMSReceiver(SettingModel settings) {
        this.mSettings = settings;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.e("In on receive", "@@@@@@@@@@@@ In Method:  ACTION_SCREEN_OFF");
            powerButtonTriggered(System.currentTimeMillis());
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.e("In on receive", "@@@@@@@@@@@@ In Method:  ACTION_SCREEN_ON");
            powerButtonTriggered(System.currentTimeMillis());

        } else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            Log.e("In on receive", "@@@@@@@@@@@@ In Method:  ACTION_USER_PRESENT");

        }
    }

    private void powerButtonTriggered(long currentTimeStamp) {
        if(mPreviousTimeStamp == 0){
            mPreviousTimeStamp = currentTimeStamp;
        }
        Log.e("In on receive", "@@@@@@@@@@@@ currentTimeStamp - mPreviousTimeStamp = " + (currentTimeStamp - mPreviousTimeStamp));
        if(currentTimeStamp - mPreviousTimeStamp <= mRequiredInterval){
            mCountPowerButtonClick++;
            mPreviousTimeStamp = currentTimeStamp;
        }else{
            mPreviousTimeStamp = 0;
            mCountPowerButtonClick = 0;
        }
        if (mCountPowerButtonClick > 2) {
            mCountPowerButtonClick = 0;

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(mSettings.getContactModels().getMobileNumber(), null, mSettings.getMessage(), null, null);
        }
    }

}
