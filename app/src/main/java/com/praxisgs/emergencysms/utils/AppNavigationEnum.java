package com.praxisgs.emergencysms.utils;


import com.praxisgs.emergencysms.pages.ContactsListDialog;
import com.praxisgs.emergencysms.pages.PasscodeFragment;
import com.praxisgs.emergencysms.pages.SettingsFragment;

/**
 * Created on 04/02/2016.
 */
public enum AppNavigationEnum {

    SETTINGS(SettingsFragment.TAG,"Settings"),
    PASSCODE(PasscodeFragment.TAG,"Passcode"),
    CONTACTS(ContactsListDialog.TAG,"Contacts");

    private String fragmentTag;
    private String title;

    AppNavigationEnum(String fragmentTag,String title){
        this.fragmentTag = fragmentTag;
        this.title = title;
    }


    public String getFragmentTag() {
        return fragmentTag;
    }

    public String getTitle() {
        return title;
    }


}
