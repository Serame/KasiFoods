package dev.com.jtd.toodles.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by smoit on 2016/12/31.
 */

public class BunniesCartItem implements Serializable {

    private Bunny bunny;

    private int bunnySessionID;
    private char parentInd;
    private int parentID; //This will be greater than 0 if the item is not a bunny and the parentID will be the bunnySessionID of the parent
    private ArrayList<BunnyIngredients> added;
    private ArrayList<BunnyIngredients> subtracted;
    public static final String CLASS_NAME = "BunniesCartItem";
    private double bunnyItemTotal;
    private double totalWithAddedItems;
    private double addedItemsTotal;

    public BunniesCartItem(Bunny bunny,ArrayList<String>  additions,ArrayList<String>  subtractions) {

        this.bunny = bunny;

        this.bunnyItemTotal = bunny.getPrice();
        this.totalWithAddedItems = bunny.getPrice();
    }

    public Bunny getBunny() {
        return bunny;
    }

    public void setBunny(Bunny bunny) {
        this.bunny = bunny;
    }

    public void addToAddedItems(double amount)
    {
        this.addedItemsTotal+= amount;
    }


    public double getAddedItemsTotal() {
        return addedItemsTotal;
    }

    public void setAddedItemsTotal(double addedItemsTotal) {
        this.addedItemsTotal = addedItemsTotal;
    }

    public void setBunnyItemTotal(double total)
    {

        this.bunnyItemTotal+=total;

    }

    public double getTotalWithAddedItems() {
        return totalWithAddedItems;
    }

    public void setTotalWithAddedItems(double totalWithAddedItems) {
        this.totalWithAddedItems = totalWithAddedItems;
    }

    public char getParentInd() {
        return parentInd;
    }

    public void setParentInd(char parentInd) {
        this.parentInd = parentInd;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getBunnySessionID() {
        return bunnySessionID;
    }

    public void setBunnySessionID(int bunnySessionID) {
        this.bunnySessionID = bunnySessionID;
    }

    public double getBunnyItemTotal()
    {
        return this.bunnyItemTotal;
    }

    @Override
    public String toString() {
        return "BunniesCartItem{" +
                "bunny=" + bunny.toString() +
                ", bunnySessionID=" + bunnySessionID +
                ", parentInd=" + parentInd +
                ", parentID=" + parentID +
                ", added=" + added +
                ", subtracted=" + subtracted +
                ", bunnyItemTotal=" + bunnyItemTotal +
                ", totalWithAddedItems=" + totalWithAddedItems +
                '}';
    }
}
