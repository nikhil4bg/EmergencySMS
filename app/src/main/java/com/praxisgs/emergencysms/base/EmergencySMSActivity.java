package com.praxisgs.emergencysms.base;

import android.content.Intent;
import android.os.Bundle;

import com.praxisgs.emergencysms.controllers.AppNavigationController;
import com.praxisgs.emergencysms.controllers.AppNavigationControllerInterface;
import com.praxisgs.emergencysms.controllers.ServiceController;
import com.praxisgs.emergencysms.controllers.ServiceControllerInterface;
import com.praxisgs.emergencysms.services.EmergencySMSService;
import com.praxisgs.emergencysms.utils.AppNavigationEnum;

public class EmergencySMSActivity extends BaseActivity implements AppNavigationControllerInterface,ServiceControllerInterface {

    private AppNavigationController mAppNavigationController;
    private ServiceController mServiceController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initialiseControllers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showPassCodePage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppNavigationController.destroy();
        mServiceController.destroy();
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
     * Start Emergency service
     */
    @Override
    public void startEmergencySMSService() {
        startService(new Intent(getBaseContext(), EmergencySMSService.class));
    }

    /**
     * Start Emergency service
     */
    @Override
    public void stopEmergencySMSService() {
        stopService(new Intent(getBaseContext(), EmergencySMSService.class));
    }
}
