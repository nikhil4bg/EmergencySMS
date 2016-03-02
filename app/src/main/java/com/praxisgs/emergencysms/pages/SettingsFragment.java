package com.praxisgs.emergencysms.pages;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment<SettingsPresenter> implements SettingsPresenter.ViewInterface {
    public static final String TAG = SettingsFragment.class.getName();

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

    }


}
