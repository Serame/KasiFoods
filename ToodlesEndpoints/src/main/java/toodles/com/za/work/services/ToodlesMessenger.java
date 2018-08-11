package toodles.com.za.work.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import toodles.com.za.work.dao.ToodlesDBReader;
import toodles.com.za.work.model.OrderDescr;
import toodles.com.za.work.model.PlacedOrders;

/**
 * Created by smoit on 2017/06/01.
 */

public class ToodlesMessenger {

    private int respCode;
    private  URL url;

    public ToodlesMessenger() throws MalformedURLException {

        this.respCode = 0;
        this.url = new URL("https://fcm.googleapis.com/fcm/send");
    }

    public int sendCompletedOrder(ArrayList<PlacedOrders> placedOrders,String token)
    {
        //int respCode = 0;


        try {
            //String clienttoken = placedOrders.get(0).getAppToken();
            String clienttoken = token;
            System.out.println("Client token: "+clienttoken);
            JsonObject data = new JsonObject();
            JsonObject placeOrderJSON = null;
            JsonArray orders = new JsonArray();
            for(PlacedOrders pl : placedOrders)
            {
                /*public PlacedOrders(int orderID, int custID, String custName,
                        String itemName, double price, int parentChildLink,
                        int parentID, String parentIND, String itemDescr, double totamount)*/
                System.out.println("PL is \n"+pl.toString());
                placeOrderJSON = new JsonObject();
                placeOrderJSON.addProperty(PlacedOrders.CUST_ID,pl.getCustID());
                placeOrderJSON.addProperty(PlacedOrders.ORDER_ID,pl.getOrderID());
                placeOrderJSON.addProperty(PlacedOrders.CUST_NAME,pl.getCustName());
                placeOrderJSON.addProperty(PlacedOrders.ITEM_NAME,pl.getItemName());
                placeOrderJSON.addProperty(PlacedOrders.PRICE,pl.getPrice());
                placeOrderJSON.addProperty(PlacedOrders.PARENT_CHILD_LINK,pl.getParentChildLink());
                placeOrderJSON.addProperty(PlacedOrders.PARENT_ID,pl.getParentID());
                placeOrderJSON.addProperty(PlacedOrders.PARENT_IND,pl.getParentIND());
                placeOrderJSON.addProperty(PlacedOrders.DESCR,pl.getItemDescr());
                placeOrderJSON.addProperty(PlacedOrders.TOT_AMOUNT,pl.getTotamount());
                placeOrderJSON.addProperty(PlacedOrders.COMPLETION_DATE_TIME,pl.getCompletionDate());
                placeOrderJSON.addProperty(PlacedOrders.ADDED_ITEMS,pl.getAddedItems());
                placeOrderJSON.addProperty(PlacedOrders.REMOVED_ITEMS,pl.getRemovedItems());

                orders.add(placeOrderJSON);

            }

            data.add("placed_orders",orders);
            //URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Authorization","key=AIzaSyCa41BHOQ1ZHJkSYWjcxw3FbytGR10YBas");
            JsonObject message = new JsonObject();
            JsonObject notification = new JsonObject();

            notification.addProperty("body","Re heditse go dira kota ya go");
            notification.addProperty("title","KotaTime");
            notification.addProperty("sound","default");
            notification.addProperty("priority","high");

            message.add("notification",notification);
            message.add("data",data);

            message.addProperty("to",clienttoken);

            OutputStream os = conn.getOutputStream();
            os.write(message.toString().getBytes());
            os.flush();
            os.close();

            respCode = conn.getResponseCode();
            System.out.println("HTTP Respose is "+respCode);
            System.out.println(data.toString());
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String test = br.readLine();
            br.close();

            //System.out.println(test);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return respCode;
    }

    public int sendNewOrderToShop(String clienttoken,int orderID ) throws SQLException, IOException, ClassNotFoundException, JSONException {

        System.out.println(clienttoken);

        ArrayList<PlacedOrders> pl = (ArrayList<PlacedOrders>) new ToodlesDBReader().getAllPlacedOrders("N",orderID);
        Gson gsonPlacedOrder = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();
        String strPlaceOrder = gsonPlacedOrder.toJson(pl);
        JsonArray orders = gsonPlacedOrder.toJsonTree(pl).getAsJsonArray();
        JsonObject data = new JsonObject();
        data.add("placed_orders",orders);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestProperty("Authorization","key=AIzaSyCa41BHOQ1ZHJkSYWjcxw3FbytGR10YBas");
        JsonObject message = new JsonObject();
        JsonObject notification = new JsonObject();

        notification.addProperty("body","Re heditse go dira kota ya go");
        notification.addProperty("title","KotaTime");
        notification.addProperty("sound","default");
        notification.addProperty("priority","high");

        message.add("notification",notification);
        message.add("data",data);

        message.addProperty("to",clienttoken);

        OutputStream os = conn.getOutputStream();
        os.write(message.toString().getBytes());
        os.flush();
        os.close();

        respCode = conn.getResponseCode();
        System.out.println("HTTP Respose is "+respCode);
        System.out.println(data.toString());
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String test = br.readLine();
        br.close();


        return respCode;
    }

    public static void main(String[] args)  {
        try {
            ToodlesDBReader td = new ToodlesDBReader();
            String token = td.getShopToken(1);
            new ToodlesMessenger().sendNewOrderToShop(token,1180519);
        } catch (SQLException | IOException | JSONException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
