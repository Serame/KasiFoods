package dev.com.jtd.toodles.model;

public class Shop {

    private int shopID;
    private String shopName;
    private String shopAddress;
    private int kasiID;

    public Shop() {
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public int getKasiID() {
        return kasiID;
    }

    public void setKasiID(int kasiID) {
        this.kasiID = kasiID;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "shopID=" + shopID +
                ", shopName='" + shopName + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", kasiID=" + kasiID +
                '}';
    }
}
