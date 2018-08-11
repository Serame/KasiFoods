package toodles.com.za.work.model;

import java.io.Serializable;

/**
 * Created by smoit on 2017/04/10.
 */

public class PlacedOrders implements Serializable{



    private int orderID;
    private int custID;
    private String custName;
    private String itemName;
    private double price;
    private int parentChildLink;
    private int parentID;
    private String parentIND;
    private String itemDescr;
    private double totamount;
    private double addedItemsTotal;
    private String appToken;
    private String completionDate;
    private String addedItems;
    private String removedItems;


    /*od.orderid,
    c.CUSTID,
	   c.NAME custName,
       c.EMAIL,
       m.name itemName,
       m.Price,
       od.cartid 'parentChildLink',
       od.parentind,
       od.parentid,
       m.DESCR,
       o.completed*/

    public static final String STATUS_COLUMN_NAME = "xstatus";
    public static final String ORDER_ID = "orderid";
    public static final String CUST_ID = "custid";
    public static final String CUST_NAME = "custName";
    public static final String EMAIL = "EMAIL";
    public static final String ITEM_NAME = "itemName";
    public static final String PRICE = "Price";
    public static final String PARENT_CHILD_LINK = "parentChildLink";
    public static final String PARENT_IND = "parentind";
    public static final String PARENT_ID = "parentid";
    public static final String DESCR = "DESCR";
    public static final String COMPLETED = "completed";
    public static final String TOT_AMOUNT = "totamount";
    public static final String APP_TOKEN = "appToken";
    public static final String COMPLETION_DATE_TIME = "completionDateTime";
    public static final String ADDED_ITEMS = "addedItems";
    public static final String REMOVED_ITEMS = "removedItems";
    public static final String ADDED_ITEMS_TOTAL = "addedItemsTotal";


    public PlacedOrders() {
    }

    public PlacedOrders(int orderID, int custID, String custName,
                        String itemName, double price, int parentChildLink,
                        int parentID, String parentIND, String itemDescr,double totamount) {
        this.orderID = orderID;
        this.custID = custID;
        this.custName = custName;
        this.itemName = itemName;
        this.price = price;
        this.parentChildLink = parentChildLink;
        this.parentID = parentID;
        this.parentIND = parentIND;
        this.itemDescr = itemDescr;
        this.totamount = totamount;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public double getTotamount() {
        return totamount;
    }

    public void setTotamount(double totamount) {
        this.totamount = totamount;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID = custID;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getParentChildLink() {
        return parentChildLink;
    }

    public void setParentChildLink(int parentChildLink) {
        this.parentChildLink = parentChildLink;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public String getParentIND() {
        return parentIND;
    }

    public void setParentIND(String parentIND) {
        this.parentIND = parentIND;
    }

    public String getItemDescr() {
        return itemDescr;
    }

    public void setItemDescr(String itemDescr) {
        this.itemDescr = itemDescr;
    }

    public String getAddedItems() {
        return addedItems;
    }

    public void setAddedItems(String addedItems) {
        this.addedItems = addedItems;
    }

    public String getRemovedItems() {
        return removedItems;
    }

    public void setRemovedItems(String removedItems) {
        this.removedItems = removedItems;
    }

    public double getAddedItemsTotal() {
        return addedItemsTotal;
    }

    public void setAddedItemsTotal(double addedItemsTotal) {
        this.addedItemsTotal = addedItemsTotal;
    }

    @Override
    public String toString() {
        return "PlacedOrders{" +
                "orderID=" + orderID +
                ", custID=" + custID +
                ", custName='" + custName + '\'' +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", parentChildLink=" + parentChildLink +
                ", parentID=" + parentID +
                ", parentIND='" + parentIND + '\'' +
                ", itemDescr='" + itemDescr + '\'' +
                ", totamount=" + totamount +
                ", addedItemsTotal=" + addedItemsTotal +
                ", appToken='" + appToken + '\'' +
                ", completionDate='" + completionDate + '\'' +
                ", addedItems='" + addedItems + '\'' +
                ", removedItems='" + removedItems + '\'' +
                '}';
    }
}
