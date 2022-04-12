package com.lambakean.RationPlanner.dto.form;

public class UserAuthenticationForm {

    private String username;
    private String password;

    public UserAuthenticationForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserAuthenticationForm() {}


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}