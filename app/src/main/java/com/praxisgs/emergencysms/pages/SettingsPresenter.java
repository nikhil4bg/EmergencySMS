package com.praxisgs.emergencysms.pages;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.praxisgs.emergencysms.base.BasePresenter;
import com.praxisgs.emergencysms.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on ${<VARIABLE_DATE>}.
 */
public class SettingsPresenter implements BasePresenter {
    private ViewInterface mView;
    ArrayList<Contact> mContactList = new ArrayList<>();

    private SettingsPresenter(ViewInterface viewInterface) {
        this.mView = viewInterface;
    }

    public static SettingsPresenter newInstance(ViewInterface viewInterface) {
        return new SettingsPresenter(viewInterface);
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

    public List<Contact> retrieveContacts() {

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


        return mContactList;


    }

    private List<Contact> getContacts() {


        String[] projection = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.HAS_PHONE_NUMBER};
        String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1";

        ContentResolver contentResolver = getAppContext().getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, projection, selection, null, null);
        if (cursor.getCount() > 0) {
            String displayName, mobileNumber;
            while (cursor.moveToNext()) {
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Cursor phones = getAppContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                while (phones.moveToNext()) {
                    int phoneType = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                    if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
                        Contact tempContact = new Contact();
                        mobileNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
                        mobileNumber = mobileNumber.replaceAll("\\s", "");
                        tempContact.setDisplayName(displayName);
                        tempContact.setMobileNumber(mobileNumber);
                        mContactList.add(tempContact);
                        break;
                    }
                }
            }
        }

        return mContactList;
    }



}
