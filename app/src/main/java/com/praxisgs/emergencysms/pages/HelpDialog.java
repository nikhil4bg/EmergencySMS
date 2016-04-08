package com.praxisgs.emergencysms.pages;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.base.BaseDialogFragment;
import com.praxisgs.emergencysms.utils.AppNavigationEnum;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpDialog extends BaseDialogFragment<HelpPresenter> implements HelpPresenter.ViewInterface {
    public static final String TAG = HelpDialog.class.getName();


    @Override
    protected HelpPresenter instantiatePresenter() {
        return HelpPresenter.newInstance(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_help, null);
        bindView(view);
        return view;
    }

    private void bindView(View view) {
        getDialog().setTitle(AppNavigationEnum.HELP.getTitle());
        Button okBtn = (Button) view.findViewById(R.id.help_ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
