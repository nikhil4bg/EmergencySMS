package com.praxisgs.emergencysms.controllers;

import android.Manifest;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.eventbus.PermissionEvents;

/**
 * Created on 04/03/2016.
 */
public class PermissionController {

    private final String TAG = AppNavigationController.class.getName();
    private PermissionControllerInterface mImplementer;

    public PermissionController(PermissionControllerInterface implementer) {
        this.mImplementer = implementer;
        EmergencySMSEventBus.register(this);
    }


    public void onEvent(PermissionEvents.CheckPermission event) {
        PermissionEvents.PermissionStatus resultEvent = new PermissionEvents.PermissionStatus();
        boolean result = mImplementer.checkForUserPermission(event.getPermission());
        resultEvent.setPermissionStatus(result);
        resultEvent.setPermissionRequested(event.getPermission());
        EmergencySMSEventBus.post(resultEvent);
    }

    public void onEvent(PermissionEvents.RequestPermission event){
        String requestedPermission = event.getPermission();
        mImplementer.requestUserPermission(requestedPermission,event.getMessageId(),event.getRequestCode());
    }

    public void destroy() {
        mImplementer = null;
        EmergencySMSEventBus.unregister(this);
    }


}
