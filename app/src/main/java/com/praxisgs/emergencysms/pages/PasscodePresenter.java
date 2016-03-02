package com.praxisgs.emergencysms.pages;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.praxisgs.emergencysms.base.BasePresenter;
import com.praxisgs.emergencysms.eventbus.AppNavigationEvents;
import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.model.EmergencySMSModel;
import com.praxisgs.emergencysms.model.PassCodeEntity;


/**
 * Created on 04/02/2016.
 */
public class PasscodePresenter implements BasePresenter {
    private ViewInterface mView;

    private PasscodePresenter(ViewInterface viewInterface) {
        this.mView = viewInterface;
    }

    public static PasscodePresenter newInstance(ViewInterface viewInterface) {
        return new PasscodePresenter(viewInterface);
    }

    public boolean isPassCodeCreated() {
        PassCodeEntity passCodeEntity = EmergencySMSModel.getInstance().getPassCodeEntity();
        if(passCodeEntity !=null && passCodeEntity.getPassCode() !=null && !passCodeEntity.getPassCode().isEmpty())
            return true;
        return false;
    }

    public void passCodeEnterOKBtnClicked(String passcode) {
        PassCodeEntity passCodeEntity = EmergencySMSModel.getInstance().getPassCodeEntity();
        if(passCodeEntity !=null && passCodeEntity.getPassCode() !=null && !passCodeEntity.getPassCode().isEmpty()){
            String storedPasscode = passCodeEntity.getPassCode();
            if(passcode.equals(storedPasscode)){
                EmergencySMSEventBus.post(new AppNavigationEvents.EventShowSettingsPage());
            }else{
                //TODO Show error
            }
        }

    }

    public void createPassCodeBtnClicked(String createPassCode, String recreatePassCode) {
        if(createPassCode.equals(recreatePassCode)){
            PassCodeEntity passCodeEntity = new PassCodeEntity();
            passCodeEntity.setPassCode(createPassCode);
            EmergencySMSModel.getInstance().setPassCodeEntity(passCodeEntity);
            EmergencySMSModel.getInstance().save();
            mView.passCodeCreate();
        }else{
            //TODO show Error
        }

    }

    public interface ViewInterface {
        Context getAppContext();

        void passCodeCreate();
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
}
