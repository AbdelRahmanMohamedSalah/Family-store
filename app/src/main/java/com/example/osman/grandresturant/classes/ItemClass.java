package com.example.osman.grandresturant.classes;

/**
 * Created by A.taher on 3/28/2018.
 */

public class ItemClass {


    public ItemClass() {
    }



   private String id , idItem, Name,image, CountryLocation, Description, ItemType, PlaceLocation, Price , UserID , UserName , UserEmail , UserNumber   ;
    private long UploadedTime;

    public ItemClass(String id, String idItem, String name, String image, String countryLocation, String description, String itemType, String placeLocation, String price, String userID, String userName, String userEmail, String userNumber, long uploadedTime) {
        this.id = id;
        this.idItem = idItem;
        Name = name;
        this.image = image;
        CountryLocation = countryLocation;
        Description = description;
        ItemType = itemType;
        PlaceLocation = placeLocation;
        Price = price;
        UserID = userID;
        UserName = userName;
        UserEmail = userEmail;
        UserNumber = userNumber;
        //UserImage = userImage;
        UploadedTime = uploadedTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCountryLocation() {
        return CountryLocation;
    }

    public void setCountryLocation(String countryLocation) {
        CountryLocation = countryLocation;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getItemType() {
        return ItemType;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
    }

    public String getPlaceLocation() {
        return PlaceLocation;
    }

    public void setPlaceLocation(String placeLocation) {
        PlaceLocation = placeLocation;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserNumber() {
        return UserNumber;
    }

    public void setUserNumber(String userNumber) {
        UserNumber = userNumber;
    }

//    public String getUserImage() {
//        return UserImage;
//    }

//    public void setUserImage(String userImage) {
//        UserImage = userImage;
//    }

    public long getUploadedTime() {
        return UploadedTime;
    }

    public void setUploadedTime(long uploadedTime) {
        UploadedTime = uploadedTime;
    }
}
