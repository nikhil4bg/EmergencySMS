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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.base.BaseFragment;
import com.praxisgs.emergencysms.model.SettingModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment<SettingsPresenter> implements SettingsPresenter.ViewInterface {
    public static final String TAG = SettingsFragment.class.getName();
    private TextView mContactText;
    private AutoCompleteTextView mContactsEditBox;
    private CheckBox mLocationEnabledCheckBox;
    private CheckBox mServiceEnabledCheckBox;
    private Button mSaveButton;
    private EditText mMessageEditText;
    private Button mChooseContact;

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

        mContactText = (TextView) view.findViewById(R.id.contect_name_text);

        mLocationEnabledCheckBox = (CheckBox) view.findViewById(R.id.include_location_checkBox);

        mServiceEnabledCheckBox = (CheckBox) view.findViewById(R.id.start_service_checkBox);
        mServiceEnabledCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mPresenter.startServiceChecked();
                }
            }
        });
        mMessageEditText = (EditText) view.findViewById(R.id.enter_message_editText);


        mSaveButton = (Button) view.findViewById(R.id.save_settings_btn);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveSettings(mLocationEnabledCheckBox.isChecked(), mServiceEnabledCheckBox.isChecked(), mMessageEditText.getText().toString());
            }
        });

        mChooseContact = (Button) view.findViewById(R.id.choose_contact_settings_btn);
        mChooseContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.chooseContactClicked();
            }
        });

        updateUI();


    }


    @Override
    public void updateUI() {
        SettingModel settingModel = mPresenter.getSettings();
        if (settingModel != null) {
            if (settingModel.getContactModels() != null) {
                mContactText.setText(settingModel.getContactModels().getDisplayName());
            }
            mLocationEnabledCheckBox.setChecked(settingModel.isLocationIncluded());
            mServiceEnabledCheckBox.setChecked(settingModel.isServiceEnabled());
            mMessageEditText.setText(settingModel.getMessage());
        }
    }
}
