package com.praxisgs.emergencysms.base;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.model.EmergencySMSModel;
import com.praxisgs.emergencysms.utils.AppUtils;
import io.fabric.sdk.android.Fabric;


/**
 * Created by British Gas on 26/01/2016.
 */
public class EmergencySMSApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        EmergencySMSModel.initialise(this);
        EmergencySMSEventBus.initialise();
        AppUtils.initialise(this);
    }
}
