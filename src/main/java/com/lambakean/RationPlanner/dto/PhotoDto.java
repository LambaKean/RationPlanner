package com.lambakean.RationPlanner.dto;

public class PhotoDto {

    private String id;

    public PhotoDto(String id) {
        this.id = id;
    }

    public PhotoDto() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
