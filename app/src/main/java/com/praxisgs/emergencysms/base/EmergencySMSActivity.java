package com.praxisgs.emergencysms.base;

import android.content.Intent;
import android.os.Bundle;

import com.praxisgs.emergencysms.controllers.AppNavigationController;
import com.praxisgs.emergencysms.controllers.AppNavigationControllerInterface;
import com.praxisgs.emergencysms.controllers.ServiceController;
import com.praxisgs.emergencysms.controllers.ServiceControllerInterface;
import com.praxisgs.emergencysms.services.EmergencySMSService;
import com.praxisgs.emergencysms.utils.AppNavigationEnum;

public class EmergencySMSActivity extends BaseActivity implements AppNavigationControllerInterface, ServiceControllerInterface {

    private AppNavigationController mAppNavigationController;
    private ServiceController mServiceController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseControllers();
        showPassCodePage();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        showDialogFragment(AppNavigationEnum.CONTACTS.getFragmentTag(), null);
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
