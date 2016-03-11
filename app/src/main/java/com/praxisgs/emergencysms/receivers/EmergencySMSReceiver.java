package com.praxisgs.emergencysms.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import com.praxisgs.emergencysms.model.SettingModel;

public class EmergencySMSReceiver extends BroadcastReceiver {
    //    private static final long MIN_TIME_BW_UPDATES = 10;
//    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 10.0f;
    private static int mCountPowerButtonClick = 0;
    private SettingModel mSettings;
    private long mPreviousTimeStamp;
    private final int mRequiredInterval = 1000;

    public EmergencySMSReceiver() {
    }

    public EmergencySMSReceiver(SettingModel settings) {
        this.mSettings = settings;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            powerButtonTriggered(context, System.currentTimeMillis());
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            powerButtonTriggered(context, System.currentTimeMillis());
        }
    }

    private void powerButtonTriggered(Context context, long currentTimeStamp) {
        if (mPreviousTimeStamp == 0) {
            mPreviousTimeStamp = currentTimeStamp;
        }
        if (currentTimeStamp - mPreviousTimeStamp <= mRequiredInterval) {
            mCountPowerButtonClick++;
            mPreviousTimeStamp = currentTimeStamp;
        } else {
            mPreviousTimeStamp = 0;
            mCountPowerButtonClick = 0;
        }
        if (mCountPowerButtonClick > 2) {
            mCountPowerButtonClick = 0;

//            LocationManager locationManager = (LocationManager)
//                    context.getSystemService(Context.LOCATION_SERVICE);
//
//            // getting GPS status
//            boolean isGPSEnabled = locationManager
//                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//            // getting network status
//            boolean isNetworkEnabled = locationManager
//                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//            if (!isGPSEnabled && !isNetworkEnabled) {
//                // no network provider is enabled
//            } else {
//                boolean canGetLocation = true;
//                Location location =null;
//                double latitude;
//                double longitude;
//                if (isNetworkEnabled) {
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//                    if (locationManager != null) {
//                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                        if (location != null) {
//                            Log.d("activity", "LOC by Network");
//                            latitude = location.getLatitude();
//                            longitude = location.getLongitude();
//                        }
//                    }
//                }
//
//                if (isGPSEnabled) {
//                    if (location == null) {
//                        locationManager.requestLocationUpdates(
//                                LocationManager.GPS_PROVIDER,
//                                MIN_TIME_BW_UPDATES,
//                                MIN_DISTANCE_CHANGE_FOR_UPDATES, null);
//                        Log.d("activity", "RLOC: GPS Enabled");
//                    }
//                    if (locationManager != null) {
//                        location = locationManager
//                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                        if (location != null) {
//                            Log.d("activity", "RLOC: loc by GPS");
//
//                            latitude = location.getLatitude();
//                            longitude = location.getLongitude();
//                        }
//                    }
//                }
//            }

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(mSettings.getContactModels().getMobileNumber(), null, mSettings.getMessage(), null, null);
        }
    }

//    private Boolean displayGpsStatus(Context context) {
//        ContentResolver contentResolver = context.getContentResolver();
//        boolean gpsStatus = Settings.Secure
//                .isLocationProviderEnabled(contentResolver,
//                        LocationManager.GPS_PROVIDER);
//        if (gpsStatus) {
//            return true;
//        } else {
//            return false;
//        }
//    }

}
