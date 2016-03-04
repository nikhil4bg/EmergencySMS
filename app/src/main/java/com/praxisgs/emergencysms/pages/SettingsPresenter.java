package com.praxisgs.emergencysms.pages;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.praxisgs.emergencysms.base.BasePresenter;
import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.eventbus.ServiceEvents;
import com.praxisgs.emergencysms.model.ContactModel;
import com.praxisgs.emergencysms.model.EmergencySMSModel;
import com.praxisgs.emergencysms.model.SettingModel;

import java.util.ArrayList;
import java.util.List;

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

        if(serviceEnabled){
            EmergencySMSEventBus.post(new ServiceEvents.EventStopEmergencySMSService());
            EmergencySMSEventBus.post(new ServiceEvents.EventStartEmergencySMSService());
        }else{
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

    public List<ContactModel> retrieveContacts() {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
            }// End of onPreExecute method

            @Override
            protected Void doInBackground(Void... params) {
                getContacts();

                return null;
            }// End of doInBackground method

            @Override
            protected void onPostExecute(Void result) {
                mView.contactsLoaded();
            }//End of onPostExecute method
        }.execute();


        return mContactModelList;


    }


    private List<ContactModel> getContacts() {


        String[] projection = {ContactsContract.Contacts._ID, Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.HONEYCOMB ?
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.HAS_PHONE_NUMBER};
        String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1";

        ContentResolver contentResolver = getAppContext().getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, projection, selection, null, null);
        if (cursor.getCount() > 0) {
            String displayName, mobileNumber, contactId;
            while (cursor.moveToNext()) {
                contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                displayName = cursor.getString(cursor.getColumnIndex(Build.VERSION.SDK_INT
                        >= Build.VERSION_CODES.HONEYCOMB ?
                        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                        ContactsContract.Contacts.DISPLAY_NAME));


                Cursor phones = getAppContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                if (phones != null) {
                    while (phones.moveToNext()) {
                        int phoneType = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
                            ContactModel tempContactModel = new ContactModel();
                            mobileNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
                            mobileNumber = mobileNumber.replaceAll("\\s", "");
                            tempContactModel.setDisplayName(displayName);
                            tempContactModel.setMobileNumber(mobileNumber);
                            tempContactModel.setId(contactId);
                            mContactModelList.add(tempContactModel);
                            phones.close();
                            break;
                        } else {
                            phones.close();
                            break;
                        }
                    }
                    phones.close();
                }
            }
            cursor.close();
        }
        cursor.close();

        return mContactModelList;
    }


}
