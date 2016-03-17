package com.praxisgs.emergencysms.controllers;

import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.eventbus.PermissionEvents;

import java.util.HashMap;

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


    public void onEvent(PermissionEvents.EventCheckPermission event) {
        PermissionEvents.EventPermissionBeforeRequestResults resultEvent = new PermissionEvents.EventPermissionBeforeRequestResults();
        HashMap<String, Boolean> result = mImplementer.checkForUserPermission(event.getPermission());
        resultEvent.setPermissionStatus(result);
//        resultEvent.setPermissionRequested(event.getPermission());
        EmergencySMSEventBus.post(resultEvent);
    }

    public void onEvent(PermissionEvents.EventReadContactsPermissionStatusAfterRequest event) {
        if (event.getPermissionStatus()) {
            EmergencySMSEventBus.post(new PermissionEvents.EventReadContactPermissionGranted());
        } else {
            EmergencySMSEventBus.post(new PermissionEvents.EventReadContactPermissionDenied());
        }
    }

    public void onEvent(PermissionEvents.EventSendSMSPermissionStatusAfterRequest event){
        if (event.getPermissionStatus()) {
            EmergencySMSEventBus.post(new PermissionEvents.EventSendSMSPermissionGranted());
        } else {
            EmergencySMSEventBus.post(new PermissionEvents.EventSendSMSPermissionDenied());
        }
    }

    public void onEvent(PermissionEvents.EventRequestPermission event) {
        String requestedPermission = event.getPermission();
        mImplementer.requestUserPermission(requestedPermission, event.getMessageId(), event.getRequestCode());
    }

    public void destroy() {
        mImplementer = null;
        EmergencySMSEventBus.unregister(this);
    }


}
