package dev.com.jtd.toodles.model;

/**
 * Created by smoit on 2017/05/13.
 */

public class NotificationData {

    public static final String NEW_ORDER_NOTIFIACTION = "NEW_ORDER";
    private String notificationImage;
    private String notificationIcon;
    private String notificationTitle;
    private String notificationText;

    public NotificationData(String notificationImage, String notificationIcon, String notificationTitle, String notificationText) {
        this.notificationImage = notificationImage;
        this.notificationIcon = notificationIcon;
        this.notificationTitle = notificationTitle;
        this.notificationText = notificationText;
    }

    public NotificationData() {
    }

    public String getNotificationImage() {
        return notificationImage;
    }

    public void setNotificationImage(String notificationImage) {
        this.notificationImage = notificationImage;
    }

    public String getNotificationIcon() {
        return notificationIcon;
    }

    public void setNotificationIcon(String notificationIcon) {
        this.notificationIcon = notificationIcon;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }
}
