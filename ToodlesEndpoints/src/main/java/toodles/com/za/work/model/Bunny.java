package toodles.com.za.work.model;

/**
 * Created by serame on 7/16/2016.
 */
public class Bunny {

    public static final String ITEM_ID = "ITEM_ID";
    public static final String PRICE = "PRICE";
    public static final String DESCR = "DESCR";
    public static final String NAME = "NAME";

    private String name;
    private int item_id;
    private String descr;
    private double price;

    public Bunny(int ID, String descr, double price) {
        this.item_id = ID;
        this.descr = descr;
        this.price = price;
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


    public static void main(String[] args)
    {
        System.out.println("Hello World");
    }
}
