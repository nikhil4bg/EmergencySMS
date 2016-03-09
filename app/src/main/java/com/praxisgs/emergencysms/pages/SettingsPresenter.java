package com.praxisgs.emergencysms.pages;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.praxisgs.emergencysms.adapters.ContactCursorAdapter;
import com.praxisgs.emergencysms.base.BasePresenter;
import com.praxisgs.emergencysms.eventbus.AppNavigationEvents;
import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.eventbus.EventDataChanged;
import com.praxisgs.emergencysms.eventbus.ServiceEvents;
import com.praxisgs.emergencysms.model.ContactModel;
import com.praxisgs.emergencysms.model.EmergencySMSModel;
import com.praxisgs.emergencysms.model.SettingModel;

import java.util.ArrayList;

/**
 * Created on ${<VARIABLE_DATE>}.
 */
public class SettingsPresenter implements BasePresenter {
    private ViewInterface mView;

//    ArrayList<ContactModel> mContactModelList = new ArrayList<>();
//
//    String[] projection = {ContactsContract.Contacts._ID, Build.VERSION.SDK_INT
//            >= Build.VERSION_CODES.HONEYCOMB ?
//            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
//            ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.HAS_PHONE_NUMBER};
//
//    String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1";

    private SettingsPresenter(ViewInterface viewInterface) {
        this.mView = viewInterface;
    }

    public static SettingsPresenter newInstance(ViewInterface viewInterface) {
        return new SettingsPresenter(viewInterface);
    }

    public SettingModel getSettings() {
        return EmergencySMSModel.getInstance().getSettingModel();
    }

    public void saveSettings(String contactNumber, boolean locationEnabled, boolean serviceEnabled, String message) {
        SettingModel settingModel = EmergencySMSModel.getInstance().getSettingModel();
        if(settingModel == null){
            settingModel = new SettingModel();
            ContactModel tempContactModel = new ContactModel();
            tempContactModel.setDisplayName("UNKNOWN");
            tempContactModel.setMobileNumber(contactNumber);
            tempContactModel.setId("UNKNOWN");
            settingModel.setContactModels(tempContactModel);
        }
        settingModel.setLocationIncluded(locationEnabled);
        settingModel.setServiceEnabled(serviceEnabled);
        settingModel.setMessage(message);

        EmergencySMSModel.getInstance().setSettingModel(settingModel);

        if (serviceEnabled) {
            EmergencySMSEventBus.post(new ServiceEvents.EventStopEmergencySMSService());
            EmergencySMSEventBus.post(new ServiceEvents.EventStartEmergencySMSService());
        } else {
            EmergencySMSEventBus.post(new ServiceEvents.EventStopEmergencySMSService());
        }
    }


    public interface ViewInterface {
        Context getAppContext();

        void updateUI();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        EmergencySMSEventBus.register(this);
    }

    @Override
    public void onPause() {
        EmergencySMSEventBus.unregister(this);
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

    public void chooseContactClicked() {
        EmergencySMSEventBus.post(new AppNavigationEvents.EventShowContactPage());
    }

    public void onEvent(EventDataChanged event) {
        mView.updateUI();
    }

}
