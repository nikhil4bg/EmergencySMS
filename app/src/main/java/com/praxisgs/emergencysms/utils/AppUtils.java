package com.praxisgs.emergencysms.utils;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.praxisgs.emergencysms.base.EmergencySMSApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created on 11/02/2016.
 */
public class AppUtils {

    private static AppUtils mInstance;
    private final Context mContext;

    private AppUtils(Context context) {
        this.mContext = context;
    }

    public static void initialise(Context context) {
        mInstance = new AppUtils(context);
    }

    public static AppUtils getInstance() {
        return mInstance;
    }

    /**
     * Get topmost dialog fragment
     *
     * @param activity where the dialog is attached.
     * @return the dialog fragment if any.
     */
    public Fragment getDialogFragment(FragmentActivity activity) {
        Fragment dialogFragment = null;
        List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof DialogFragment) {
                    if (fragment.isVisible()) {
                        dialogFragment = fragment;
                        break;
                    }
                }
            }
        }
        return dialogFragment;
    }



}
