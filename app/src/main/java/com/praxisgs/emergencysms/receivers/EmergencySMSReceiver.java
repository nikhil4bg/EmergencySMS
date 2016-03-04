package com.praxisgs.emergencysms.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

import com.praxisgs.emergencysms.model.SettingModel;

public class EmergencySMSReceiver extends BroadcastReceiver {
    private static int mCountPowerButtonClick = 0;
    private final SettingModel mSettings;

    public EmergencySMSReceiver(SettingModel settings) {
        this.mSettings = settings;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.e("In on receive", "@@@@@@@@@@@@ In Method:  ACTION_SCREEN_OFF");
            mCountPowerButtonClick++;
            sendSMSIfConfigured();
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.e("In on receive", "@@@@@@@@@@@@ In Method:  ACTION_SCREEN_ON");
            mCountPowerButtonClick++;
            sendSMSIfConfigured();

        } else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            Log.e("In on receive", "@@@@@@@@@@@@ In Method:  ACTION_USER_PRESENT");

        }
    }

    private void sendSMSIfConfigured() {
        if (mCountPowerButtonClick > 2) {
            mCountPowerButtonClick = 0;

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(mSettings.getContactModels().get(0).getMobileNumber(), null, mSettings.getMessage(), null, null);
        }
    }
}
