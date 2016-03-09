package com.praxisgs.emergencysms.model;

import com.google.gson.annotations.Expose;

/**
 * Created on 03/03/2016.
 */
public class SettingModel {

    @Expose
    private ContactModel contactModels;
    @Expose
    private boolean locationIncluded, serviceEnabled;
    @Expose
    private String message;

    public ContactModel getContactModels() {
        return contactModels;
    }

    public void setContactModels(ContactModel contactModels) {
        this.contactModels = contactModels;
    }

    public boolean isLocationIncluded() {
        return locationIncluded;
    }

    public void setLocationIncluded(boolean locationIncluded) {
        this.locationIncluded = locationIncluded;
    }

    public boolean isServiceEnabled() {
        return serviceEnabled;
    }

    public void setServiceEnabled(boolean serviceEnabled) {
        this.serviceEnabled = serviceEnabled;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
