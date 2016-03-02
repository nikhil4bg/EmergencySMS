package com.praxisgs.emergencysms.pages;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.praxisgs.emergencysms.base.BasePresenter;

/**
 * Created on ${<VARIABLE_DATE>}.
 */
public class SettingsPresenter implements BasePresenter {
    private ViewInterface mView;

    private SettingsPresenter(ViewInterface viewInterface) {
        this.mView = viewInterface;
    }

    public static SettingsPresenter newInstance(ViewInterface viewInterface) {
        return new SettingsPresenter(viewInterface);
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
