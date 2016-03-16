package com.praxisgs.emergencysms.eventbus;

import com.praxisgs.emergencysms.utils.Constants;

/**
 * Created on 16/03/2016.
 */
public class PermissionEvents {

    public static class CheckPermission {
        private final String mPermission;

        public CheckPermission(String permission) {
            this.mPermission = permission;
        }

        public String getPermission() {
            return mPermission;
        }
    }

    public static class PermissionStatus {
        private boolean mPermissionStatus;
        private String mPermissionRequested;

        public boolean isPermissionStatus() {
            return mPermissionStatus;
        }

        public void setPermissionStatus(boolean mPermissionStatus) {
            this.mPermissionStatus = mPermissionStatus;
        }

        public void setPermissionRequested(String permissionRequested) {
            this.mPermissionRequested = permissionRequested;
        }

        public String getPermissionRequested() {
            return mPermissionRequested;
        }

    }

    public static class RequestPermission {
        private final String mPermission;
        private final int mMessageId;
        private final int mRequestCode;

        public RequestPermission(String permission, int messageId, int requestCode) {
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
}
