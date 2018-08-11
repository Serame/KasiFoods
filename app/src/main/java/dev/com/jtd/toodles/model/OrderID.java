package dev.com.jtd.toodles.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by smoit on 2017/12/04.
 */

public class OrderID implements Serializable {

    private int order_id;
    private ArrayList<ToodlesMessage> messages;


    /*public OrderID(int order_id) {
        this.order_id = order_id;
    }*/

    public OrderID() {

        this.messages = new ArrayList<>();
    }

    public ArrayList<ToodlesMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<ToodlesMessage> messages) {
        this.messages = messages;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
