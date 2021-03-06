package com.praxisgs.emergencysms.pages;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.base.BasePresenter;
import com.praxisgs.emergencysms.eventbus.AppNavigationEvents;
import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.eventbus.EventDataChanged;
import com.praxisgs.emergencysms.eventbus.PermissionEvents;
import com.praxisgs.emergencysms.eventbus.ServiceEvents;
import com.praxisgs.emergencysms.eventbus.SnackBarEvents;
import com.praxisgs.emergencysms.model.EmergencySMSModel;
import com.praxisgs.emergencysms.model.SettingModel;
import com.praxisgs.emergencysms.utils.Constants;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

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

    public SettingModel getSettings() {
        return EmergencySMSModel.getInstance().getSettingModel();
    }

    public void saveSettings(boolean locationEnabled, boolean serviceEnabled, String message) {
        SettingModel settingModel = EmergencySMSModel.getInstance().getSettingModel();
        if (settingModel == null) {
            EmergencySMSEventBus.post(new SnackBarEvents.EventInformation(R.string.please_select_contact));
        } else if (settingModel != null) {
            settingModel.setLocationIncluded(locationEnabled);
            settingModel.setServiceEnabled(serviceEnabled);
            settingModel.setMessage(message);
            EmergencySMSModel.getInstance().setSettingModel(settingModel);
            EmergencySMSModel.getInstance().save();

            if (serviceEnabled) {
                EmergencySMSEventBus.post(new ServiceEvents.EventStartEmergencySMSService());
            } else {
                EmergencySMSEventBus.post(new ServiceEvents.EventStopEmergencySMSService());
            }
            EmergencySMSEventBus.post(new SnackBarEvents.EventInformation(R.string.saved));
        }
    }


    public interface ViewInterface {
        Context getAppContext();

        void updateUI(boolean updatedMyUser);
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
        EmergencySMSEventBus.post(new PermissionEvents.EventCheckPermission(new String[]{Manifest.permission.READ_CONTACTS}));
    }

    public void startServiceChecked() {
        EmergencySMSEventBus.post(new PermissionEvents.EventCheckPermission(new String[]{Manifest.permission.SEND_SMS}));
    }

    public void changePassCodeClicked() {
        EmergencySMSEventBus.post(new AppNavigationEvents.EventShowChangePassCodePage());
    }

    public void resetClicked() {
        EmergencySMSEventBus.post(new AppNavigationEvents.EventShowResetPage());
    }

    public void helpClicked() {
        EmergencySMSEventBus.post(new AppNavigationEvents.EventShowHelpPage());
    }

    public void aboutClicked() {
        EmergencySMSEventBus.post(new AppNavigationEvents.EventShowAboutPage());
    }


    public void onEvent(EventDataChanged event) {
        mView.updateUI(true);
    }

    public void onEvent(PermissionEvents.EventPermissionBeforeRequestResults event) {
        HashMap<String, Boolean> permissionStatus = event.getPermissionStatus();
        final Set<String> permissionsApplied = permissionStatus.keySet();
        Iterator<String> permissionsAppliedIterator = permissionsApplied.iterator();
        while (permissionsAppliedIterator.hasNext()) {
            String tempPermission = permissionsAppliedIterator.next();
            switch (tempPermission) {
                case Manifest.permission.READ_CONTACTS:
                    if (permissionStatus.get(Manifest.permission.READ_CONTACTS)) {
                        EmergencySMSEventBus.post(new AppNavigationEvents.EventShowContactPage());
                    } else {
                        EmergencySMSEventBus.post(new PermissionEvents.EventRequestPermission(Manifest.permission.READ_CONTACTS, R.string.read_permission_message, Constants.PERMISSION_READ_CONTACT_REQUEST_CODE));
                    }
                    break;
                case Manifest.permission.SEND_SMS:
                    if (!permissionStatus.get(Manifest.permission.SEND_SMS)) {
                        EmergencySMSEventBus.post(new PermissionEvents.EventRequestPermission(Manifest.permission.SEND_SMS, R.string.send_sms_permission_message, Constants.PERMISSION_SEND_SMS_AND_PREVENT_POWER_KEY_CODE));
                        mView.updateUI(true);
                    }
                    break;
            }
        }
//        if(event.isPermissionStatus()){
//            EmergencySMSEventBus.post(new AppNavigationEvents.EventShowContactPage());
//        }else{
//            EmergencySMSEventBus.post(new PermissionEvents.RequestPermission(Manifest.permission.READ_CONTACTS,R.string.read_permission_message, Constants.PERMISSION_READ_CONTACT_REQUEST_CODE));
//        }
    }

    public void onEvent(PermissionEvents.EventReadContactPermissionGranted event) {
        EmergencySMSEventBus.post(new AppNavigationEvents.EventShowContactPage());
    }

    public void onEvent(PermissionEvents.EventReadContactPermissionDenied event) {
        EmergencySMSEventBus.post(new SnackBarEvents.EventInformation(R.string.read_permission_message));
    }

    public void onEvent(PermissionEvents.EventSendSMSPermissionGranted event) {
        //TODO
    }

    public void onEvent(PermissionEvents.EventSendSMSPermissionDenied event) {
        EmergencySMSEventBus.post(new SnackBarEvents.EventInformation(R.string.send_sms_permission_message));
    }

}
