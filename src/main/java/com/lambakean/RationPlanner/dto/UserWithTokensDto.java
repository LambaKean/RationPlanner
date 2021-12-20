package com.lambakean.RationPlanner.dto;

public class UserWithTokensDto {

    private UserDto user;
    private String accessToken;

    public UserWithTokensDto(UserDto userDto, String accessToken) {
        this.user = userDto;
        this.accessToken = accessToken;
    }

    public UserWithTokensDto() {}


    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
