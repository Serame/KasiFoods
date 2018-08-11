package toodles.com.za.work.model;

/**
 * Created by smoit on 2016/12/30.
 * This class represents a single record of the bunniesIngredients table linked with the ingredients table in the Toodlesdb.
 * This table is a mapping of the Menu table and the Ingredients table
 */

public class BunnyIngredients {

    private int bunny_id;
    private int ingr_id;
    private String descr;
    private double price;

    public BunnyIngredients(int bunny_id, int ingr_id, String descr,double price) {
        this.bunny_id = bunny_id;
        this.ingr_id = ingr_id;
        this.descr = descr;
        this.price = price;
    }

    public double getPrice()
    {
        return this.price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }


    public int getBunny_id() {
        return bunny_id;
    }

    public void setBunny_id(int bunny_id) {
        this.bunny_id = bunny_id;
    }

    public int getIngr_id() {
        return ingr_id;
    }

    public void setIngr_id(int ingr_id) {
        this.ingr_id = ingr_id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Override
    public String toString() {
        return "BunnyIngredients{" +
                "bunny_id=" + bunny_id +
                ", ingr_id=" + ingr_id +
                ", descr='" + descr +
                "price= "+price+'\'' +
                '}';
    }
}
