package com.praxisgs.emergencysms.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.controllers.AppNavigationController;
import com.praxisgs.emergencysms.controllers.AppNavigationControllerInterface;
import com.praxisgs.emergencysms.utils.AppNavigationEnum;

public class EmergencySMSActivity extends BaseActivity implements AppNavigationControllerInterface {

    private AppNavigationController appNavigationController;

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
        appNavigationController.destroy();
    }

    private void initialiseControllers() {
        appNavigationController = new AppNavigationController(this);
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
}
