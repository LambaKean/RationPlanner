package com.lambakean.RationPlanner.dto;

public class PhotoDto {

    private String id;
    private String extension;

    public PhotoDto(String id, String extension) {
        this.id = id;
        this.extension = extension;
    }

    public PhotoDto() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
