package com.praxisgs.emergencysms.pages;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.praxisgs.emergencysms.base.BasePresenter;
import com.praxisgs.emergencysms.eventbus.AppNavigationEvents;
import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
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
    ArrayList<ContactModel> mContactModelList = new ArrayList<>();
    String[] projection = {ContactsContract.Contacts._ID, Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.HONEYCOMB ?
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
            ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.HAS_PHONE_NUMBER};
    String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1";

    private SettingsPresenter(ViewInterface viewInterface) {
        this.mView = viewInterface;
    }

    public static SettingsPresenter newInstance(ViewInterface viewInterface) {
        return new SettingsPresenter(viewInterface);
    }

    public SettingModel getSettings() {
        return EmergencySMSModel.getInstance().getSettingModel();
    }

    public void saveSettings(boolean locationEnabled, boolean serviceEnabled, String message) {
        SettingModel settingModel = new SettingModel();
        settingModel.setLocationIncluded(locationEnabled);
        settingModel.setServiceEnabled(serviceEnabled);
        settingModel.setMessage(message);

        ArrayList<ContactModel> contacts = new ArrayList<ContactModel>();
        if (EmergencySMSModel.getInstance().getSelectedContact() != null) {
            contacts.add(EmergencySMSModel.getInstance().getSelectedContact());
        }
        settingModel.setContactModels(contacts);

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

        void contactsLoaded();
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

    public void chooseContactClicked() {
        EmergencySMSEventBus.post(new AppNavigationEvents.EventShowContactPage());
    }

}
