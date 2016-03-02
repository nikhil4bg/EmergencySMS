package com.praxisgs.emergencysms.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.praxisgs.emergencysms.utils.Constants;


/**
 * Created on 01/02/2016.
 */
public class EmergencySMSModel {
    private static EmergencySMSModel instance;

    private final Gson gson;
    private final Context context;

    @Expose
    private PassCodeEntity passCodeEntity;

    public static void initialise(Context context) {
        if (instance == null) {
            instance = new EmergencySMSModel(context);
        }
    }

    public static EmergencySMSModel getInstance() {
        return instance;
    }

    private EmergencySMSModel(Context context) {
        this.context = context;
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        load();
    }

    public void save() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARE_PREF_NAME, Context.MODE_PRIVATE);
        String modelAsString = gson.toJson(instance);
        sharedPreferences.edit().putString(Constants.SHARE_PREF_NAME, modelAsString).apply();
    }

    private void load() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARE_PREF_NAME, Context.MODE_PRIVATE);
        String loadedModelString = sharedPreferences.getString(Constants.SHARE_PREF_NAME, null);
        setData(loadedModelString);
    }

    private void setData(String modelStr) {
        if (modelStr != null) {
            EmergencySMSModel model = gson.fromJson(modelStr, EmergencySMSModel.class);
            setPassCodeEntity(model.getPassCodeEntity());
        }
    }

    public void clearAndResetData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARE_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public void loadNewData(String modelStr) {
        if (modelStr != null) {
            setPassCodeEntity(gson.fromJson(modelStr, PassCodeEntity.class));
        }
    }


    public PassCodeEntity getPassCodeEntity() {
        return passCodeEntity;
    }

    public void setPassCodeEntity(PassCodeEntity passCodeEntity) {
        this.passCodeEntity = passCodeEntity;
    }

}
