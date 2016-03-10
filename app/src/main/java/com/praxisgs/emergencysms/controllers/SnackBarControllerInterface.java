package com.praxisgs.emergencysms.controllers;

/**
 * Created on 10/03/2016.
 */
public interface SnackBarControllerInterface {

    void showInformation(int messageResId, String[] parameters);
    void showError(int messageResId, String[] parameters);

}
