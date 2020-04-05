package com.example.osman.grandresturant.classes;

/**
 * Created by osman on 4/12/2018.
 */

public class Encaps_Basket {

    String id,name,image,price,companyName,place;

    public Encaps_Basket() {
    }

    public Encaps_Basket(String name, String image, String price, String companyName, String place) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.companyName = companyName;
        this.place = place;
    }

    public Encaps_Basket(String id, String name, String image, String price, String companyName, String place) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.companyName = companyName;
        this.place = place;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
