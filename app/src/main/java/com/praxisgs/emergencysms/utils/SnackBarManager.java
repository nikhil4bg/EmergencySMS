package com.praxisgs.emergencysms.utils;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.praxisgs.emergencysms.R;

/**
 * Created on 10/03/2016.
 */
public class SnackBarManager {
    public static final String TAG = SnackBarManager.class.getSimpleName();

    /**
     * Message types
     */
    private final String ERROR = "ERROR";
    private final String INFO = "INFO";

    public void showErrorMessage( int messageResId,  FragmentActivity activity,String... parameters) {
        showMessage( ERROR, messageResId, activity,parameters);
    }

    public void showInformationMessage( int messageResId, FragmentActivity activity,String... parameters) {
        showMessage(INFO, messageResId,activity,parameters);
    }

    /**
     *
     * @param type
     * @param messageResId
     * @param activity
     * @param parameters
     */
    private void showMessage( String type, int messageResId, FragmentActivity activity,String... parameters) {

        String message = activity.getResources().getString(messageResId,parameters);

        RelativeLayout coordinatorLayout = getBlockedView(activity);

        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);

        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.0f);
        textView.setTextColor(activity.getResources().getColor(R.color.Black));
        textView.setMaxLines(32);

        switch (type) {
            case ERROR:
                snackbar.getView().setBackgroundColor(activity.getResources().getColor(R.color.OrangeRed));
                break;
            case INFO:
                snackbar.getView().setBackgroundColor(activity.getResources().getColor(R.color.LightSeaGreen));
                break;
        }
        snackbar.show();
    }

    private RelativeLayout getBlockedView(FragmentActivity activity) {
        Fragment dialogFragment = AppUtils.getInstance().getDialogFragment(activity);

        RelativeLayout coordinatorLayout;
        if (dialogFragment != null && dialogFragment.isVisible()) {
            coordinatorLayout = (RelativeLayout)dialogFragment.getView().findViewById(R.id.snackbarPosition);
            coordinatorLayout.setLayoutParams(new RelativeLayout.LayoutParams(dialogFragment.getView().getMeasuredWidth(), dialogFragment.getView().getMeasuredHeight()));
        } else {
            coordinatorLayout = (RelativeLayout)activity.findViewById(R.id.snackbarPosition);
        }

        return coordinatorLayout;
    }


}
