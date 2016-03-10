package com.praxisgs.emergencysms.controllers;

import com.praxisgs.emergencysms.eventbus.AppNavigationEvents;
import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;


/**
 * Created on 04/02/2016.
 */
public class AppNavigationController {

    private final String TAG = AppNavigationController.class.getName();
    private AppNavigationControllerInterface mImplementer;

    public AppNavigationController(AppNavigationControllerInterface implementer) {
        this.mImplementer = implementer;
        EmergencySMSEventBus.register(this);
    }


    public void onEvent(AppNavigationEvents.EventShowPassCodePage event) {
        mImplementer.showPassCodePage();
    }


    public void onEvent(AppNavigationEvents.EventShowPreviousPage event) {
        mImplementer.showPreviousPage();
    }

    public void onEvent(AppNavigationEvents.EventShowSettingsPage event) {
        mImplementer.showSettingsPage();
    }

    public void onEvent(AppNavigationEvents.EventShowContactPage event) {
        mImplementer.showContactsPage();
    }

    public void destroy() {
        mImplementer = null;
        EmergencySMSEventBus.unregister(this);
    }


}
