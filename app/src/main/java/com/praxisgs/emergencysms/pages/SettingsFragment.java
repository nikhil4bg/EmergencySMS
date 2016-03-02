package com.praxisgs.emergencysms.pages;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.adapters.ContactsAdapter;
import com.praxisgs.emergencysms.base.BaseFragment;
import com.praxisgs.emergencysms.model.Contact;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment<SettingsPresenter> implements SettingsPresenter.ViewInterface {
    public static final String TAG = SettingsFragment.class.getName();
    private AutoCompleteTextView mContactEditText;

    @Override
    protected SettingsPresenter instantiatePresenter() {
        return SettingsPresenter.newInstance(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_settings, null);
        bindView(view);
        return view;
    }

    private void bindView(View view) {
        List<Contact> contacts = mPresenter.retrieveContacts();
        mContactEditText = (AutoCompleteTextView)view.findViewById(R.id.contect_editText);
        mContactEditText.setEnabled(false);
        mContactEditText.setAdapter(new ContactsAdapter(getAppContext(),contacts));
    }


    @Override
    public void contactsLoaded() {
        if(!mContactEditText.isEnabled()){
            mContactEditText.setEnabled(true);
            mContactEditText.setHint(R.string.type_in_the_contact_name);
        }
    }
}
