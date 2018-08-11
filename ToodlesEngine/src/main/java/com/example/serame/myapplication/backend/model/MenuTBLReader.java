package com.example.serame.myapplication.backend.model;

import com.mysql.jdbc.CallableStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * Created by serame on 7/16/2016.
 */
public class MenuTBLReader {

    private Connection con;
    private Statement stm;

    public MenuTBLReader() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        String connection = "jdbc:mysql://localhost:3306/toodlesdb";
        this.con = DriverManager.getConnection(connection,"root","Aseblief45@");
       // stm = con.createStatement();
    }

    public ArrayList<Bunny> GetMenuItemss() throws SQLException {
        ArrayList<Bunny> BunniesList =  null;
        //stm = con.createStatement();
        String query = "{?=CALL SP_ALL_MENU_ITEMS()}";
        java.sql.CallableStatement cStmt = this.con.prepareCall(query);
        ResultSet rs = cStmt.executeQuery();
        if(rs.next())
        {
            Bunny bunny;
            BunniesList = new ArrayList<Bunny>();
            while(rs.next())
            {
                bunny = new Bunny(rs.getInt(Bunny.ITEM_ID),rs.getString(Bunny.DESCR),rs.getDouble(Bunny.PRICE));
                BunniesList.add(bunny);
            }
        }
        rs.close();
        stm.close();
        return BunniesList;
    }

    public ArrayList<Bunny> GetMenuItems() throws SQLException {
        ArrayList<Bunny> BunniesList =  null;
        stm = con.createStatement();
        String query = "SELECT * FROM MENU ORDER BY 1";
        ResultSet rs = stm.executeQuery(query);
        if(rs.next())
        {
            Bunny bunny;
            BunniesList = new ArrayList<Bunny>();
            while(rs.next())
            {
                bunny = new Bunny(rs.getInt(Bunny.ITEM_ID),rs.getString(Bunny.DESCR),rs.getDouble(Bunny.PRICE));
                BunniesList.add(bunny);
            }
        }
        rs.close();
        stm.close();
        return BunniesList;
    }


    public static void main(String[] args )
    {
        try {
            ArrayList<Bunny> list = new MenuTBLReader().GetMenuItems();

            for(int x =0; x < list.size(); x++)
            {
                System.out.println(list.get(x).getDescr());
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
