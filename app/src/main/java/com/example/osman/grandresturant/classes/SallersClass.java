package com.example.osman.grandresturant.classes;

import com.example.osman.grandresturant.SallersRecycler;

/**
 * Created by A.taher on 4/12/2018.
 */

public class SallersClass {

    String country , email , mobile , profile_image , user_tpe , username, id ;


    public SallersClass(){}

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getUser_tpe() {
        return user_tpe;
    }

    public void setUser_tpe(String user_tpe) {
        this.user_tpe = user_tpe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SallersClass(String country, String email, String mobile, String profile_image, String user_tpe, String username) {

        this.country = country;
        this.email = email;
        this.mobile = mobile;
        this.profile_image = profile_image;
        this.user_tpe = user_tpe;
        this.username = username;
    }
}
