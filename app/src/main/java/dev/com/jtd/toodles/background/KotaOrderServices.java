package dev.com.jtd.toodles.background;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dev.com.jtd.toodles.R;
import dev.com.jtd.toodles.model.PlacedOrders;
import dev.com.jtd.toodles.view.LoginActivity;

/**
 * Created by smoit on 2017/05/13.
 */

public class KotaOrderServices extends FirebaseMessagingService {

    public static final int NOTIFICATION_ID = 001;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.w("Title",remoteMessage.toString());

        if(remoteMessage.getNotification() == null)
        {
            Log.w("Notification","Notificatin is null");
        }
        else
        {
            createNotification(remoteMessage);
        }

        if(remoteMessage.getData().size() > 0)
        {
            Gson gson = new GsonBuilder().disableHtmlEscaping()
                    .setPrettyPrinting()
                    .serializeNulls().create();
            Log.w("NotificationData","Notification is not null");
            Log.w("NotificationData",remoteMessage.getData().toString());

            JsonParser parser = new JsonParser();
            JsonObject data = parser.parse(remoteMessage.getData().toString()).getAsJsonObject();
            JsonArray placedOrderJsonArray = data.getAsJsonArray("placed_orders");
            ArrayList<PlacedOrders> orderHistoryArray = new ArrayList<>();
            ToodlesDBAO dbao = new ToodlesDBAO(this);
            for(int x = 0; x< placedOrderJsonArray.size();x++)
            {
                //PlacedOrders placedOrder = gson.fromJson(placedOrderJsonArray.get(x).getAsString(),PlacedOrders.class);
                /*Currently the above manner of passing my JSONObject into java object is not working properly
                    I believe this to be because of the inconsistency between then naming of the class's method's and
                     static fields which describe the properties of the class
                 */

                JsonObject order = placedOrderJsonArray.get(x).getAsJsonObject();
                PlacedOrders pl = new PlacedOrders();
                pl.setOrderID(order.get(PlacedOrders.ORDER_ID).getAsInt());
                pl.setCustID(order.get(PlacedOrders.CUST_ID).getAsInt());
                pl.setCustName(order.get(PlacedOrders.CUST_NAME).getAsString());
                pl.setItemName(order.get(PlacedOrders.ITEM_NAME).getAsString());
                pl.setPrice(order.get(PlacedOrders.PRICE).getAsDouble());
                pl.setParentChildLink((order.get(PlacedOrders.PARENT_CHILD_LINK).getAsInt()));
                pl.setParentID(order.get(PlacedOrders.PARENT_ID).getAsInt());
                pl.setParentIND(order.get(PlacedOrders.PARENT_IND).getAsString());
                pl.setItemDescr(order.get(PlacedOrders.DESCR).getAsString());
                pl.setTotamount(order.get(PlacedOrders.TOT_AMOUNT).getAsDouble());
                pl.setCompletionDate(order.get(PlacedOrders.COMPLETION_DATE_TIME).getAsString());
                pl.setAddedItems(order.get(PlacedOrders.ADDED_ITEMS).getAsString());
                pl.setRemovedItems(order.get(PlacedOrders.REMOVED_ITEMS).getAsString());

                orderHistoryArray.add(pl);
                Log.w("PlaceOrder",pl.toString());
            }

            dbao.insertOrderHistory(orderHistoryArray);





        }

       // Log.w("Message",remoteMessage.getNotification().getBody());
         //createNotification(remoteMessage);

    }

    public void addToOrderHistory()
    {

    }

    public void createNotification(RemoteMessage remoteMessage)
    {
        Log.w("ToodlesNotification","Building Notification");
        Log.w("ToodlesNotitifcatoin",remoteMessage.getNotification().getBody());
        Log.w("ToodlesNotificatin",remoteMessage.getData().toString());
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(remoteMessage.getNotification().getTitle());
        notificationBuilder.setSmallIcon(R.drawable.kotatime_notification);

        notificationBuilder.setContentText(remoteMessage.getNotification().getBody());
        notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        Intent notificationIntent  = new Intent(this, LoginActivity.class);
        PendingIntent pintent = PendingIntent.getActivity(this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pintent);
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_ID,notificationBuilder.build());

    }
}
