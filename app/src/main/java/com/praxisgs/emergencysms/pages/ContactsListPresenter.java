package com.praxisgs.emergencysms.pages;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.adapters.ContactCursorAdapter;
import com.praxisgs.emergencysms.adapters.ContactsAdapter;
import com.praxisgs.emergencysms.base.BasePresenter;
import com.praxisgs.emergencysms.eventbus.EmergencySMSEventBus;
import com.praxisgs.emergencysms.eventbus.EventDataChanged;
import com.praxisgs.emergencysms.eventbus.ServiceEvents;
import com.praxisgs.emergencysms.eventbus.SnackBarEvents;
import com.praxisgs.emergencysms.model.ContactModel;
import com.praxisgs.emergencysms.model.EmergencySMSModel;
import com.praxisgs.emergencysms.model.SettingModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created on ${<VARIABLE_DATE>}.
 */
public class ContactsListPresenter implements BasePresenter, LoaderManager.LoaderCallbacks<Cursor> {
    private ViewInterface mView;
    String[] projection = {ContactsContract.Contacts._ID, Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.HONEYCOMB ?
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
            ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.HAS_PHONE_NUMBER};
    String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1";
    private final int mLoaderID = 1;

    private ContactsListPresenter(ViewInterface viewInterface) {
        this.mView = viewInterface;
    }

    public static ContactsListPresenter newInstance(ViewInterface viewInterface) {
        return new ContactsListPresenter(viewInterface);
    }

    public void initiateLoader() {
        mView.getActivity().getSupportLoaderManager().initLoader(mLoaderID, null, this);
    }




    public interface ViewInterface {
        Context getAppContext();

        FragmentActivity getActivity();

        void contactLoadingFinished(ContactCursorAdapter adapter);
        void dismiss();
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
        mView.getActivity().getSupportLoaderManager().destroyLoader(mLoaderID);
    }

    @Override
    public Context getAppContext() {
        return mView.getAppContext();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getAppContext(), ContactsContract.Contacts.CONTENT_URI, projection, selection, null, Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.HONEYCOMB ?
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                ContactsContract.Contacts.DISPLAY_NAME);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        ContactCursorAdapter contactCursorAdapter = new ContactCursorAdapter(getAppContext(),cursor,0);
       // ContactsAdapter adapter = new ContactsAdapter(getAppContext(), cursor);
        mView.contactLoadingFinished(contactCursorAdapter);
//
//
//        while (!cursor.isAfterLast()) {
//            //TODO
//            cursor.moveToNext();
//        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void contactSelected(HashMap<String, String> contactDetails) {
        if(contactDetails.get(ContactCursorAdapter.MOBILE_NUMBER_KEY).equalsIgnoreCase(getAppContext().getResources().getString(R.string.no_mobile_number_avaialble))){
            EmergencySMSEventBus.post(new SnackBarEvents.EventInformation(R.string.no_mobile_number_message,contactDetails.get(ContactCursorAdapter.DISPLAY_NAME_KEY)));
        }else{
            SettingModel settingModel = EmergencySMSModel.getInstance().getSettingModel();
            if(settingModel == null){
                settingModel = new SettingModel();
                settingModel.setLocationIncluded(false);
                settingModel.setServiceEnabled(false);
                settingModel.setMessage("");
            }

            ContactModel tempContactModel = new ContactModel();
            tempContactModel.setDisplayName(contactDetails.get(ContactCursorAdapter.DISPLAY_NAME_KEY));
            tempContactModel.setMobileNumber(contactDetails.get(ContactCursorAdapter.MOBILE_NUMBER_KEY));
            tempContactModel.setId(contactDetails.get(ContactCursorAdapter.ID));
            settingModel.setContactModels(tempContactModel);

            EmergencySMSModel.getInstance().setSettingModel(settingModel);

            EmergencySMSEventBus.post(new EventDataChanged());
            mView.dismiss();
        }

    }
}
