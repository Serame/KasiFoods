package dev.com.jtd.toodles.background;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
//import service.work.za.com.toodles.menuApi.MenuApi;
/*import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;*/



import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.AppController;
import dev.com.jtd.toodles.model.BunniesCartItem;
import dev.com.jtd.toodles.model.OrderDescr;
import dev.com.jtd.toodles.view.CartActivity;
import dev.com.jtd.toodles.view.MainMenuActivity;
import dev.com.jtd.toodles.view.OrdersActivity;



/**
 * Created by smoit on 2016/12/28
 * .
 */

public class NetworkAsyncTask extends AsyncTask<Pair<Context,String>,Void,String> {

    private ProgressDialog pd;
    private Context context;
   // private MenuApi service = null;
    private String data = " ";
    private String data2 = " ";
    private StringBuffer requestID;
    private RecyclerView.Adapter adapter;
    private String orderArray;
    private List<String> ordersList;
    private AppController appController;

    public NetworkAsyncTask(ProgressDialog pd, Context context)
    {
        this.pd = pd;
        this.context = context;


    }

    public NetworkAsyncTask(Context context)
    {
        this.context = context;


    }

    public NetworkAsyncTask(Context context, RecyclerView.Adapter adapter)
    {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
      /*  pd.setMessage("Loading....");
        pd.setTitle("Toodles");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);*/
        Log.w("Async Task ","Executing onPreExecute method");
        if(pd != null)
            pd.show();

    }

    public void setOrders(List<String> orders)
    {
        this.ordersList = orders;
    }

    @Override
    protected String doInBackground(Pair<Context, String>... pairs) {/*

        Log.w("Async Task ","Executing doInBackGroud method");
        requestID = new StringBuffer(pairs[0].second);


        if(service == null)
        {
           MenuApi.Builder builder = new MenuApi.Builder(AndroidHttp.newCompatibleTransport(),
                                                        new AndroidJsonFactory(),null);

            builder.setRootUrl("http://10.0.3.2:8080/_ah/api");
            builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                    request.setDisableGZipContent(true);
                }
            });
            service = builder.build();

        }

        context = pairs[0].first;

        int numRetries = 1;

        do { // For an unknown reason the server reply is sometimes blank, this loop is to retry at least five times.

            try {


                if (requestID.toString().equalsIgnoreCase(MainMenuActivity.MENU_REQ_ID)) {
                    Log.w("Request ID", "Menu Request id");

                   data = service.getMenuItems(1).execute().toString();


                }
                else if (requestID.toString().equals(CartActivity.INGREDIENTS_REQ_ID)) {
                    Log.w("Request ID", "Ingredients Request id");
                    data = service.getIngredients().execute().toString();
                    data2 = service.getMenuItems(2).execute().toString();


                }
                else if(requestID.toString().equals(AppController.REQUEST_ID))
                {
                    this.appController = (AppController) context;
                    data = service.getIngredients().execute().toString();
                    data2 = service.getMenuItems(2).execute().toString();
                }
                else if(requestID.toString().equals(OrdersActivity.ORDERID_REQ_ID))
                {




                }
                else if(requestID.toString().equals(OrdersActivity.PLACE_ORDER_REQ))
                {
                    data = service.insertNewOrder(ordersList).execute().toString();
                }
            } catch (IOException ex) {
                data = ex.getMessage();
            }
            Log.w("Connection attept #"+numRetries,"Data");
            numRetries++;
        }while(data.equals(" ") && numRetries < 6);

        return data;*/
    return "null";

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        MainMenuActivity mainMenuActivity;
        CartActivity cartActivity;
        BunniesCartAdapter cartAdapter;
        OrdersActivity ordersActivity;

        if(context.getClass().getName().equals(MainMenuActivity.CLASS_NAME))
        {
            mainMenuActivity = (MainMenuActivity) this.context;
            //mainMenuActivity.RequestData(data);

        }
      /*  else if(context.getClass().getName().equals(CartActivity.CLASS_NAME))
        {
            cartActivity = (CartActivity) this.context;
            cartActivity.RequestData(data);


        }*/
        else if(adapter != null && adapter.getClass().getName().equals(BunniesCartAdapter.CLASS_NAME))
        {
            cartAdapter = (BunniesCartAdapter)this.adapter;
            //cartAdapter.RequestIngredients(data,data2);
        }
        else  if(context.getClass().getName().equals(OrdersActivity.CLASS_NAME))
        {

            ordersActivity = (OrdersActivity) this.context;
           // ordersActivity.RequestData(data);
            if(pd != null)
            {
                pd.dismiss();
            }

        }
        else if(context.getClass().getName().equals(AppController.CLASS_NAME))
        {
            //appController.requestMenuItems(data,data2);
        }





        if(pd != null)
            pd.dismiss();
    }




}
