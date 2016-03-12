package com.praxisgs.emergencysms.pages;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends BaseFragment<HelpPresenter> implements HelpPresenter.ViewInterface {
    public static final String TAG = HelpFragment.class.getName();


    @Override
    protected HelpPresenter instantiatePresenter() {
        return HelpPresenter.newInstance(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_passcode, null);
        bindView(view);
        return view;
    }

    private void bindView(View view) {

    }

}
