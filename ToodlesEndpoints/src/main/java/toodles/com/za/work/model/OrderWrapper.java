package toodles.com.za.work.model;


import java.io.Serializable;
import java.util.List;

/**
 * Created by smoit on 2017/02/25.
 */

public class OrderWrapper implements Serializable{



    private int orderID;
    private List<OrderDescr> orders;


    public OrderWrapper() {


    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public List<OrderDescr> getOrdersList() {
        return this.orders;
    }

    public void setOrders(List<OrderDescr> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "OrderWrapper{" +
                "orderID=" + orderID +
                ", orders=" + orders +
                '}';
    }
}
