package app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import dev.com.jtd.toodles.background.BunniesCart;
import dev.com.jtd.toodles.background.ClientServerCommunicator;
import dev.com.jtd.toodles.background.NetworkAsyncTask;
import dev.com.jtd.toodles.model.Bunny;
import dev.com.jtd.toodles.model.BunnyIngredients;

/**
 * Created by serame on 10/5/2016.
 *Testing Git
 */
public class AppController extends Application {

    private  static AppController appControllerInstance;
    private RequestQueue mRequestQueue;
    private BunniesCart bunniesCart;
    static final String TAG = AppController.class.getSimpleName();
    private int bunnySessionID = 0;
    public static final String CLASS_NAME = "app.AppController";
    public static final String REQUEST_ID = "ALL_INGREDIENTS_REQ_ID";
    private ArrayList<BunnyIngredients> bunniesIngredientsPerBun;
    private ArrayList<Bunny> ingredients;
    private String fireBaseToken;
    private GoogleAccountCredential credential;


    @Override
    public void onCreate() {


        super.onCreate();
        appControllerInstance = this;
        this.bunniesCart = new BunniesCart();
        ClientServerCommunicator csm = new ClientServerCommunicator();
        csm.setContext(this);
        csm.requestBunnies(Bunny.ITEMTYPE_INGREDIENT);
        csm.requestIngredientsPerBun();
        this.fireBaseToken = FirebaseInstanceId.getInstance().getToken();
        //Log.w("TheToken",fireBaseToken);
        /*NetworkAsyncTask bunniesTask = new NetworkAsyncTask(this);
        bunniesTask.execute(new Pair<Context, String>(this,REQUEST_ID));*/
    }

    public void updateFirebaseToken(String updatedToken)
    {
        this.fireBaseToken = updatedToken;
    }

    public String getFireBaseToken()
    {
        return this.fireBaseToken;
    }

    public void setGoogleAccountCredential(GoogleAccountCredential credential)
    {
        this.credential = credential;
    }
    public GoogleAccountCredential getCredential()
    {
        return this.credential;
    }





    public static synchronized  AppController getInstance()
    {
        return appControllerInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public BunniesCart getBunniesCart()
    {
        Log.w("AppContollerGetMethod",bunniesCart.getAllBunniesItem().toString());
        return this.bunniesCart;

    }

    public void setBunniesCart(BunniesCart bunniesCart)
    {
        this.bunniesCart = bunniesCart;
        Log.w("AppContollerSetMethod",this.bunniesCart.getAllBunniesItem().toString());
    }

    public  int getBunnySessionID()
    {
        bunnySessionID++;
        return bunnySessionID;
    }

    public void requestMenuItems(ArrayList<Bunny> ingredientsList)
    {
        ingredients = ingredientsList;
        Log.w("reqMenuIt App","Size "+ingredients.size()+" is null"+ingredients.isEmpty());


    }
    public void requestIngredientsPerBun(ArrayList<BunnyIngredients> ingredientsPerBunList)//This method will return all the Ingredients per bunny
    {
        bunniesIngredientsPerBun = ingredientsPerBunList;
        Log.w("AppController","bunniesIngrPerBun is empty: "+bunniesIngredientsPerBun.isEmpty()+" size "+bunniesIngredientsPerBun.size());
        /*

        if(ingredientsPerBun == null && allIngredients == null)
        {
            Log.w("Ingredients ","Ingredients list is empty");

        }
        else
        {
            Log.w("Ingredients","Data is not empty");
            bunniesIngredientsPerBun = new ArrayList<BunnyIngredients>();
            JSONObject ingredientsJSON = null;
            BunnyIngredients bunnyIngredients = null;
            try {
                Log.w("TroublShoot",ingredientsPerBun);
                Log.w("TroublShoot",new JSONObject(ingredientsPerBun).toString());
                JSONArray ingrArray = new JSONObject(ingredientsPerBun).getJSONArray("items");
                if(ingrArray != null)
                {
                    for(int x = 0; x <= ingrArray.length()-1; x++)
                    {
                        ingredientsJSON = ingrArray.getJSONObject(x);

                        bunnyIngredients = new BunnyIngredients(ingredientsJSON.getInt("bunny_id"),
                                ingredientsJSON.getInt("ingr_id"),
                                ingredientsJSON.getString("descr"),
                                ingredientsJSON.getDouble("price"));
                        bunniesIngredientsPerBun.add(bunnyIngredients);
                    }
                }



                JSONObject bunnyJSON = null;
                Bunny item = null;
                ingredients = new ArrayList<Bunny>();

                JSONArray menuArray = new JSONObject(allIngredients).getJSONArray("items");

                if (menuArray != null) {
                    for (int x = 0; x <= menuArray.length() - 1; x++) {
                        bunnyJSON = menuArray.getJSONObject(x);
                        item = new Bunny(bunnyJSON.getInt("id"),
                                bunnyJSON.get("descr").toString(),
                                bunnyJSON.getDouble("price"));
                        item.setName(bunnyJSON.get("name").toString());
                        ingredients.add(item);
                        Log.w("Bunny:",item.toString());

                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }*/

    }

    public ArrayList<Bunny> getIngredients()
    {
        return this.ingredients;
    }

    public ArrayList<BunnyIngredients> getBunniesIngredientsPerBun()
    {
        return this.bunniesIngredientsPerBun;
    }





}
