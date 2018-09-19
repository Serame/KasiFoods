package dev.com.jtd.toodles.model;

import java.util.List;

public class Kasi {

    private int kasiID;
    private String kasiName;
    private String kasiCordinates;
    private String kasiAddress;
    private List<Shop> listShops;

    private final String KASI_ID = "KASI_ID";
    private final String KASINAME = "KASINAME";
    private final String KASIADDRESS = "KASIADDRESS";
    private final String KASICOORDINATES = "KASICOORDINATES";


    public Kasi() {
    }

    public String getKasiAddress() {
        return kasiAddress;
    }

    public void setKasiAddress(String kasiAddress) {
        this.kasiAddress = kasiAddress;
    }

    public int getKasiID() {
        return kasiID;
    }

    public void setKasiID(int kasiID) {
        this.kasiID = kasiID;
    }

    public String getKasiName() {
        return kasiName;
    }

    public void setKasiName(String kasiName) {
        this.kasiName = kasiName;
    }

    public String getKasiCordinates() {
        return kasiCordinates;
    }

    public void setKasiCordinates(String kasiCordinates) {
        this.kasiCordinates = kasiCordinates;
    }

    public List<Shop> getListShops() {
        return listShops;
    }

    public void setListShops(List<Shop> listShops) {
        this.listShops = listShops;
    }

    @Override
    public String toString() {
        return "Kasi{" +
                "kasiID=" + kasiID +
                ", kasiName='" + kasiName + '\'' +
                ", kasiCordinates='" + kasiCordinates + '\'' +
                ", kasiAddress='" + kasiAddress + '\'' +
                '}';
    }
}
