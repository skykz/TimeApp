package com.example.hp.timeapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImagesById {

    @SerializedName("organization_id")
    @Expose
    private int organization_id;

    @SerializedName("path_image")
    @Expose
    private String path_image;

    public int getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(int organization_id) {
        this.organization_id = organization_id;
    }

    public String getPath_image() {
        return path_image;
    }

    public void setPath_image(String path_image) {
        this.path_image = path_image;
    }
}
