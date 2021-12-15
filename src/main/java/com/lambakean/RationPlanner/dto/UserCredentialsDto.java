package com.lambakean.RationPlanner.dto;

public class UserCredentialsDto {

    private String username;
    private String password;

    public UserCredentialsDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserCredentialsDto() {}


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