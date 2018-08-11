package dev.com.jtd.toodles.model;


import java.io.Serializable;
import java.util.List;

/**
 * Created by smoit on 2017/02/25.
 */

public class OrderWrapper implements Serializable{

    private List<OrderDescr> orders;
    private int orderID;

    public OrderWrapper() {

    }

    public List<OrderDescr> getOrders() {
        return orders;
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

    public void setOrders(List<OrderDescr>orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "OrderWrapper{" +
                "orders=" + orders.toString() +
                '}';
    }
}
