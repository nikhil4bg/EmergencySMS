package com.praxisgs.emergencysms.controllers;

import java.util.HashMap;

/**
 * Created on 04/03/2016.
 */
public interface PermissionControllerInterface {

    /**
     * @param permissions
     * @return Hash map of the permissions check for each persmission asked
     */
    HashMap<String,Boolean> checkForUserPermission(String[] permissions);

    /**
     * request for permission
     *
     * @param permission
     * @param messageRid
     */
    void requestUserPermission(String permission,int messageRid,int requestCode);

}
