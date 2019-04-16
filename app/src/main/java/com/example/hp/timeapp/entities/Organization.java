package com.example.hp.timeapp.entities;


public class Organization {

    private  int id;
    private String name_of_organization;
    private String full_description;
    private double price;
    private int status_of_organization;
    private int rating;
    private String image_main;
    private String address;
    private int used_by_people;
    private String telephone;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public  Organization(int id){
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsed_by_people() {
        return used_by_people;
    }

    public void setUsed_by_people(int used_by_people) {
        this.used_by_people = used_by_people;
    }

    public String getTitle() {
        return name_of_organization;
    }

    public void setTitle(String title) {
        this.name_of_organization = title;
    }

    public String getBody() {
        return full_description;
    }

    public void setBody(String body) {
        this.full_description = body;
    }

    public double getPrice() {
        return price;
    }

    public int getStatus_of_organization() {
        return status_of_organization;
    }

    public int getRating() {
        return rating;
    }

    public String getImage_url() {
        return image_main;
    }

    public String getAddress() {
        return address;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus_of_organization(int status_of_organization) {
        this.status_of_organization = status_of_organization;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setImage_url(String image_url) {
        this.image_main = image_url;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
