package com.praxisgs.emergencysms.pages;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.praxisgs.emergencysms.base.BasePresenter;

/**
 * Created on ${<VARIABLE_DATE>}.
 */
public class ContactsListPresenter implements BasePresenter {
    private ViewInterface mView;

    private ContactsListPresenter(ViewInterface viewInterface) {
        this.mView = viewInterface;
    }

    public static ContactsListPresenter newInstance(ViewInterface viewInterface) {
        return new ContactsListPresenter(viewInterface);
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
