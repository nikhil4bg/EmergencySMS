package com.praxisgs.emergencysms.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.praxisgs.emergencysms.R;
import com.praxisgs.emergencysms.controllers.SnackBarController;
import com.praxisgs.emergencysms.controllers.SnackBarControllerInterface;
import com.praxisgs.emergencysms.utils.SnackBarManager;


public abstract class BaseActivity extends AppCompatActivity implements SnackBarControllerInterface {

    final String TAG = BaseActivity.class.getName();
    private SnackBarManager mSnackBarManager;
    private SnackBarController mSnackBarController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBackStackListner();
        initialiseControllers();
        setSnackBarManager();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSnackBarController.destroy();
        mSnackBarManager = null;
        mSnackBarController = null;
    }

    private void initialiseControllers() {
        mSnackBarController = new SnackBarController(this);
    }

    private void setSnackBarManager() {
        mSnackBarManager = new SnackBarManager();
    }

    private void setBackStackListner() {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                String fragmentTag = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getTag();
                setTitle(fragmentTag);
            }
        });
    }


    @Override
    public void showInformation(int messageResId, String... parameters) {
        mSnackBarManager.showInformationMessage(messageResId, this, parameters);
    }

    @Override
    public void showError(int messageResId, String... parameters) {
        mSnackBarManager.showErrorMessage(messageResId, this, parameters);
    }


    public void hideKeyboard() {
        try {
            InputMethodManager im = (InputMethodManager) BaseActivity.this.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im != null) {
                im.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showFragment(String fragmentTag, Bundle bundle, String title, boolean addToBackStack) {

        hideKeyboard();

        Log.e(TAG, "@@@@@@@@@@@@@@@ activity: " + this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        Fragment newInstance = Fragment.instantiate(this, fragmentTag, bundle);
        ft.replace(R.id.fragment_container, newInstance, title);
        if (addToBackStack) {
            ft.addToBackStack(fragmentTag);
        }
        ft.commit();

        setTitle(title);
    }

    /**
     * Show the specified fragment in a dialog popup
     *
     * @param fragmentTag    TAG of the fragment to be shown as a dialog
     * @param fragmentBundle parameters to be passes to fragment in form of bundle
     * @return dialog fragment displayed
     */
    public DialogFragment showDialogFragment(String fragmentTag, Parcelable fragmentBundle) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(fragmentTag, fragmentBundle);

        DialogFragment newInstance = (DialogFragment) DialogFragment.instantiate(
                getApplicationContext(), fragmentTag, bundle);

        FragmentManager fm = getSupportFragmentManager();
        newInstance.show(fm, fragmentTag);
        return newInstance;
    }

}
