package com.praxisgs.emergencysms.pages;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.base.BaseDialogFragment;
import com.praxisgs.emergencysms.utils.AppNavigationEnum;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResetComfirmationDialog extends BaseDialogFragment<ResetComfirmationPresenter> implements ResetComfirmationPresenter.ViewInterface {
    public static final String TAG = ResetComfirmationDialog.class.getName();

    @Override
    protected ResetComfirmationPresenter instantiatePresenter() {
        return ResetComfirmationPresenter.newInstance(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_reset, null);
        bindView(view);
        return view;
    }

    private void bindView(View view) {
        getDialog().setTitle(AppNavigationEnum.RESET.getTitle());

        Button okBtn = (Button) view.findViewById(R.id.reset_ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.resetClicked();
            }
        });

        Button cancelBtn = (Button) view.findViewById(R.id.reset_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetComfirmationDialog.this.dismiss();
            }
        });
    }


    public FragmentActivity getAppActivity() {
        return getActivity();
    }

}
