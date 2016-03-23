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

     public void onEvent(AppNavigationEvents.EventShowHelpPage event){
         mImplementer.showHelpPage();
     }

    public void onEvent(AppNavigationEvents.EventShowAboutPage event){
        mImplementer.showAboutPage();
    }

    public void onEvent( AppNavigationEvents.EventHideKeyboard event){
        mImplementer.hideKeyboard();
    }

    public void onEvent(AppNavigationEvents.EventShowResetPage event){
        mImplementer.showResetPage();
    }

    public void destroy() {
        mImplementer = null;
        EmergencySMSEventBus.unregister(this);
    }


}
