package com.praxisgs.emergencysms.pages;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.base.BaseDialogFragment;
import com.praxisgs.emergencysms.utils.AppNavigationEnum;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePassCodeDialog extends BaseDialogFragment<ChangePassCodePresenter> implements ChangePassCodePresenter.ViewInterface {
    public static final String TAG = ChangePassCodeDialog.class.getName();

    @Override
    protected ChangePassCodePresenter instantiatePresenter() {
        return ChangePassCodePresenter.newInstance(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_chage_passcode, null);
        bindView(view);
        return view;
    }

    private void bindView(View view) {
        getDialog().setTitle(AppNavigationEnum.CHANGE_PASSCODE.getTitle());
        final EditText oldPassCode = (EditText)view.findViewById(R.id.change_passcode_old_passcode);
        final EditText newPassCode = (EditText)view.findViewById(R.id.change_passcode_new_passcode);
        final EditText confirmNewPassCode = (EditText)view.findViewById(R.id.change_passcode_confirm_passcode);

        Button changePassCodeBtn = (Button) view.findViewById(R.id.change_passcode_change_passcode);
        changePassCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.changePassCodeClicked(oldPassCode.getText().toString(),newPassCode.getText().toString(),confirmNewPassCode.getText().toString());
            }
        });

        Button cancelBtn = (Button) view.findViewById(R.id.change_passcode_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassCodeDialog.this.dismiss();
            }
        });
    }


    public FragmentActivity getAppActivity() {
        return getActivity();
    }

}
