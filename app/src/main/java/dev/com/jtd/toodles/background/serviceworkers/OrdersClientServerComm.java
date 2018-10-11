package dev.com.jtd.toodles.background.serviceworkers;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import app.AppController;
import dev.com.jtd.toodles.model.BunniesMessage;
import dev.com.jtd.toodles.model.OrderWrapper;
import dev.com.jtd.toodles.view.OrdersActivity;

public class OrdersClientServerComm extends  CommService{

    private AppController appController;
    private Context context;
    private ProgressDialog progressDialog;
    private String urlJSON = "https://kasifoods-218712.appspot.com/_ah/api/menuApi/v1/";
    private Gson gson;
    private GoogleAccountCredential credential;

    public OrdersClientServerComm()
    {
        appController = AppController.getInstance();
        this.gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();
        appController.getCredential();
        credential = appController.getCredential();

    }

    public void   placeNewOrder(final OrderWrapper orderWrapper,int shopID) throws JSONException {

        Log.w("Method:placeNewOrder()","Placing New Order");
        Log.w("OrderWrapper",gson.toJson(orderWrapper));
        //      JSONArray orderArray = new JSONArray(orders);
        JSONObject orderParams = new JSONObject(gson.toJson(orderWrapper));
        orderParams.put("shopID",shopID);
        //orderParams.put("orders",orderWrapper);
//        Log.w("Orders size ",orderArray.length()+"");


        final OrdersActivity ordersActivity = (OrdersActivity) context;
        showDailog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, urlJSON + "orders/NewOrder", orderParams,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                BunniesMessage message = gson.fromJson(jsonObject.toString(),BunniesMessage.class);
                ordersActivity.recieveFeedback(message);
                Log.w("Feedback",jsonObject.toString());
                dismissDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                volleyError.printStackTrace();
                dismissDialog();

            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        appController.addToRequestQueue(req);
    }

    @Override
    public void showDailog() {

        if(progressDialog != null && !progressDialog.isShowing())
            progressDialog.show();

    }

    @Override
    public void dismissDialog() {

        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();

    }

    @Override
    public void setProgressDialog(ProgressDialog progressDialog) {

        this.progressDialog = progressDialog;

    }

    @Override
    public void setContext(Context context) {

        this.context = context;

    }


}
