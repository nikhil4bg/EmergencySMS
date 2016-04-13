package com.praxisgs.emergencysms.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.praxisgs.emergencysms.model.EmergencySMSModel;
import com.praxisgs.emergencysms.model.SettingModel;
import com.praxisgs.emergencysms.receivers.EmergencySMSReceiver;
import com.praxisgs.emergencysms.utils.Constants;

public class EmergencySMSService extends Service {
    private SettingModel mSettings;
    private EmergencySMSReceiver mEmergencySMSReceiver;
    private Gson gson;

    public EmergencySMSService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        load();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);

        mEmergencySMSReceiver = new EmergencySMSReceiver(mSettings);
        registerReceiver(mEmergencySMSReceiver, filter);

    }


    private void load() {

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARE_PREF_NAME, Context.MODE_PRIVATE);
        String loadedModelString = sharedPreferences.getString(Constants.SHARE_PREF_NAME, null);
        setData(loadedModelString);
    }

    private void setData(String modelStr) {
        if (modelStr != null) {
            EmergencySMSModel model = gson.fromJson(modelStr, EmergencySMSModel.class);
            setSettings(model.getSettingModel());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mEmergencySMSReceiver != null)
        {
            unregisterReceiver(mEmergencySMSReceiver);
            mEmergencySMSReceiver = null;
        }
    }


    private void setSettings(SettingModel mSettings) {
        this.mSettings = mSettings;
    }
}
