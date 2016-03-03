package com.praxisgs.emergencysms.pages;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.adapters.ContactsAdapter;
import com.praxisgs.emergencysms.base.BaseFragment;
import com.praxisgs.emergencysms.model.ContactModel;
import com.praxisgs.emergencysms.model.SettingModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment<SettingsPresenter> implements SettingsPresenter.ViewInterface {
    public static final String TAG = SettingsFragment.class.getName();
    private AutoCompleteTextView mContactEditText;
    private AutoCompleteTextView mContactsEditBox;
    private CheckBox mLocationEnabledCheckBox;
    private CheckBox mServiceEnabledCheckBox;
    private Button mSaveButton;
    private EditText mMessageEditText;

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
        List<ContactModel> contactModels = mPresenter.retrieveContacts();
        SettingModel settingModel = mPresenter.getSettings();
        mContactEditText = (AutoCompleteTextView) view.findViewById(R.id.contect_editText);
        mContactEditText.setEnabled(false);
        mContactEditText.setAdapter(new ContactsAdapter(getAppContext(), contactModels));

        mLocationEnabledCheckBox = (CheckBox) view.findViewById(R.id.include_location_checkBox);
        mServiceEnabledCheckBox = (CheckBox) view.findViewById(R.id.start_service_checkBox);
        mMessageEditText = (EditText) view.findViewById(R.id.enter_message_editText);

        if (settingModel != null) {
            if (settingModel.getContactModels() != null && settingModel.getContactModels().size() > 0) {
                mContactEditText.setText(settingModel.getContactModels().get(0).getDisplayName());
            }
            mLocationEnabledCheckBox.setChecked(settingModel.isLocationIncluded());
            mServiceEnabledCheckBox.setChecked(settingModel.isServiceEnabled());
            mMessageEditText.setText(settingModel.getMessage());
        }

        mSaveButton = (Button) view.findViewById(R.id.save_settings_btn);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveSettings(mLocationEnabledCheckBox.isChecked(), mServiceEnabledCheckBox.isChecked(), mMessageEditText.getText().toString());
            }
        });


    }


    @Override
    public void contactsLoaded() {
        if (!mContactEditText.isEnabled()) {
            mContactEditText.setEnabled(true);
            mContactEditText.setHint(R.string.type_in_the_contact_name);
        }
    }
}
