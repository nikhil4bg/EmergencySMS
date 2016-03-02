package com.praxisgs.emergencysms.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.praxisgs.emergencysms.R;


public abstract class BaseActivity extends AppCompatActivity {

    final String TAG = BaseActivity.class.getName();

//    private Fragment newInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_activity);
        setBackStackListner();
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
    protected void onStart() {
        super.onStart();
    }




    private void hideKeyboard() {
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

//        if (newInstance != null && newInstance.getTag() != null && newInstance.getTag().equals(fragmentTag)) {
//            return;
//        }

        hideKeyboard();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        Fragment newInstance = Fragment.instantiate(this, fragmentTag, bundle);
        ft.replace(R.id.fragment_container, newInstance,title);
        if (addToBackStack) {
            ft.addToBackStack(fragmentTag);
        }
        ft.commit();

        setTitle(title);
    }

}
