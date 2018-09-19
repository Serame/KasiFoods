package toodles.com.za.work.dao;

import com.google.appengine.api.utils.SystemProperty;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import toodles.com.za.work.model.Kasi;
import toodles.com.za.work.model.Shop;

public class KasiFoodsDBReader {

    private Connection con;
    private CallableStatement cStmt;
    private ResultSet rs;
    private String sqlStatment;

    //KASI TABLE NAME AND COLUMN NAMES
    private final String KASI_TABLE = "KASI";
    private final String KASI_ID = "KASI_ID";
    private final String KASINAME = "KASINAME";
    private final String KASIADDRESS = "KASIADDRESS";
    private final String KASICOORDINATES = "KASICOORDINATES";
    private final String USERNAME = "USERNAME";
    private final String TMSTAMP = "TMSTAMP";

    //SHOP TABLE NAME AND COLUMN NAMES
    private final String SHOP_ID = "SHOP_ID";
    private final String SHOP_NAME = "SHOPNAME";
    private final String SHOPADDRESS = "SHOPADDRESS";
    private final String SHOPCOORDINATES = "SHOPCOORDINATES";
    private final String SHOPOWNERID = "SHOPOWNERID";
    private final String SHOPFCMTOKEN = "SHOPFCMTOKEN";
    private final String SHOPDBSTRING = "SHOPDBSTRING";



    public KasiFoodsDBReader() throws ClassNotFoundException, SQLException {

        String url;

        if(SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {

            Class.forName("com.mysql.jdbc.GoogleDriver");
            url = "jdbc:google:mysql://xenon-lantern-213109:us-central1:uat-kasifood02u/kasifoodsdb";
            //url = "jdbc:mysql://google/toodlesdb?cloudSqlInstance=xenon-lantern-213109:us-central1:uat-kasifood02u&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=root&password=Aseblief45@&useSSL=false";
            //xenon-lantern-213109:us-central1:uat-kasifood02u
            System.out.println("Using prod environment values");

        }
        else
        {
            Class.forName("com.mysql.jdbc.Driver");
            url = "jdbc:mysql://35.192.79.108:3306/kasifoodsdb";
            System.out.println("Using dev machine environment values");



        }
        con = DriverManager.getConnection(url,"root","Aseblief45@");

    }

    public List<Kasi> getKasiList() throws SQLException {

        ArrayList<Kasi> kasiList = new ArrayList<>();
        sqlStatment = "Select * from KASI";
        Statement stm = con.createStatement();
        rs = stm.executeQuery(sqlStatment);
        Kasi kasi;

        while(rs.next())
        {
            kasi = new Kasi();
            kasi.setKasiID(rs.getInt(this.KASI_ID));
            kasi.setKasiName(rs.getString(this.KASINAME));
            kasi.setKasiAddress((rs.getString(this.KASIADDRESS)));
            kasi.setKasiCordinates(rs.getString(this.KASICOORDINATES));
            kasiList.add(kasi);

        }

        return kasiList;

    }

    public HashMap<String,List> getShopsListWithKasi() throws SQLException {
        HashMap<String,List> shopListWithKasi = new HashMap<>();
        List<Kasi> kasiList = (ArrayList<Kasi>) this.getKasiList();
        List<Shop> kasiShops;
        List<Shop> shopList = new ArrayList<>();
        Shop shop;
        sqlStatment = "SELECT * FROM SHOP";
        Statement stm = con.createStatement();
        rs = stm.executeQuery(sqlStatment);

        while(rs.next())
        {
            shop = new Shop();
            shop.setShopID(rs.getInt(this.SHOP_ID));
            shop.setKasiID(rs.getInt(this.KASI_ID));
            shop.setShopName(rs.getString(this.SHOP_NAME));
            shop.setShopAddress(rs.getString(this.SHOPADDRESS));
            shop.setShopCoodinates(rs.getString(this.SHOPCOORDINATES));
            shop.setShopFCMToken(rs.getString(this.SHOPFCMTOKEN));
            shop.setShopOwnerID(rs.getInt(this.SHOPOWNERID));

            shopList.add(shop);
        }

        for(Kasi k: kasiList)
        {
            kasiShops = new ArrayList<>();

            for(Shop s :shopList)
            {
                if(k.getKasiID() == s.getKasiID())
                {
                    kasiShops.add(s);
                }
            }
            shopListWithKasi.put(k.getKasiName(),kasiShops);

        }

        return  shopListWithKasi;


    }

    public static void main(String[] args)
    {
        try {
            KasiFoodsDBReader kfd = new KasiFoodsDBReader();
            HashMap<String,List> hm = kfd.getShopsListWithKasi();
            for(String key : hm.keySet())
            {
                ArrayList<Shop> listShops = (ArrayList<Shop>) hm.get(key);
                for(Shop shop :listShops)
                {
                    System.out.println("Kasi Name: "+key+"\n Shop Details: "+shop.toString());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
