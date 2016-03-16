package com.praxisgs.emergencysms.controllers;

/**
 * Created on 04/03/2016.
 */
public interface PermissionControllerInterface {

    /**
     * @param permission
     * @return true if permission granted
     */
    boolean checkForUserPermission(String permission);

    /**
     * request for permission
     *
     * @param permission
     * @param messageRid
     */
    void requestUserPermission(String permission,int messageRid,int requestCode);

}
