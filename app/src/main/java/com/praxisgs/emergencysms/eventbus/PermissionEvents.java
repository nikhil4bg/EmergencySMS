package com.praxisgs.emergencysms.eventbus;

import com.praxisgs.emergencysms.utils.Constants;

import java.util.HashMap;

/**
 * Created on 16/03/2016.
 */
public class PermissionEvents {

    public static class EventCheckPermission {
        private final String[] mPermission;

        public EventCheckPermission(String[] permission) {
            this.mPermission = permission;
        }

        public String[] getPermission() {
            return mPermission;
        }
    }

    public static class EventPermissionBeforeRequestResults {
        private HashMap<String,Boolean> mPermissionStatus;
//        private String mPermissionRequested;

        public HashMap<String,Boolean> getPermissionStatus() {
            return mPermissionStatus;
        }

        public void setPermissionStatus(HashMap<String,Boolean> mPermissionStatus) {
            this.mPermissionStatus = mPermissionStatus;
        }

//        public void setPermissionRequested(String permissionRequested) {
//            this.mPermissionRequested = permissionRequested;
//        }
//
//        public String getPermissionRequested() {
//            return mPermissionRequested;
//        }

    }

    public static class EventReadContactsPermissionStatusAfterRequest {

        private final boolean mPermissionStatus;

        public EventReadContactsPermissionStatusAfterRequest(boolean status){
            this.mPermissionStatus = status;
        }

        public boolean getPermissionStatus() {
            return mPermissionStatus;
        }
    }

    public static class EventRequestPermission {
        private final String mPermission;
        private final int mMessageId;
        private final int mRequestCode;

        public EventRequestPermission(String permission, int messageId, int requestCode) {
            this.mPermission = permission;
            this.mMessageId = messageId;
            this.mRequestCode = requestCode;
        }

        public String getPermission() {
            return mPermission;
        }

        public int getMessageId() {
            return mMessageId;
        }

        public int getRequestCode() {
            return mRequestCode;
        }
    }

    public static class EventSendSMSPermissionStatusAfterRequest {

        private final boolean mPermissionStatus;

        public EventSendSMSPermissionStatusAfterRequest(boolean status){
            this.mPermissionStatus = status;
        }

        public boolean getPermissionStatus() {
            return mPermissionStatus;
        }
    }

    public static class EventReadContactPermissionGranted{}

    public static class EventReadContactPermissionDenied{}

    public static class EventSendSMSPermissionGranted{}

    public static class EventSendSMSPermissionDenied{}
}
