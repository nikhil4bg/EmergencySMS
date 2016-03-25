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
import com.praxisgs.emergencysms.model.EmergencySMSModel;
import com.praxisgs.emergencysms.model.PassCodeModel;

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

    public interface ViewInterface {
        Context getAppContext();

        FragmentActivity getActivity();

        void dismiss();
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

    public void changePassCodeClicked(String oldPassCode, String newPassCode, String confirmNewPassCode) {
        //Check if any of the fields are empty
        if (oldPassCode.isEmpty()) {
            EmergencySMSEventBus.post(new SnackBarEvents.EventError(R.string.old_passcode_empty_error));
        } else if (newPassCode.isEmpty() || confirmNewPassCode.isEmpty()) {
            EmergencySMSEventBus.post(new SnackBarEvents.EventError(R.string.new_passcode_empty_error));
        } else //check if old and new pass code are the same
            if (oldPassCode.equalsIgnoreCase(newPassCode)) {
                EmergencySMSEventBus.post(new SnackBarEvents.EventError(R.string.new_old_passcode_same_error));
            } else //check if new and confirm pass code are the same
                if (!newPassCode.equalsIgnoreCase(confirmNewPassCode)) {
                    EmergencySMSEventBus.post(new SnackBarEvents.EventError(R.string.new_passcode_missmatch_error));
                } else //check if old pass code matches the stored pass code
                    if (EmergencySMSModel.getInstance().getPassCodeModel() != null && !EmergencySMSModel.getInstance().getPassCodeModel().getPassCode().equalsIgnoreCase(oldPassCode)) {
                        EmergencySMSEventBus.post(new SnackBarEvents.EventError(R.string.old_passcode_missmatch_error));
                    }else{
                        PassCodeModel passCodeModel = new PassCodeModel();
                        passCodeModel.setPassCode(newPassCode);
                        EmergencySMSModel.getInstance().setPassCodeModel(passCodeModel);
                        EmergencySMSModel.getInstance().save();
                        mView.dismiss();
                        EmergencySMSEventBus.post(new SnackBarEvents.EventInformation(R.string.passcode_changed_confirmation));
                    }
    }

}
