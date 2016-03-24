package com.praxisgs.emergencysms.pages;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.base.BasePresenter;
import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.eventbus.SnackBarEvents;

/**
 * Created on ${<VARIABLE_DATE>}.
 */
public class ChangePassCodePresenter implements BasePresenter {
    private ViewInterface mView;

    private ChangePassCodePresenter(ViewInterface viewInterface) {
        this.mView = viewInterface;
    }

    public static ChangePassCodePresenter newInstance(ViewInterface viewInterface) {
        return new ChangePassCodePresenter(viewInterface);
    }

    public void changePassCodeClicked(String oldPassCode, String newPassCode, String confirmNewPassCode) {
        //Check if any of the fields are empty
        if(oldPassCode.isEmpty()){
            EmergencySMSEventBus.post(new SnackBarEvents.EventError(R.string.old_passcode_empty_error));
        }else if(newPassCode.isEmpty() || confirmNewPassCode.isEmpty()){
            EmergencySMSEventBus.post(new SnackBarEvents.EventError(R.string.new_passcode_empty_error));
        }
        //check if old pass code matches the stored pass code
        //check if old and new pass code are the same
        //check if new and confirm pass code are the same
    }

    public interface ViewInterface {
        Context getAppContext();

        FragmentActivity getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public Context getAppContext() {
        return mView.getAppContext();
    }

    public String getApplicationName() {
        return getAppContext().getResources().getString(R.string.app_name);
    }

    public String getVersionNumber() {
        PackageInfo pInfo;
        String versionName;
        try {
            pInfo = getAppContext().getPackageManager()
                    .getPackageInfo((getAppContext()).getPackageName(), 0);
            versionName = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = "1.00";
            e.printStackTrace();
        }
        return versionName;
    }

}
