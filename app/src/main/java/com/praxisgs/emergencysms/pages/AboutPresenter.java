package com.praxisgs.emergencysms.pages;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.adapters.ContactCursorAdapter;
import com.praxisgs.emergencysms.base.BasePresenter;
import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.eventbus.EventDataChanged;
import com.praxisgs.emergencysms.eventbus.SnackBarEvents;
import com.praxisgs.emergencysms.model.ContactModel;
import com.praxisgs.emergencysms.model.EmergencySMSModel;
import com.praxisgs.emergencysms.model.SettingModel;

import java.util.HashMap;

/**
 * Created on ${<VARIABLE_DATE>}.
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
