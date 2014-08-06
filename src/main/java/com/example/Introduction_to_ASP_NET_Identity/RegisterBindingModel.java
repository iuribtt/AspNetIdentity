package com.example.Introduction_to_ASP_NET_Identity;

/**
 * Created by ServusKevin on 7/27/14.
 */
public class RegisterBindingModel {
    private String email;
    private String password;
    private String confirmPassword;

    public RegisterBindingModel() {
    }

    public RegisterBindingModel(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}