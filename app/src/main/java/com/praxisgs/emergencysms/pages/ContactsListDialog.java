package com.praxisgs.emergencysms.pages;


import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.adapters.ContactCursorAdapter;
import com.praxisgs.emergencysms.base.BaseDialogFragment;
import com.praxisgs.emergencysms.utils.AppNavigationEnum;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsListDialog extends BaseDialogFragment<ContactsListPresenter> implements ContactsListPresenter.ViewInterface {
    public static final String TAG = ContactsListDialog.class.getName();

    private ListView mContactsListview;
    private EditText mSearchEditText;


    @Override
    protected ContactsListPresenter instantiatePresenter() {
        return ContactsListPresenter.newInstance(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.initiateLoader();

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
        mContactsListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactCursorAdapter cursorAdaptor = ((ContactCursorAdapter) parent.getAdapter());
                Cursor cursor = cursorAdaptor.getCursor();
                cursor.moveToPosition(position);
                HashMap<String, String> contactDetails = cursorAdaptor.getContactDetails(cursor);
                mPresenter.contactSelected(contactDetails);
            }
        });
        mSearchEditText = (EditText) view.findViewById(R.id.contacts_search_editText);
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPresenter.searchTextChangedTo(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button cancelBtn = (Button) view.findViewById(R.id.contacts_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


//    @Override
//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        return new CursorLoader(getAppContext(), ContactsContract.Contacts.CONTENT_URI, projection, selection, null, null);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
//        cursor.moveToFirst();
//        ContactsAdapter adapter = new ContactsAdapter(getAppContext(), cursor);
//        mContactsListview.setAdapter(adapter);
//
//        while (!cursor.isAfterLast()) {
//            //TODO
//            cursor.moveToNext();
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        getActivity().getSupportLoaderManager().destroyLoader(1);
//        super.onDestroy();
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//
//    }

    public FragmentActivity getAppActivity() {
        return getActivity();
    }

    @Override
    public void contactLoadingFinished(ContactCursorAdapter adapter) {
        mContactsListview.setAdapter(adapter);
    }
}
