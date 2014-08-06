package com.example.Introduction_to_ASP_NET_Identity;

/**
 * Created by ServusKevin on 8/2/14.
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
