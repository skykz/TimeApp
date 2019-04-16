package com.example.hp.timeapp.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleOrganization {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name_of_organization")
    @Expose
    private String name_of_organization;

    @SerializedName("full_description")
    @Expose
    private String full_description;

    @SerializedName("price")
    @Expose
    private double price;

    @SerializedName("status_of_organization")
    @Expose
    private int status_of_organization;

    @SerializedName("rating")
    @Expose
    private int rating;

    @SerializedName("image_main")
    @Expose
    String image_main;

    @SerializedName("address_1")
    @Expose
    String address_1;

    @SerializedName("used_by_people")
    @Expose
    int used_amount;

    @SerializedName("favorite")
    @Expose
    int favorite;

    @SerializedName("path_image")
    @Expose
    String path_image;

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getPath_image() {
        return path_image;
    }

    public void setPath_image(String path_image) {
        this.path_image = path_image;
    }


    public int getUsed_amount() {
        return used_amount;
    }

    public void setUsed_amount(int used_amount) {
        this.used_amount = used_amount;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_of_organization() {
        return name_of_organization;
    }

    public void setName_of_organization(String name_of_organization) {
        this.name_of_organization = name_of_organization;
    }

    public String getFull_description() {
        return full_description;
    }

    public void setFull_description(String full_description) {
        this.full_description = full_description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus_of_organization() {
        return status_of_organization;
    }

    public void setStatus_of_organization(int status_of_organization) {
        this.status_of_organization = status_of_organization;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImage_main() {
        return image_main;
    }

    public void setImage_main(String image_main) {
        this.image_main = image_main;
    }


}
