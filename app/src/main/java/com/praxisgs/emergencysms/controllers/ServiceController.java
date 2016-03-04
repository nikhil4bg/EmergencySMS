package com.praxisgs.emergencysms.controllers;

import com.praxisgs.emergencysms.eventbus.AppNavigationEvents;
import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.eventbus.ServiceEvents;

/**
 * Created on 04/03/2016.
 */
public class ServiceController {

    private final String TAG = AppNavigationController.class.getName();
    private final ServiceControllerInterface implementer;

    public ServiceController(ServiceControllerInterface implementer) {
        this.implementer = implementer;
        EmergencySMSEventBus.register(this);
    }


    public void onEvent(ServiceEvents.EventStartEmergencySMSService event) {
        implementer.startEmergencySMSService();
    }


    public void onEvent(ServiceEvents.EventStopEmergencySMSService event) {
        implementer.stopEmergencySMSService();
    }

    public void destroy() {
        EmergencySMSEventBus.unregister(this);
    }
}
