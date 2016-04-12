package com.praxisgs.emergencysms.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */
public class EmergencySMSIntentService extends IntentService {

    public EmergencySMSIntentService() {
        super("EmergencySMSIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if(bundle!=null){
                String mobileNumber = bundle.getString("mobile_number");
                String message = bundle.getString("message");
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(mobileNumber, null, message, null, null);
            }
        }
    }

}
