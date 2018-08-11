package dev.com.jtd.toodles.model;

import java.io.Serializable;

/**
 * Created by serame on 7/16/2016.
 */
public class Bunny implements Serializable {

    public static final String ITEM_ID = "ITEM_ID";
    public static final String PRICE = "PRICE";
    public static final String DESCR = "DESCR";
    public static final String NAME = "NAME";

    private String name;
    private int item_id;
    private String descr;
    private double price;

    public static final int ITEMTYPE_INGREDIENT = 2;
    public static final int ITEMTYPE_BUNNY = 1;


    public Bunny(int item_id, String descr, double price) {
        this.item_id = item_id;
        this.descr = descr;
        this.price = price;
    }

    public Bunny()
    {

    }

    public String getName() {
        return name;
    }

    public int getItem_id() {
        return item_id;
    }

    public String getDescr() {
        return descr;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString()
    {
        return "item_id: "+ getItem_id()+" Description: "+getDescr()+" Price: "+getPrice()+" Name: "+getName();
    }
}
