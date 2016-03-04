package com.praxisgs.emergencysms.services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

import com.praxisgs.emergencysms.model.EmergencySMSModel;
import com.praxisgs.emergencysms.model.SettingModel;
import com.praxisgs.emergencysms.receivers.EmergencySMSReceiver;

public class EmergencySMSService extends Service {
    private final SettingModel mSettings;
    private EmergencySMSReceiver mEmergencySMSReceiver;

    public EmergencySMSService() {
        this.mSettings = EmergencySMSModel.getInstance().getSettingModel();
    }

    public EmergencySMSService(SettingModel settings){
        this.mSettings = settings;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        if(mSettings!=null && mSettings.getContactModels().size()>0){
            Toast.makeText(this, "mSettings Mobile: " + mSettings.getContactModels().get(0).getMobileNumber(), Toast.LENGTH_LONG).show();
            Toast.makeText(this, "mSettings message: " + mSettings.getMessage(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Please select a contact before you use this service ", Toast.LENGTH_LONG).show();
            stopSelf();
        }


        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        mEmergencySMSReceiver = new EmergencySMSReceiver(mSettings);
        registerReceiver(mEmergencySMSReceiver, filter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        if (mEmergencySMSReceiver != null)
        {
            unregisterReceiver(mEmergencySMSReceiver);
            mEmergencySMSReceiver = null;
        }
    }


}
