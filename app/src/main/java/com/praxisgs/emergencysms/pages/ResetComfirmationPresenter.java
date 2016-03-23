package com.praxisgs.emergencysms.pages;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.base.BasePresenter;
import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.eventbus.EventDataChanged;
import com.praxisgs.emergencysms.eventbus.ServiceEvents;
import com.praxisgs.emergencysms.eventbus.SnackBarEvents;
import com.praxisgs.emergencysms.model.EmergencySMSModel;

/**
 * Created on ${<VARIABLE_DATE>}.
 */
public class ResetComfirmationPresenter implements BasePresenter {
    private ViewInterface mView;

    private ResetComfirmationPresenter(ViewInterface viewInterface) {
        this.mView = viewInterface;
    }

    public static ResetComfirmationPresenter newInstance(ViewInterface viewInterface) {
        return new ResetComfirmationPresenter(viewInterface);
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

    public void resetClicked() {
        EmergencySMSEventBus.post(new ServiceEvents.EventStopEmergencySMSService());
        EmergencySMSModel.getInstance().setSettingModel(null);
        EmergencySMSEventBus.post(new EventDataChanged());
        mView.dismiss();
        EmergencySMSEventBus.post(new SnackBarEvents.EventInformation(R.string.reset_done));
    }

}
