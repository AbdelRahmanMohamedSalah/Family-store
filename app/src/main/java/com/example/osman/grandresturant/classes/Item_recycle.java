package com.example.osman.grandresturant.classes;

import java.io.Serializable;

/**
 * Created by Abdel Rahman on 06-Apr-18.
 */

public class Item_recycle  implements Serializable{

    String name,image,id;


    public Item_recycle(String name, String image, String id) {
        this.name = name;
        this.image = image;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
