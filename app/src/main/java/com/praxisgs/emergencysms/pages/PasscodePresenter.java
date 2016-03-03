package com.praxisgs.emergencysms.pages;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.praxisgs.emergencysms.base.BasePresenter;
import com.praxisgs.emergencysms.eventbus.AppNavigationEvents;
import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.model.EmergencySMSModel;
import com.praxisgs.emergencysms.model.PassCodeModel;


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
        PassCodeModel passCodeModel = EmergencySMSModel.getInstance().getPassCodeModel();
        if(passCodeModel !=null && passCodeModel.getPassCode() !=null && !passCodeModel.getPassCode().isEmpty())
            return true;
        return false;
    }

    public void passCodeEnterOKBtnClicked(String passcode) {
        PassCodeModel passCodeModel = EmergencySMSModel.getInstance().getPassCodeModel();
        if(passCodeModel !=null && passCodeModel.getPassCode() !=null && !passCodeModel.getPassCode().isEmpty()){
            String storedPasscode = passCodeModel.getPassCode();
            if(passcode.equals(storedPasscode)){
                mView.passcodeMatchSuccessfull();
                EmergencySMSEventBus.post(new AppNavigationEvents.EventShowSettingsPage());
            }else{
                //TODO Show error
            }
        }

    }

    public void createPassCodeBtnClicked(String createPassCode, String recreatePassCode) {
        if(createPassCode.equals(recreatePassCode)){
            PassCodeModel passCodeModel = new PassCodeModel();
            passCodeModel.setPassCode(createPassCode);
            EmergencySMSModel.getInstance().setPassCodeModel(passCodeModel);
            EmergencySMSModel.getInstance().save();
            mView.passCodeCreate();
        }else{
            //TODO show Error
        }

    }

    public interface ViewInterface {
        Context getAppContext();

        void passCodeCreate();

        void passcodeMatchSuccessfull();
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
