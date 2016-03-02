package com.praxisgs.emergencysms.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created on 04/02/2016.
 */
public interface BasePresenter {
    void onCreate(Bundle savedInstanceState);

    void onCreateView(LayoutInflater inflater, ViewGroup container,
                      Bundle savedInstanceState);

    void onResume();

    void onPause();

    void onDetach();

    void onDestroy();

    Context getAppContext();
}
