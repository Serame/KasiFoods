package dev.com.jtd.toodles.background;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import dev.com.jtd.toodles.model.PlacedOrders;

/**
 * Created by smoit on 2017/06/12.
 */

public class ToodlesDBAO {


    private Context context;
    private ToodlesDBHelper dbHelper;

    public ToodlesDBAO(Context ctx) {

        this.context = ctx;
        this.dbHelper = new ToodlesDBHelper(ctx);

    }

    public void test() {
        Log.w("ToodlesDB is open", this.dbHelper.getReadableDatabase().isOpen() + "");
        Log.w("ToodlesDB is open", this.dbHelper.getDatabaseName());
        Log.w("ToodlesDB is open", this.dbHelper.toString());

    }

    public ArrayList<PlacedOrders> getSingleOrders(int orderID)
    {
        ArrayList<PlacedOrders> singleOrders = new ArrayList<>();
        PlacedOrders pl = null;
        Cursor cur;
        if(orderID == 0)
        {
            cur = dbHelper.getReadableDatabase().query(ToodlesDBHelper.ORDERS_TABLE_NAME,null,null,null,null,null,null);

        }
        else
        {
            cur = dbHelper.getReadableDatabase().query(ToodlesDBHelper.ORDERS_TABLE_NAME,null,ToodlesDBHelper.ORDER_ID+" = ?",new String[]{String.valueOf(orderID)},null,null,null);

        }




        while(cur.moveToNext())
        {
            pl = new PlacedOrders();
            pl.setOrderID(cur.getInt(cur.getColumnIndex(ToodlesDBHelper.ORDER_ID)));
            pl.setCompletionDate(cur.getString(cur.getColumnIndex(ToodlesDBHelper.ORDER_DATE)));
            pl.setTotamount(cur.getDouble(cur.getColumnIndex(ToodlesDBHelper.TOT_AMOUNT)));
            Log.w("SingleOrder",cur.getDouble(cur.getColumnIndex(ToodlesDBHelper.TOT_AMOUNT))+" "+cur.getString(cur.getColumnIndex(ToodlesDBHelper.ORDER_DATE)));
            singleOrders.add(pl);

        }

        return singleOrders;
    }

    public void insertOrderHistory(ArrayList<PlacedOrders> placedOrders) {

        Log.w("insertOrderHistory", "Start");
        ContentValues cvORders = new ContentValues();
        ContentValues cvOrdersDescrs = new ContentValues();

        int ids[] = new int[placedOrders.size()];
        int f = 0;
        for (PlacedOrders p : placedOrders) {
            ids[f] = p.getOrderID();
            f++;


        }

        Set<Integer> setString = new LinkedHashSet<Integer>();
        for (int i = 0; i < ids.length; i++) {
            setString.add(ids[i]);
        }

        f = 0;
        long ordersResults;
        for (int jj : setString) {
            cvORders.put(ToodlesDBHelper.ORDER_ID, jj);
            cvORders.put(ToodlesDBHelper.ORDER_DATE, placedOrders.get(0).getCompletionDate());
            cvORders.put(ToodlesDBHelper.TOT_AMOUNT, placedOrders.get(0).getTotamount());
            ordersResults = dbHelper.getWritableDatabase().insert(ToodlesDBHelper.ORDERS_TABLE_NAME, null, cvORders);
            f++;

        }

        for (PlacedOrders pl : placedOrders) {

            cvOrdersDescrs.put(ToodlesDBHelper.ORDER_ID, pl.getOrderID());
            cvOrdersDescrs.put(ToodlesDBHelper.CUST_ID, pl.getCustID());
            cvOrdersDescrs.put(ToodlesDBHelper.CUST_NAME, pl.getCustName());
            cvOrdersDescrs.put(ToodlesDBHelper.EMAIL, " ");
            cvOrdersDescrs.put(ToodlesDBHelper.ITEM_NAME, pl.getItemName());
            cvOrdersDescrs.put(ToodlesDBHelper.DESCR, pl.getItemDescr());
            cvOrdersDescrs.put(ToodlesDBHelper.PRICE, pl.getPrice());
            cvOrdersDescrs.put(ToodlesDBHelper.PARENT_CHILD_LINK, pl.getParentChildLink());
            cvOrdersDescrs.put(ToodlesDBHelper.PARENT_IND, pl.getParentIND());
            cvOrdersDescrs.put(ToodlesDBHelper.PARENT_ID, pl.getParentID());

            long ordersDescrResults = dbHelper.getWritableDatabase().insert(ToodlesDBHelper.ORDERS_DESCR_TABLE_NAME, null, cvOrdersDescrs);


        }

        Log.w("insertOrderHistory", "Finish");

    }

    public int[] getDistinctOrderIDs() {
        String[] projection = {ToodlesDBHelper.ORDER_ID};
        Cursor cursor = dbHelper.getReadableDatabase().query(true, ToodlesDBHelper.ORDERS_TABLE_NAME, projection,
                null, null, null, null, null, null);
        int[] orderIDs = null;
        int x = 0;
        orderIDs = new int[cursor.getCount()];
        Log.w("CusorLength", "" + cursor.getCount());
        while (cursor.moveToNext()) {
            orderIDs[x] = cursor.getInt(cursor.getColumnIndex(PlacedOrders.ORDER_ID));
            x++;
        }

        return orderIDs;
    }

    public ArrayList<PlacedOrders> getORderHistory() {

        ArrayList<PlacedOrders> orderHistory = null;
        String[] projectionOrderDescr = {ToodlesDBHelper.ORDER_ID,
                ToodlesDBHelper.CUST_ID,
                ToodlesDBHelper.CUST_NAME,
                ToodlesDBHelper.ITEM_NAME,
                ToodlesDBHelper.PRICE,
                ToodlesDBHelper.PARENT_CHILD_LINK,
                ToodlesDBHelper.PARENT_ID,
                ToodlesDBHelper.PARENT_IND,
                ToodlesDBHelper.DESCR
        };
        String[] projectionOrders = {PlacedOrders.ORDER_ID,
                ToodlesDBHelper.ORDER_DATE,
                ToodlesDBHelper.TOT_AMOUNT};
        Cursor orderCursor = dbHelper.getReadableDatabase().query(ToodlesDBHelper.ORDERS_TABLE_NAME, null, null,
                null, null, null, null);
        PlacedOrders tempPlacedOrders = null;

        orderHistory = new ArrayList<>();
        Cursor oDesCur = null;
        int orderIDS[] = new int[orderCursor.getCount()];
        int x = 0;

        ArrayList<PlacedOrders> tempOdHis = new ArrayList<>();

        while (orderCursor.moveToNext()) {
            orderIDS[x] = orderCursor.getInt(orderCursor.getColumnIndex(PlacedOrders.ORDER_ID));
            x++;
            tempPlacedOrders = new PlacedOrders();
            tempPlacedOrders.setOrderID(orderCursor.getInt(orderCursor.getColumnIndex(ToodlesDBHelper.ORDER_ID)));
            tempPlacedOrders.setTotamount(orderCursor.getDouble(orderCursor.getColumnIndex(ToodlesDBHelper.TOT_AMOUNT)));
            tempPlacedOrders.setCompletionDate(orderCursor.getString(orderCursor.getColumnIndex(ToodlesDBHelper.ORDER_DATE)));
            tempOdHis.add(tempPlacedOrders);
        }


        for (PlacedOrders p : tempOdHis) {

            Cursor oDesCursor = dbHelper.getReadableDatabase().query(ToodlesDBHelper.ORDERS_DESCR_TABLE_NAME, null,
                    ToodlesDBHelper.ORDER_ID + " = ?", new String[]{String.valueOf(p.getOrderID())}, null, null, null);
            PlacedOrders placedOrder;
            while (oDesCursor.moveToNext()) {

                if (p.getOrderID() == oDesCursor.getInt(oDesCursor.getColumnIndex(ToodlesDBHelper.ORDER_ID)))
                    ;
                {

                    placedOrder = new PlacedOrders();
                    placedOrder.setOrderID(p.getOrderID());
                    placedOrder.setCompletionDate(p.getCompletionDate());
                    placedOrder.setCustName(oDesCursor.getString(oDesCursor.getColumnIndex(ToodlesDBHelper.CUST_NAME)));
                    placedOrder.setCustID(oDesCursor.getInt(oDesCursor.getColumnIndex(ToodlesDBHelper.CUST_ID)));
                    placedOrder.setItemName(oDesCursor.getString(oDesCursor.getColumnIndex(ToodlesDBHelper.ITEM_NAME)));
                    placedOrder.setItemDescr(oDesCursor.getString(oDesCursor.getColumnIndex(ToodlesDBHelper.DESCR)));
                    placedOrder.setPrice(oDesCursor.getDouble(oDesCursor.getColumnIndex(ToodlesDBHelper.PRICE)));
                    placedOrder.setParentChildLink(oDesCursor.getInt(oDesCursor.getColumnIndex(ToodlesDBHelper.PARENT_CHILD_LINK)));
                    placedOrder.setParentID(oDesCursor.getInt(oDesCursor.getColumnIndex(ToodlesDBHelper.PARENT_ID)));
                    placedOrder.setParentIND(oDesCursor.getString(oDesCursor.getColumnIndex(ToodlesDBHelper.PARENT_IND)));

                    orderHistory.add(placedOrder);
                }

            }

        }

        return orderHistory;

    }

    public void deleteAllOrders() {
        dbHelper.getWritableDatabase().delete(ToodlesDBHelper.ORDERS_TABLE_NAME, null, null);
        dbHelper.getWritableDatabase().delete(ToodlesDBHelper.ORDERS_DESCR_TABLE_NAME, null, null);

        Log.w("DeleteOrders", "All Orders Deleted");
    }

    public PlacedOrders getOrderHistoryString(int orderID) {
        ArrayList<PlacedOrders> orders = this.getORderHistory();
        ArrayList<PlacedOrders> singleOrders = this.getSingleOrders(orderID);

        StringBuffer orderDescr = new StringBuffer("");
        String test = "";
        ArrayList<String> ordersString = new ArrayList<>();
        Log.w("OrderHistString","Getting ORder history string");


        for (PlacedOrders pl : orders) {

            if (pl.getOrderID() == orderID) {

                       /* orderDescr.append(Html.fromHtml("<b>Kota Name: </b>" + pl.getItemName() + "<br>" +
                                "<b>Descr: </b>" + pl.getItemDescr() + "<br>" +
                                "<b>Added Items: </b>" + pl.getAddedItems() + "<br>" +
                                "<b>Removed Items: </b>" + pl.getRemovedItems() + "<br>" +
                                "<b>Price: </b>" + pl.getPrice() + "<br>" +
                                "<br><br>").toString());*/

                //test = Html.fromHtml("Kota Name: "+pl.getItemName()+"<br> Descr: "+pl.getItemDescr()).toString();
                test = Html.fromHtml("<b>Kota Name: </b>" + pl.getItemName() + "<br>" +
                        "<b>Descr: </b>" + pl.getItemDescr() + "<br>" +
                        "<b>Added Items: </b>" + pl.getAddedItems() + "<br>" +
                        "<b>Removed Items: </b>" + pl.getRemovedItems() + "<br>" +
                        "<b>Price: </b>" + pl.getPrice() + "<br>" +
                        "<br>").toString();
                ordersString.add(test);

            }

        }

        int x = 0;
        for (String s : ordersString) {
            orderDescr.append(s);
            x++;
        }

        singleOrders.get(0).setOrderDescription(orderDescr);
        return  singleOrders.get(0);

    }

    private class ToodlesDBHelper extends SQLiteOpenHelper {
        private Context context;
        private static final String DATABASE_NAME = "ToodelsDB";
        private static final int DATABASE_VERSION = 2;
        private static final String ORDERS_TABLE_NAME = "Orders";
        private static final String ORDERS_DESCR_TABLE_NAME = "Orders_descr";
        private static final String ORDER_ID = "orderid";
        private static final String ORDER_DATE = "order_date";
        private static final String TOT_AMOUNT = "totamount";
        private static final String CUST_ID = "custid";
        private static final String CUST_NAME = "custName";
        private static final String EMAIL = "EMAIL";
        public static final String ITEM_NAME = "itemName";
        private static final String PRICE = "Price";
        private static final String PARENT_CHILD_LINK = "parentChildLink";
        private static final String PARENT_IND = "parentind";
        private static final String PARENT_ID = "parentid";
        private static final String DESCR = "DESCR";


        /*public PlacedOrders(int orderID, int custID, String custName,
                        String itemName, double price, int parentChildLink,
                        int parentID, String parentIND, String itemDescr, double totamount) {*/

        private static final String ORDERS_DESCR_TABLE_CREATE_STATEMENT = "Create table " + ORDERS_DESCR_TABLE_NAME + "(" + ORDER_ID + " Integer, " +
                CUST_ID + " Integer, " + CUST_NAME + " Varchar(30), " + EMAIL + " Varchar(30), " +
                ITEM_NAME + " Varhcar(20), " + DESCR + " Varchar(50), " + PRICE + " Double, " +
                PARENT_CHILD_LINK + " Integer, " + PARENT_IND + " Varhcar(2), " + PARENT_ID + " Integer)";

        private static final String ORDERS_TABLE_CREATE_STATEMENT = "Create table " + ORDERS_TABLE_NAME + "(" + ORDER_ID + " Integer Primary Key, " +
                ORDER_DATE + " VARCHAR(20), " + TOT_AMOUNT + " Double)";

        private static final String DROP_TABLE_ORDERS = "DROP TABLE IF EXISTS " + ORDERS_TABLE_NAME;
        private static final String DROP_TABLE_ORDERS_DESCR = "DROP TABLE IF EXISTS " + ORDERS_DESCR_TABLE_NAME;

        public ToodlesDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.execSQL(ORDERS_TABLE_CREATE_STATEMENT);
            sqLiteDatabase.execSQL(ORDERS_DESCR_TABLE_CREATE_STATEMENT);
            Log.w("ToodlsDBHelper", "Creating Tables");

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {


        }
    }
}
