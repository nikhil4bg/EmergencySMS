package com.praxisgs.emergencysms.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.controllers.AppNavigationController;
import com.praxisgs.emergencysms.controllers.AppNavigationControllerInterface;
import com.praxisgs.emergencysms.controllers.ServiceController;
import com.praxisgs.emergencysms.controllers.ServiceControllerInterface;
import com.praxisgs.emergencysms.services.EmergencySMSService;
import com.praxisgs.emergencysms.utils.AppNavigationEnum;

public class EmergencySMSActivity extends BaseActivity implements AppNavigationControllerInterface, ServiceControllerInterface {

    private AppNavigationController mAppNavigationController;
    private ServiceController mServiceController;
    private final int PERMISSION_READ_CONTACT_REQUEST_CODE = 1;
    private boolean requestedForPermission = false;

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
        mAppNavigationController = null;
        mServiceController = null;
    }

    private void initialiseControllers() {
        mAppNavigationController = new AppNavigationController(this);
        mServiceController = new ServiceController(this);
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
        if (userHasPermission(Manifest.permission.READ_CONTACTS)) {
            showDialogFragment(AppNavigationEnum.CONTACTS.getFragmentTag(), null);
        } else {
            requestPermission(Manifest.permission.READ_CONTACTS, R.string.read_permission_message);
        }
    }

    private void requestPermission(String permission, int messageRid) {
        requestedForPermission = true;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            showInformation(messageRid);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSION_READ_CONTACT_REQUEST_CODE);
        }
    }

    private boolean userHasPermission(String permission) {
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        requestedForPermission = false;
        switch (requestCode) {
            case PERMISSION_READ_CONTACT_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showDialogFragment(AppNavigationEnum.CONTACTS.getFragmentTag(), null);
                } else {
                    showInformation(R.string.read_permission_message);
                }
                break;
        }
    }

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
}
