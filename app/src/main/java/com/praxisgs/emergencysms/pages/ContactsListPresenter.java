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

import com.praxisgs.emergencysms.adapters.ContactsAdapter;
import com.praxisgs.emergencysms.base.BasePresenter;

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

        void contactLoadingFinished(ContactsAdapter adapter);
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
        return new CursorLoader(getAppContext(), ContactsContract.Contacts.CONTENT_URI, projection, selection, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        ContactsAdapter adapter = new ContactsAdapter(getAppContext(), cursor);
        mView.contactLoadingFinished(adapter);
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
}
