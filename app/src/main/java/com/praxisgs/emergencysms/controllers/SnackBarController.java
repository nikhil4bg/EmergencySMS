package com.praxisgs.emergencysms.controllers;

import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.eventbus.SnackBarEvents;

/**
 * Created on 10/03/2016.
 */
public class SnackBarController {

    private final String TAG = SnackBarController.class.getName();
    private SnackBarControllerInterface mImplementer;

    public SnackBarController(SnackBarControllerInterface implementer) {
        this.mImplementer = implementer;
        EmergencySMSEventBus.register(this);
    }

    public void onEvent(SnackBarEvents.EventInformation event){
        mImplementer.showInformation(event.getMessageId(),event.getParameters());
    }

    public void onEvent(SnackBarEvents.EventError event){
        mImplementer.showError(event.getMessageId(),event.getParameters());
    }

    public void destroy() {
        mImplementer = null;
        EmergencySMSEventBus.unregister(this);
    }

}
