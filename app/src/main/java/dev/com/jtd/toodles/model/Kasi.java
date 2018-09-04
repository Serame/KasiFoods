package dev.com.jtd.toodles.model;

public class Kasi {

    private int kasiID;
    private String kasiName;
    private String kasiCordinates;

    public Kasi() {
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

    @Override
    public String toString() {
        return "Kasi{" +
                "kasiID=" + kasiID +
                ", kasiName='" + kasiName + '\'' +
                ", kasiCordinates='" + kasiCordinates + '\'' +
                '}';
    }
}
