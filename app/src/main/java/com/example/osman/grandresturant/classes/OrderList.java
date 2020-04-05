package com.example.osman.grandresturant.classes;

import com.example.osman.grandresturant.Helper.OrderStatus;

import java.util.ArrayList;

public class OrderList {
    private ArrayList<Order> orderList;
    private OrderStatus status;
    private long time;


    public OrderList() {
    }

    public OrderList(ArrayList<Order> orderList, OrderStatus status, long time) {
        this.orderList = orderList;
        this.status = status;
        this.time = time;
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
