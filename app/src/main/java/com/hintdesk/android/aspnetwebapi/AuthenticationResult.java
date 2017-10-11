package com.hintdesk.android.aspnetwebapi;

/**
 * Created by ServusKevin on 06.10.2017.
 */

public class AuthenticationResult {
    public boolean isSuccessful() {
        return isSuccessful;
    }

    public AuthenticationResult(boolean isSuccessful, String accessToken, String error) {
        this.isSuccessful = isSuccessful;
        this.accessToken = accessToken;
        this.error = error;
    }



    public String getAccessToken() {
        return accessToken;
    }



    public String getError() {
        return error;
    }



    private boolean isSuccessful;
    private String accessToken;
    private String error;
}
