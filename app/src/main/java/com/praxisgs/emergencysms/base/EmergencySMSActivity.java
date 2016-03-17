package com.praxisgs.emergencysms.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.praxisgs.emergencysms.controllers.AppNavigationController;
import com.praxisgs.emergencysms.controllers.AppNavigationControllerInterface;
import com.praxisgs.emergencysms.controllers.PermissionController;
import com.praxisgs.emergencysms.controllers.PermissionControllerInterface;
import com.praxisgs.emergencysms.controllers.ServiceController;
import com.praxisgs.emergencysms.controllers.ServiceControllerInterface;
import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.eventbus.PermissionEvents;
import com.praxisgs.emergencysms.services.EmergencySMSService;
import com.praxisgs.emergencysms.utils.AppNavigationEnum;
import com.praxisgs.emergencysms.utils.Constants;

import java.util.HashMap;

public class EmergencySMSActivity extends BaseActivity implements AppNavigationControllerInterface, ServiceControllerInterface, PermissionControllerInterface {

    private AppNavigationController mAppNavigationController;
    private ServiceController mServiceController;

    private boolean requestedForPermission = false;
    private PermissionController mPermissionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!requestedForPermission) {
            initialiseControllers();
            showPassCodePage();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppNavigationController.destroy();
        mServiceController.destroy();
        mPermissionController.destroy();
        mAppNavigationController = null;
        mServiceController = null;
        mPermissionController = null;
    }

    private void initialiseControllers() {
        mAppNavigationController = new AppNavigationController(this);
        mServiceController = new ServiceController(this);
        mPermissionController = new PermissionController(this);
    }


    /**
     * Show PassCode Page
     */
    @Override
    public void showPassCodePage() {
        showFragment(AppNavigationEnum.PASSCODE.getFragmentTag(), null, AppNavigationEnum.PASSCODE.getTitle(), false);
    }


    @Override
    public void showPreviousPage() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    /**
     * Show Settings Page
     */
    @Override
    public void showSettingsPage() {
        showFragment(AppNavigationEnum.SETTINGS.getFragmentTag(), null, AppNavigationEnum.SETTINGS.getTitle(), true);
    }

    /**
     * Show Contact page
     */
    @Override
    public void showContactsPage() {
//        if (userHasPermission(Manifest.permission.READ_CONTACTS)) {
            showDialogFragment(AppNavigationEnum.CONTACTS.getFragmentTag(), null);
//        } else {
//            requestPermission(Manifest.permission.READ_CONTACTS, R.string.read_permission_message);
//        }
    }

//    private void requestPermission(String permission, int messageRid) {
//        requestedForPermission = true;
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
//            showInformation(messageRid);
//        } else {
//            ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSION_READ_CONTACT_REQUEST_CODE);
//        }
//    }


    @Override
    public void showHelpPage() {
        showFragment(AppNavigationEnum.HELP.getFragmentTag(), null, AppNavigationEnum.HELP.getTitle(), true);
    }

    @Override
    public void showAboutPage() {
        showDialogFragment(AppNavigationEnum.ABOUT.getFragmentTag(), null);
    }

    /**
     * Start Emergency service
     */
    @Override
    public void startEmergencySMSService() {
        startService(new Intent(this, EmergencySMSService.class));
    }

    /**
     * Start Emergency service
     */
    @Override
    public void stopEmergencySMSService() {
        stopService(new Intent(this, EmergencySMSService.class));
    }


    /**
     *
     * @param permissions
     * @return
     */
    @Override
    public HashMap<String, Boolean> checkForUserPermission(String[] permissions) {
        HashMap<String,Boolean> results  = new HashMap();
        for(String permission:permissions){
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED){
                results.put(permission,true);}else{
                results.put(permission,false);
            }
        }
        return results;
    }

    /**
     * request for permission
     *
     * @param permission
     */
    @Override
    public void requestUserPermission(String permission, int messageRid,int requestCode) {
        requestedForPermission = true;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            showInformation(messageRid);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case Constants.PERMISSION_READ_CONTACT_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            PermissionEvents.EventReadContactsPermissionStatusAfterRequest resultEvent= new PermissionEvents.EventReadContactsPermissionStatusAfterRequest(true);
                            EmergencySMSEventBus.post(resultEvent);
                            requestedForPermission = false;
                        }
                    }, 200);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            PermissionEvents.EventReadContactsPermissionStatusAfterRequest resultEvent= new PermissionEvents.EventReadContactsPermissionStatusAfterRequest(false);
                            EmergencySMSEventBus.post(resultEvent);
                            requestedForPermission = false;
                        }
                    }, 200);
                }
                break;
        }
    }
}
