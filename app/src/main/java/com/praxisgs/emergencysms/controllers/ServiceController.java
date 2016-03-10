package com.praxisgs.emergencysms.controllers;

import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.eventbus.ServiceEvents;

/**
 * Created on 04/03/2016.
 */
public class ServiceController {

    private final String TAG = AppNavigationController.class.getName();
    private ServiceControllerInterface mImplementer;

    public ServiceController(ServiceControllerInterface implementer) {
        this.mImplementer = implementer;
        EmergencySMSEventBus.register(this);
    }


    public void onEvent(ServiceEvents.EventStartEmergencySMSService event) {
        mImplementer.startEmergencySMSService();
    }


    public void onEvent(ServiceEvents.EventStopEmergencySMSService event) {
        mImplementer.stopEmergencySMSService();
    }

    public void destroy() {
        mImplementer = null;
        EmergencySMSEventBus.unregister(this);
    }
}
