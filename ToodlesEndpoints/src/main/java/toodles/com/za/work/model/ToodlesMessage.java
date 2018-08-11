package toodles.com.za.work.model;

import java.io.Serializable;

/**
 * Created by smoit on 2017/02/25.
 */

public class ToodlesMessage implements Serializable{

    private String message;
    private String status;
    private int orderID;
    public static final String STATUS_OK = "Success";
    public static final String STATUS_NOT_OK = "FAILED";

    public ToodlesMessage() {
    }

    public ToodlesMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
}
