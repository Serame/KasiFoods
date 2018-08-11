package com.example.serame.myapplication.backend.model;

/**
 * Created by serame on 7/16/2016.
 */
public class Bunny {

    public static final String ITEM_ID = "ITEM_ID";
    public static final String PRICE = "PRICE";
    public static final String DESCR = "DESCR";
    public static final String NAME = "NAME";

    private String name;
    private int ID;
    private String descr;
    private double price;

    public Bunny(int ID, String descr, double price) {
        this.ID = ID;
        this.descr = descr;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
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

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static void main(String[] args)
    {
        System.out.println("Hello World");
    }
}
