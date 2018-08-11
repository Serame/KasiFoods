package toodles.com.za.work.model;

import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by smoit on 2017/05/28.
 */

public class TestFirebaseAdminServer {

    public static void main(String[] args) {

        String clienttoken = "eDqL7Z9lcec:APA91bFiOorSq1N7QHDZvpbd_nGarFXcBEOWXrjz8_jApr8QW3oIYgbOW1Eo_Qv29Rhw0lfT2j38qCWcmvFxQgTVMH0t4kX5Z51GrZnZSxE2ofXVvtga_t7lc4J08vMp2C8mPxeGxEFc";

        try {
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Authorization","key=AIzaSyCa41BHOQ1ZHJkSYWjcxw3FbytGR10YBas");
            JsonObject message = new JsonObject();
            JsonObject notification = new JsonObject();
            JsonObject data = new JsonObject();
            notification.addProperty("body","Re heditse go dira kota ya go");
            notification.addProperty("title","KotaTime");
            notification.addProperty("sound","default");
            notification.addProperty("priority","high");
            data.addProperty("custid",1);
            data.addProperty("order_num",2);
            data.addProperty("name","Serame Moitsheki");
            message.add("notification",notification);
            message.add("data",data);
            message.addProperty("to",clienttoken);

            OutputStream os = conn.getOutputStream();
            os.write(message.toString().getBytes());
            os.flush();
            os.close();

            int respCode = conn.getResponseCode();
            System.out.println("HTTP Respose is "+respCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String test = br.readLine();
            StringBuffer sb = new StringBuffer();
              while(!test.equals(null))
            {
                System.out.println("BR is not null");
                System.out.println(test);
                sb.append(br.readLine());
                test = br.readLine();
            }
            br.close();
            System.out.println(sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
