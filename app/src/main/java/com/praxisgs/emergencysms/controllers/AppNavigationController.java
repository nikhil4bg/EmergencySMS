package com.praxisgs.emergencysms.controllers;

import com.praxisgs.emergencysms.eventbus.AppNavigationEvents;
import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;


/**
 * Created on 04/02/2016.
 */
public class AppNavigationController {

    private final String TAG = AppNavigationController.class.getName();
    private final AppNavigationControllerInterface implementer;

    public AppNavigationController(AppNavigationControllerInterface implementer) {
        this.implementer = implementer;
        EmergencySMSEventBus.register(this);
    }


    public void onEvent(AppNavigationEvents.EventShowPassCodePage event) {
        implementer.showPassCodePage();
    }


    public void onEvent(AppNavigationEvents.EventShowPreviousPage event){
        implementer.showPreviousPage();
    }

    public void onEvent(AppNavigationEvents.EventShowSettingsPage event){
        implementer.showSettingsPage();
    }

    public void destroy() {
        EmergencySMSEventBus.unregister(this);
    }


}
