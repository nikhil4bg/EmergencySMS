package com.praxisgs.emergencysms.base;

import android.app.Application;

import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.model.EmergencySMSModel;
import com.praxisgs.emergencysms.utils.AppUtils;


/**
 * Created by British Gas on 26/01/2016.
 */
public class EmergencySMSApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EmergencySMSModel.initialise(this);
        EmergencySMSEventBus.initialise();
        AppUtils.initialise(this);
    }
}
