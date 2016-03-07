package com.praxisgs.emergencysms.pages;


import android.app.Fragment;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.adapters.ContactsAdapter;
import com.praxisgs.emergencysms.base.BaseDialogFragment;
import com.praxisgs.emergencysms.utils.AppNavigationEnum;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsListDialog extends BaseDialogFragment<ContactsListPresenter> implements ContactsListPresenter.ViewInterface,
        LoaderManager.LoaderCallbacks<Cursor> {
    public static final String TAG = ContactsListDialog.class.getName();

    String[] projection = {ContactsContract.Contacts._ID, Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.HONEYCOMB ?
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
            ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.HAS_PHONE_NUMBER};
    String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1";
    private ListView mContactsListview;


    @Override
    protected ContactsListPresenter instantiatePresenter() {
        return ContactsListPresenter.newInstance(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(1, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_contacts_list, null);
        bindView(view);
        return view;
    }

    private void bindView(View view) {
        getDialog().setTitle(AppNavigationEnum.CONTACTS.getTitle());
        mContactsListview = (ListView) view.findViewById(R.id.contacts_listview);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getAppContext(), ContactsContract.Contacts.CONTENT_URI, projection, selection, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        ContactsAdapter adapter = new ContactsAdapter(getAppContext(), cursor);
        mContactsListview.setAdapter(adapter);

        while (!cursor.isAfterLast()) {
            //TODO
            cursor.moveToNext();
        }
    }

    @Override
    public void onDestroy() {
        getActivity().getSupportLoaderManager().destroyLoader(1);
        super.onDestroy();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
