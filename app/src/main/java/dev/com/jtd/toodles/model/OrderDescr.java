package dev.com.jtd.toodles.model;

import java.io.Serializable;

/**
 * Created by smoit on 2017/02/25.
 */

public class OrderDescr implements Serializable {


    private int orderid; //This orderid will be generated by the system when order is place, at initialization we will pass 0
    private long custormerID; //This wil be stored in the app shared preferences
    private int itemID;
    private int extrasID; //Only if extras have been added
    private int removedID; //Only if there are items removed
    private double price;
    private int cartPosition;
    private int parentID;
    private char parentIND;
    private double addedItemsTotal;
    private String appToken; //This is the firebase token which will be used by the sever to send notifications once an order has been completed.

    public OrderDescr(int orderid, long custormerID, int itemID, int extrasID, int removedID, double price, double addedItemsTotal, int cartPosition,int parentID,char parentIND,String appTokem) {
        this.orderid = orderid;
        this.custormerID = custormerID;
        this.itemID = itemID;
        this.extrasID = extrasID;
        this.removedID = removedID;
        this.price = price;
        this.addedItemsTotal = addedItemsTotal;
        this.cartPosition = cartPosition;
        this.parentID = parentID;
        this.parentIND = parentIND;
        this.appToken = appTokem;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public long getCustormerID() {
        return custormerID;
    }

    public void setCustormerID(long custormerID) {
        this.custormerID = custormerID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getExtrasID() {
        return extrasID;
    }

    public void setExtrasID(int extrasID) {
        this.extrasID = extrasID;
    }

    public int getRemovedID() {
        return removedID;
    }

    public void setRemovedID(int removedID) {
        this.removedID = removedID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAddedItemsTotal() {
        return addedItemsTotal;
    }

    public void setAddedItemsTotal(double addedItemsTotal) {
        this.addedItemsTotal = addedItemsTotal;
    }

    public int getCartPosition() {
        return cartPosition;
    }

    public void setCartPosition(int cartPosition) {
        this.cartPosition = cartPosition;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public char getParentIND() {
        return parentIND;
    }

    public void setParentIND(char parentIND) {
        this.parentIND = parentIND;
    }

    @Override
    public String toString() {
        return "OrderDescr{" +
                "orderid=" + orderid +
                ", custormerID=" + custormerID +
                ", itemID=" + itemID +
                ", extrasID=" + extrasID +
                ", removedID=" + removedID +
                ", price=" + price +
                ", cartPosition=" + cartPosition +
                ", parentID=" + parentID +
                ", parentIND=" + parentIND +
                ", addedItemsTotal=" + addedItemsTotal +
                ", appToken='" + appToken + '\'' +
                '}';
    }
}