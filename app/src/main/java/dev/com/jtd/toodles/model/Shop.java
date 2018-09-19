package dev.com.jtd.toodles.model;

public class Shop {

    private int shopID;
    private String shopName;
    private String shopAddress;
    private String shopCoodinates;
    private int shopOwnerID;
    private String shopFCMToken;
    private int kasiID;

    private final String SHOP_ID = "SHOP_ID";
    private final String KASI_ID = "KASI_ID";
    private final String SHOP_NAME = "SHOPNAME";
    private final String SHOPADDRESS = "SHOPADDRESS";
    private final String SHOPCOORDINATES = "SHOPCOORDINATES";
    private final String SHOPOWNERID = "SHOPOWNERID";
    private final String SHOPFCMTOKEN = "SHOPFCMTOKEN";


    public Shop() {
    }

    public int getShopOwnerID() {
        return shopOwnerID;
    }

    public void setShopOwnerID(int shopOwnerID) {
        this.shopOwnerID = shopOwnerID;
    }

    public String getShopFCMToken() {
        return shopFCMToken;
    }

    public void setShopFCMToken(String shopFCMToken) {
        this.shopFCMToken = shopFCMToken;
    }

    public String getShopCoodinates() {
        return shopCoodinates;
    }

    public void setShopCoodinates(String shopCoodinates) {
        this.shopCoodinates = shopCoodinates;
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
                ", shopCoodinates='" + shopCoodinates + '\'' +
                ", shopOwnerID=" + shopOwnerID +
                ", shopFCMToken='" + shopFCMToken + '\'' +
                ", kasiID=" + kasiID +
                '}';
    }
}
