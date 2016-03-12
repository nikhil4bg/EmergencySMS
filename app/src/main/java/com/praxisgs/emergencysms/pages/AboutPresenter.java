package com.praxisgs.emergencysms.pages;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.praxisgs.emergencysms.base.BasePresenter;


/**
 * Created on 04/02/2016.
 */
public class AboutPresenter implements BasePresenter {
    private ViewInterface mView;

    private AboutPresenter(ViewInterface viewInterface) {
        this.mView = viewInterface;
    }

    public static AboutPresenter newInstance(ViewInterface viewInterface) {
        return new AboutPresenter(viewInterface);
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
