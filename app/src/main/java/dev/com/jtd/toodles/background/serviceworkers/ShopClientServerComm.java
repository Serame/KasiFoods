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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import app.AppController;
import dev.com.jtd.toodles.model.Shop;
import dev.com.jtd.toodles.view.ShopActivity;

public class ShopClientServerComm extends CommService {

    private AppController appController;
    private Context context;
    private ProgressDialog progressDialog;
    private String urlKasiFoods = "https://kasifoods-218712.appspot.com/_ah/api/kasiFoodsApi/v1/";
    private Gson gson;
    private GoogleAccountCredential credential;

    public ShopClientServerComm() {

        appController = AppController.getInstance();
        this.gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();
        appController.getCredential();
        credential = appController.getCredential();


    }

    public void getKasiShops()
    {

        showDailog();


        final HashMap<String,List<Shop>> kasiShopsList = new HashMap<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlKasiFoods + "getKasiWithShops", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                Shop shop;
                List<Shop> shopsList;
                Iterator<String> i = jsonObject.keys();
                for (Iterator<String> it = i; it.hasNext(); ) {
                    String s = it.next();
                    shopsList = new ArrayList<>();
                    try {
                        JSONArray arr = jsonObject.getJSONArray(s);
                        for(int x = 0; x <= arr.length()-1;x++)
                        {
                            JSONObject json = (JSONObject) arr.get(x);

                            shop = new Shop();
                            shop.setShopID(json.getInt("shopID"));
                            shop.setKasiID(json.getInt("kasiID"));
                            shop.setShopOwnerID(json.getInt("shopOwnerID"));
                            shop.setShopCoodinates(json.getString("shopCoodinates"));
                            shop.setShopAddress(json.getString("shopAddress"));
                            shop.setShopName(json.getString("shopName"));
                            shop.setShopFCMToken(json.getString("shopFCMToken"));

                            shopsList.add(shop);

                        }

                        kasiShopsList.put(s,shopsList);
                        ShopActivity shopActivity = (ShopActivity) context;
                        shopActivity.requestKasis(kasiShopsList);

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                    dismissDialog();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {


                volleyError.printStackTrace();
                dismissDialog();

            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        appController.addToRequestQueue(request);




        dismissDialog();

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
