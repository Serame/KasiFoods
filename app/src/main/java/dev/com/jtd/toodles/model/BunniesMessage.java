package dev.com.jtd.toodles.model;

/**
 * Created by smoit on 2017/04/17.
 */

public class BunniesMessage {

    private String message;
    private int orderID;
    private String status;
    public static final String STATUS_OK = "Success";
    public static final String STATUS_NOT_OK = "FAILED";


    public BunniesMessage(String message, int orderID, String status) {
        this.message = message;
        this.orderID = orderID;
        this.status = status;
    }

    public BunniesMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BunniesMessage{" +
                "message='" + message + '\'' +
                ", orderID=" + orderID +
                ", status='" + status + '\'' +
                '}';
    }
}
