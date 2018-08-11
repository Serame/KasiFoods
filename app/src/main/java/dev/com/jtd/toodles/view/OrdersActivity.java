package dev.com.jtd.toodles.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import app.AppController;
import dev.com.jtd.toodles.R;
import dev.com.jtd.toodles.background.BunniesCart;
import dev.com.jtd.toodles.background.BunniesNetworkManager;
import dev.com.jtd.toodles.background.BunniesOrderAdapter;
import dev.com.jtd.toodles.background.ClientServerCommunicator;
import dev.com.jtd.toodles.model.BunniesCartItem;
import dev.com.jtd.toodles.model.BunniesMessage;
import dev.com.jtd.toodles.model.Bunny;
import dev.com.jtd.toodles.model.BunnyIngredients;
import dev.com.jtd.toodles.model.OrderDescr;
import dev.com.jtd.toodles.model.OrderWrapper;
import dev.com.jtd.toodles.model.User;

public class OrdersActivity extends AppCompatActivity implements BunniesNetworkManager, View.OnClickListener {

    public static final String CLASS_NAME = "dev.com.jtd.toodles.view.OrdersActivity";
    public static final String ORDERID_REQ_ID = "ORDERID";
    public static final String PLACE_ORDER_REQ = "PLACE_ORDER";
    private BunniesOrderAdapter orderAdapter;
    private RecyclerView orderRecyclerView;
    private BunniesCart bunniesCart;
    private ArrayList<BunniesCartItem> cartItems;
    private Toolbar bottomToolbar;
    private int orderItemsTotal = 0;
    private double total = 0;
    private int orderID = 0;
    private ProgressDialog pd;
    private TextView txtOrderCount, txtOrdertotal, txtOrderCheckOut;
    private ImageView imgCheckout;
    private SharedPreferences loginPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_activity);
        initItems();


    }

    @Override
    public void RequestIngredients(String ingredientsPerBun, String allIngredients) {



    }

    private OrderWrapper createOrder()
    {

        //JsonArray orderArray = new JsonArray();
        ArrayList<OrderDescr> orders = new ArrayList<OrderDescr>();
        OrderWrapper orderWrapper = new OrderWrapper();
        orderWrapper.setOrderID(0);
        Gson gson = new GsonBuilder().disableHtmlEscaping()
                                     .setPrettyPrinting()
                                     .serializeNulls().create();
        OrderDescr orderDescr = null;
        List<String> ordersList = new ArrayList<String>();
        String orderDescrStr = "";
        String appToken = AppController.getInstance().getFireBaseToken();


        /****************************************************************************/
        for(int x = 0; x<= this.bunniesCart.getAllBunniesItem().size()-1; x++)
        {
            //OrderDescr(int orderid, long custormerID, int itemID, int extrasID, int removedID, double price,double addedItemsTotal,int cartPosition,int parentID,char parentIND,String appTokem) {
            orderDescr = new OrderDescr(0,
                                        loginPreferences.getLong(User.CUST_ID_COL,-1),
                                        bunniesCart.getAllBunniesItem().get(x).getBunny().getItem_id(),
                                        0,
                                       0,
                                        bunniesCart.getAllBunniesItem().get(x).getBunnyItemTotal(),
                                        bunniesCart.getAllBunniesItem().get(x).getAddedItemsTotal(),
                                        bunniesCart.getAllBunniesItem().get(x).getBunnySessionID(),
                                        bunniesCart.getAllBunniesItem().get(x).getParentID(),
                                        bunniesCart.getAllBunniesItem().get(x).getParentInd(),
                                        appToken);
            orderDescr.setCartPosition(bunniesCart.getAllBunniesItem().get(x).getBunnySessionID());

            //Log.w("FinalOrder",orderDescr.toString());

            orders.add(orderDescr);
            orderDescrStr = gson.toJson(orderDescr,OrderDescr.class);
            ordersList.add(orderDescrStr);

        }
        for(int x = 0; x<= this.bunniesCart.getAllAddedItems().size()-1; x++)
        {
            orderDescr = new OrderDescr(0,loginPreferences.getLong(User.CUST_ID_COL,-1),
                                        bunniesCart.getAllAddedItems().get(x).getBunny().getItem_id(),
                                        0,0,bunniesCart.getAllAddedItems().get(x).getBunnyItemTotal(),
                                        0,
                                        bunniesCart.getAllAddedItems().get(x).getBunnySessionID(),
                                        bunniesCart.getAllAddedItems().get(x).getParentID(),
                                        bunniesCart.getAllAddedItems().get(x).getParentInd(),
                                        appToken);
            orderDescr.setCartPosition(bunniesCart.getAllAddedItems().get(x).getBunnySessionID());
            orders.add(orderDescr);
            orderDescrStr = gson.toJson(orderDescr,OrderDescr.class);
            ordersList.add(orderDescrStr);
        }

        orderWrapper.setOrders(orders);
        //Log.w("PlaceOrder121",orderWrapper.toString());


        return orderWrapper;

    }



            @Override
        public void onClick(View view) {
            if (view == findViewById(R.id.imgCheckout))
        {
            ClientServerCommunicator csm = new ClientServerCommunicator();
            csm.setContext(this);
            csm.setProgressDialog(pd);
            try {
                csm.placeNewOrder(createOrder());
                //csm.tesing(createOrder());
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    public void recieveFeedback(BunniesMessage message)
    {

        Log.w("Message status",message.getStatus());
        if(message.getStatus().equals(BunniesMessage.STATUS_OK))
        {

            Log.w("Message",message.toString());
            ConfirmationFragment cf =  new ConfirmationFragment();
            Bundle args = new Bundle();
            args.putInt("orderNumber",message.getOrderID());
            cf.setArguments(args);
            cf.show(getFragmentManager(),ConfirmationFragment.CLASS_NAME);

        }

    }

    private void initItems()
    {

        loginPreferences = getSharedPreferences(LoginActivity.LOGIN_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        orderRecyclerView = (RecyclerView) findViewById(R.id.listCheckOutItems);
        orderRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        orderRecyclerView.setLayoutManager(layoutManager);
        Intent intent = getIntent();

        //bunniesCart = (BunniesCart) intent.getSerializableExtra("BunniesCart");
        bunniesCart = AppController.getInstance().getBunniesCart();
        cartItems = (ArrayList<BunniesCartItem>) bunniesCart.getAllBunniesItem();
        orderItemsTotal = cartItems.size();
        bottomToolbar = (Toolbar) findViewById(R.id.orderToolbar);

//      setSupportActionBar(bottomToolbar);
        txtOrderCount = (TextView) bottomToolbar.findViewById(R.id.txtOrderCount);
        imgCheckout = (ImageView) bottomToolbar.findViewById(R.id.imgCheckout);
        txtOrdertotal = (TextView) bottomToolbar.findViewById(R.id.txtOrderTotal);
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        total = bunniesCart.getTotal();
        txtOrdertotal.setText("R"+new DecimalFormat("0.00").format(total));
        txtOrderCount.setText(""+this.orderItemsTotal);
        imgCheckout.setOnClickListener(this);
        orderAdapter = new BunniesOrderAdapter(cartItems);
        orderRecyclerView.setAdapter(orderAdapter);
        BunniesItemDecorator decorator = new BunniesItemDecorator(this,BunniesItemDecorator.VERTICAL_LIST);
        orderRecyclerView.addItemDecoration(decorator);

    }

}
