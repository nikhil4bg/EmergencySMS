package com.praxisgs.emergencysms.pages;


import android.app.Dialog;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.adapters.ContactCursorAdapter;
import com.praxisgs.emergencysms.base.BaseDialogFragment;
import com.praxisgs.emergencysms.base.BasePresenter;
import com.praxisgs.emergencysms.utils.AppNavigationEnum;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutDialog extends BaseDialogFragment<AboutPresenter> implements AboutPresenter.ViewInterface {
    public static final String TAG = AboutDialog.class.getName();

    @Override
    protected AboutPresenter instantiatePresenter() {
        return AboutPresenter.newInstance(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_about, null);
        bindView(view);
        return view;
    }


    private void bindView(View view) {
        getDialog().setTitle(AppNavigationEnum.ABOUT.getTitle());
        TextView appName = (TextView) view.findViewById(R.id.about_app_name);
        appName.setText(mPresenter.getApplicationName());

        TextView versionNumber = (TextView) view.findViewById(R.id.about_version_number);
        versionNumber.setText(mPresenter.getVersionNumber());

        Button okBtn = (Button) view.findViewById(R.id.about_ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutDialog.this.dismiss();
            }
        });
    }


    public FragmentActivity getAppActivity() {
        return getActivity();
    }

}
