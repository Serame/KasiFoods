package dev.com.jtd.toodles.background;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.AppController;
import dev.com.jtd.toodles.model.BunniesCartItem;
import dev.com.jtd.toodles.model.BunniesMessage;
import dev.com.jtd.toodles.model.Bunny;
import dev.com.jtd.toodles.model.BunnyIngredients;
import dev.com.jtd.toodles.model.OrderDescr;
import dev.com.jtd.toodles.model.OrderID;
import dev.com.jtd.toodles.model.ToodlesMessage;
import dev.com.jtd.toodles.view.CartActivity;
import dev.com.jtd.toodles.view.LoginActivity;
import dev.com.jtd.toodles.view.MainMenuActivity;
import dev.com.jtd.toodles.view.OrdersActivity;
import dev.com.jtd.toodles.model.User;
import dev.com.jtd.toodles.model.OrderWrapper;


/**
 * Created by smoit on 2017/04/17.
 */

public class ClientServerCommunicator {
    private AppController appController;
    private Context context;
    private ProgressDialog progressDialog;
    private ArrayList<Bunny> ingredients;
    private ArrayList<BunnyIngredients> ingredientsPerBun;
    private String urlJSON = "http://10.0.3.2:8080/_ah/api/menuApi/v1/";
    //private String urlJSON = "https://xenon-lantern-213109.appspot.com/_ah/api/menuApi/v1/";
    private MainMenuActivity menuActivity;
    private CartActivity cartActivity;
    private Gson gson;
    private GoogleAccountCredential credential;


    public ClientServerCommunicator() {
        appController = AppController.getInstance();
        this.gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();
        appController.getCredential();
        credential = appController.getCredential();


    }

    public void signOut(AccessToken acessToken)
    {
        Log.w("Method","Signin Out Method");


        GraphRequest.Callback callback = new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {

                MainMenuActivity mainMenuActivity = null;
                if(context instanceof  MainMenuActivity)
                {
                    mainMenuActivity = (MainMenuActivity)context;
                }
                LoginManager.getInstance().logOut();
                SharedPreferences.Editor editor = mainMenuActivity.getSharedPreferences(LoginActivity.LOGIN_SHARED_PREFERENCES,Context.MODE_PRIVATE).edit();
                editor.putLong(User.CUST_ID_COL,-1);
                editor.commit();
                AppController.getInstance().getBunniesCart().clearCart();
                Intent intent = new Intent(mainMenuActivity,LoginActivity.class);
                mainMenuActivity.startActivity(intent);
                mainMenuActivity.finish();
                Log.w("signOut","Logging Out");

            }
        };
        GraphRequest req = new GraphRequest(acessToken,"/me/permissions/",null, HttpMethod.DELETE,callback);
        req.executeAsync();


    }



    public void requestBunnies(final int itemType)
    {

        showDailog();


        JSONObject params = new JSONObject();
        try {
            params.put("User",credential);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, urlJSON + "getMenuItems?itemType="+itemType, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                ArrayList<BunniesCartItem> bunnies =  new ArrayList<>();//This will only be used if the method is called by an instance of MainMenuActivity
                ArrayList<Bunny> bunniesList = new ArrayList<>(); //This will only be used if the method is called by an instance of AppController

                BunniesCartItem cartItem = null;

                try {
                    JSONArray itemsJsonArray = jsonObject.getJSONArray("items");

                    for(int x = 0; x <= itemsJsonArray.length()-1; x++)
                    {


                        Bunny bunny = gson.fromJson(itemsJsonArray.get(x).toString(),Bunny.class);
                        bunny.setItem_id(itemsJsonArray.getJSONObject(x).getInt("item_id"));

                        cartItem = new BunniesCartItem(bunny,null,null);
                        cartItem.setParentInd('Y');
                        cartItem.setParentID(0);
                        bunnies.add(cartItem);
                        bunniesList.add(bunny);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                if(context instanceof  MainMenuActivity)
                {
                    menuActivity = (MainMenuActivity) context;
                    menuActivity.requestMenuItems(bunnies);
                }
                else if (context instanceof  CartActivity)
                {
                    Log.w("CSM method reqBunns","Context instance of OrderActivity");
                    cartActivity = (CartActivity) context;
                    cartActivity.requestMenuItems(bunniesList);


                }

                else
                    appController.requestMenuItems(bunniesList);
                    Log.w("CSM method reqBunns","Using AppController");


                dismissDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dismissDialog();
                volleyError.printStackTrace();


            }
        }){

        };

        req.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        appController.addToRequestQueue(req);


    }

    public void getSignIn(User usr) throws JSONException {


        Log.w("Method","signIn");
        if(!usr.getLoginType().equals(User.LOGIN_TYPE_APP))
        {
            Log.w("User",usr.toString());

        }
        Gson gsonUser = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();
        String userStr = gsonUser.toJson(usr);
        JSONObject params = new JSONObject(userStr);
        params.put("User",userStr);

        showDailog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, urlJSON + "signIn", params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.w("JSONRequest","ExecutinJSONRequest");
                Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();
                Log.w("UserObjectt",jsonObject.toString());
                User user = gson.fromJson(jsonObject.toString(),User.class);
                LoginActivity lg = (LoginActivity)context;
                lg.getUser(user);
                dismissDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                volleyError.printStackTrace();
                Log.w("SignInError","Error Signing in");
                dismissDialog();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("Authorization",appController.getCredential().toString());

                return params;
            }
        };
        int socketTimeOut = 30000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(retryPolicy);
        appController.addToRequestQueue(req);

    }



    public void requestIngredientsPerBun()
    {
        Log.w("reqIngrPerBun","Entered requstIngredietnsPerBun method");
        JSONObject params = new JSONObject();
        try {
            params.put("User",credential);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, urlJSON + "getIngredients", params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {


               // cartActivity = (CartActivity) context;

                try {
                    ArrayList<BunnyIngredients> bunniesIngredientsList = new ArrayList<>();
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();

                    for(int x = 0; x<= jsonArray.length()-1; x++)
                    {

                        BunnyIngredients ingredients = gson.fromJson(jsonArray.get(x).toString(),BunnyIngredients.class);
                        bunniesIngredientsList.add(ingredients);
                    }
                    if(context instanceof  CartActivity)
                    {
                        cartActivity.requestIngredientsPerBun(bunniesIngredientsList);
                    }
                    else if(context instanceof  AppController)
                    {
                        appController.requestIngredientsPerBun(bunniesIngredientsList);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                volleyError.printStackTrace();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                try {
                    JSONObject user = new JSONObject();
                    user.put("User",AppController.getInstance().getCredential());

                    params.put("User",user.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return params;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        appController.addToRequestQueue(req);
    }



    public void   placeNewOrder(final OrderWrapper orderWrapper) throws JSONException {

        Log.w("Method:placeNewOrder()","Placing New Order");
        Log.w("OrderWrapper",gson.toJson(orderWrapper));
  //      JSONArray orderArray = new JSONArray(orders);
        JSONObject orderParams = new JSONObject(gson.toJson(orderWrapper));
        //orderParams.put("orders",orderWrapper);
//        Log.w("Orders size ",orderArray.length()+"");


        final OrdersActivity ordersActivity = (OrdersActivity) context;
        showDailog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, urlJSON + "NewOrder", orderParams,new Response.Listener<JSONObject>() {
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




    private void showDailog()
    {
        if(progressDialog != null && !progressDialog.isShowing())
            progressDialog.show();
    }
    private void dismissDialog()
    {
        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void setProgressDialog(ProgressDialog pd)
    {
        this.progressDialog = pd;
    }



    public void setContext(Context context) {
        this.context = context;
    }
}
