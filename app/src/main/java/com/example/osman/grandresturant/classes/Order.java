package com.example.osman.grandresturant.classes;

import com.example.osman.grandresturant.Helper.OrderStatus;

import static com.example.osman.grandresturant.Helper.OrderStatus.SENT;

public class Order {
    private RequestsClass item;
    private int quantity;


    public Order() {
    }

    public Order(RequestsClass item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public RequestsClass getItem() {
        return item;
    }

    public void setItem(RequestsClass item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
