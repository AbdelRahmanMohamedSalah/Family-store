package com.example.osman.grandresturant.classes;

/**
 * Created by A.taher on 4/18/2018.
 */

public class RequestsClass {

    private String RequestUserName, RequestUserID, RequestUserEmail, RequestUserNumber, RequestUserImage, RequestItemID, RequestItemName, RequestItemPrice, RequestItemImage, RequestSallerID , RequestID;
    private long RequestTime;

    public RequestsClass(){}

    public RequestsClass(String requestUserName, String requestUserID, String requestUserEmail, String requestUserNumber, String requestUserImage, String requestItemID, String requestItemName, String requestItemPrice, String requestItemImage, String requestSallerID, long requestTime) {
        RequestUserName = requestUserName;
        RequestUserID = requestUserID;
        RequestUserEmail = requestUserEmail;
        RequestUserNumber = requestUserNumber;
        RequestUserImage = requestUserImage;
        RequestItemID = requestItemID;
        RequestItemName = requestItemName;
        RequestItemPrice = requestItemPrice;
        RequestItemImage = requestItemImage;
        RequestSallerID = requestSallerID;
        RequestTime = requestTime;
    }

    public String getRequestUserName() {
        return RequestUserName;
    }

    public void setRequestUserName(String requestUserName) {
        RequestUserName = requestUserName;
    }

    public String getRequestUserID() {
        return RequestUserID;
    }

    public void setRequestUserID(String requestUserID) {
        RequestUserID = requestUserID;
    }

    public String getRequestUserEmail() {
        return RequestUserEmail;
    }

    public void setRequestUserEmail(String requestUserEmail) {
        RequestUserEmail = requestUserEmail;
    }

    public String getRequestUserNumber() {
        return RequestUserNumber;
    }

    public void setRequestUserNumber(String requestUserNumber) {
        RequestUserNumber = requestUserNumber;
    }

    public String getRequestUserImage() {
        return RequestUserImage;
    }

    public void setRequestUserImage(String requestUserImage) {
        RequestUserImage = requestUserImage;
    }

    public String getRequestItemID() {
        return RequestItemID;
    }

    public void setRequestItemID(String requestItemID) {
        RequestItemID = requestItemID;
    }

    public String getRequestItemName() {
        return RequestItemName;
    }

    public void setRequestItemName(String requestItemName) {
        RequestItemName = requestItemName;
    }

    public String getRequestItemPrice() {
        return RequestItemPrice;
    }

    public void setRequestItemPrice(String requestItemPrice) {
        RequestItemPrice = requestItemPrice;
    }

    public String getRequestItemImage() {
        return RequestItemImage;
    }

    public void setRequestItemImage(String requestItemImage) {
        RequestItemImage = requestItemImage;
    }

    public String getRequestSallerID() {
        return RequestSallerID;
    }

    public void setRequestSallerID(String requestSallerID) {
        RequestSallerID = requestSallerID;
    }

    public String getRequestID() {
        return RequestID;
    }

    public void setRequestID(String requestID) {
        RequestID = requestID;
    }

    public long getRequestTime() {
        return RequestTime;
    }

    public void setRequestTime(long requestTime) {
        RequestTime = requestTime;
    }
}
