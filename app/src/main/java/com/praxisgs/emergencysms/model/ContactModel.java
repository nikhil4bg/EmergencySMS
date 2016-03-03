package com.praxisgs.emergencysms.model;

import com.google.gson.annotations.Expose;

/**
 * Created on 02/03/2016.
 */
public class ContactModel {

    @Expose
    private String id;

    @Expose
    private String displayName;
    @Expose
    private String mobileNumber;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
