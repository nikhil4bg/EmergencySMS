package com.praxisgs.emergencysms.pages;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.base.BasePresenter;
import com.praxisgs.emergencysms.eventbus.AppNavigationEvents;
import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.eventbus.SnackBarEvents;
import com.praxisgs.emergencysms.model.EmergencySMSModel;
import com.praxisgs.emergencysms.model.PassCodeModel;


/**
 * Created on 04/02/2016.
 */
public class HelpPresenter implements BasePresenter {
    private ViewInterface mView;

    private HelpPresenter(ViewInterface viewInterface) {
        this.mView = viewInterface;
    }

    public static HelpPresenter newInstance(ViewInterface viewInterface) {
        return new HelpPresenter(viewInterface);
    }


    public interface ViewInterface {
        Context getAppContext();

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
