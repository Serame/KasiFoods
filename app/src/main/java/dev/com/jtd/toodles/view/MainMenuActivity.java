package dev.com.jtd.toodles.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import app.AppController;
import dev.com.jtd.toodles.R;
import dev.com.jtd.toodles.background.BunniesNetworkManager;
import dev.com.jtd.toodles.background.ClientServerCommunicator;
import dev.com.jtd.toodles.background.NetworkAsyncTask;
import dev.com.jtd.toodles.background.BunniesCart;
import dev.com.jtd.toodles.background.ToodlesDBAO;
import dev.com.jtd.toodles.model.BunniesCartItem;
import dev.com.jtd.toodles.model.Bunny;
import dev.com.jtd.toodles.background.BunnyAdapter;
import dev.com.jtd.toodles.model.CircleTransform;
import dev.com.jtd.toodles.model.PlacedOrders;
import dev.com.jtd.toodles.model.User;


public class MainMenuActivity extends AppCompatActivity implements BunniesNetworkManager, View.OnClickListener {

    private static final String TAG = MainMenuActivity.class.getSimpleName();
    private String urlJSON = "http://10.0.3.2:8080/_ah/api/menuApi/v1/";
    private BunnyAdapter bunnyAdapter;
    private ProgressDialog pd;
    private Button btnTest;
    private BunniesCart bunniesCart;
    private RecyclerView bunniesRecyclerView;
    private MenuItem mTextUserName = null;
    private MenuItem mSignOut = null;
    private NetworkAsyncTask bunniesTask;
    private int count = 0;
    private double total = 0.00;
    public static final String MENU_REQ_ID = "MENU_ITEMS";
    private AppController appController;
    private Toolbar mainBottomToolBar,toolbar;

    private TextView txtMainTotal;
    private TextView txtMainCount;
    private ImageView imgMoveToCart;
    private ImageView imgClearCart;
    private ClientServerCommunicator clientServerCommunicator;
    private String name,loginType,surname,email;
    private NavigationView navView;
    private DrawerLayout drawerLayout;
    private View navHeader;
    private ImageView imgProPic;
    private String proPicUrl;  //If Facebook login
    private TextView txtProfileName;
    private TextView txtProfileEmail;


    public static final String ORDER_REQ_ID = "ORDERS";

    public static final String CLASS_NAME = "dev.com.jtd.toodles.view.MainMenuActivity";
    //private List<Bunny> bunniesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("onCreate","Running the 1st Activity's onCreate");

        setContentView(R.layout.activity_main_menu);
        name = getSharedPreferences(LoginActivity.LOGIN_SHARED_PREFERENCES,Context.MODE_PRIVATE).getString(User.NAME_COL,"SignIn");
        loginType = getSharedPreferences(LoginActivity.LOGIN_SHARED_PREFERENCES,Context.MODE_PRIVATE).getString(User.LOGIN_TYPE_COL,"No Data");
        surname = getSharedPreferences(LoginActivity.LOGIN_SHARED_PREFERENCES,Context.MODE_PRIVATE).getString(User.SURNAME_COL,"No Data");
        email = getSharedPreferences(LoginActivity.LOGIN_SHARED_PREFERENCES,Context.MODE_PRIVATE).getString(User.EMAIL_COL,"No Data");
      //  new ToodlesDBAO(this).deleteAllOrders();


        clientServerCommunicator = new ClientServerCommunicator();
        clientServerCommunicator.setContext(getApplicationContext());

        clientServerCommunicator.requestBunnies(Bunny.ITEMTYPE_BUNNY);
        clientServerCommunicator.requestIngredientsPerBun();
      /*  try {
            clientServerCommunicator.tesing();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


        initItems();




    }

    private void initItems()
    {

        //This method is for initialize all class memebers that are initialized in the onCreate method
        //For code neatness's sake
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.navDrawerLayout);
        navView = (NavigationView) findViewById(R.id.nav_view);

        navHeader = navView.getHeaderView(0);
        txtProfileName = (TextView) navHeader.findViewById(R.id.txtProfileName);
        txtProfileEmail = (TextView) navHeader.findViewById(R.id.txtProfileEmail);
        imgProPic = (ImageView) navHeader.findViewById(R.id.imgProPic);


        try {
            loadNavHeader();
            setUptNavigationView();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        bunniesRecyclerView = findViewById(R.id.bunniesRecyclerView);
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        bunniesRecyclerView.setHasFixedSize(true);
        bunnyAdapter = new BunnyAdapter();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,1);
        bunniesRecyclerView.setLayoutManager(layoutManager);
        bunniesRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1,dpToPx(5),false));
        bunniesRecyclerView.setAdapter(bunnyAdapter);
        bunniesRecyclerView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        appController = AppController.getInstance();
        bunniesCart = appController.getBunniesCart();
        mainBottomToolBar = (Toolbar) findViewById(R.id.mainBottomToolbar);
        txtMainCount = (TextView) mainBottomToolBar.findViewById(R.id.txtMainCount);
        txtMainTotal = (TextView) mainBottomToolBar.findViewById(R.id.txtMainTotal);
        imgClearCart = (ImageView) mainBottomToolBar.findViewById(R.id.imgClearCart);
        imgMoveToCart = (ImageView) mainBottomToolBar.findViewById(R.id.imgMoveToCart);
        imgClearCart.setOnClickListener(this);
        imgMoveToCart.setOnClickListener(this);
        txtMainTotal.setText("R"+bunniesCart.getTotal());;
        txtMainCount.setText(""+bunniesCart.getCount());


        clientServerCommunicator.setContext(this);
        clientServerCommunicator.setProgressDialog(pd);
        clientServerCommunicator.requestBunnies(Bunny.ITEMTYPE_BUNNY);



        testToodlesDBAO();

       /* bunniesTask = new NetworkAsyncTask(pd,this);
        bunniesTask.execute(new Pair<Context, String>(this,MENU_REQ_ID));*/

    }

    public void testToodlesDBAO()  //This is a test method for troubleshooting the insertion and retrieval of Order history
    {

        ToodlesDBAO t = new ToodlesDBAO(this);
        //t.deleteAllOrders();
        ArrayList<PlacedOrders> pl = t.getORderHistory();
        for(PlacedOrders p : pl)
        {
            Log.w("Order: ",p.getItemDescr());
        }


        /* PlacedOrders p = new PlacedOrders(61,1,"Serame","Polo Vivo",35.5,0,0,"Y","Chips,French,Vienna",35.5);
        PlacedOrders p4 = new PlacedOrders(61,1,"Serame","Polo Vivo",25.5,0,0,"Y","Chips,Special,Vienna",25.5);
        PlacedOrders p5 = new PlacedOrders(61,1,"Serame","Polo Vivo",15.5,0,0,"Y","Chips,Russion,Vienna",15.5);
        PlacedOrders p1 = new PlacedOrders(62,1,"Serame","Month-End Fever",40,0,0,"Y","Chips,French,Vienna,Cheese,Burger,Bacon",40);
        PlacedOrders p2 = new PlacedOrders(63,1,"Serame","Vegeterian",30,0,0,"Y","Chips,Lettuce,Tomato",30);
        PlacedOrders p3 = new PlacedOrders(64,1,"Serame","Meaty-terian",50,0,0,"Y","Chips,French,Vienna,Bacon,Burger",50);

        ArrayList<PlacedOrders> pos = new ArrayList<>();
        pos.add(p);
        pos.add(p1);
        pos.add(p2);
        pos.add(p3);
        pos.add(p4);
        pos.add(p5);

        t.insertOrderHistory(pos);*/


        //t.getOrderHistoryString();

       /* t.getOrderHistoryString();
        Log.w("Placed orders size: ",al.size()+"");

        */

    }

    private void loadNavHeader() throws Exception
    {
        if(loginType.equals(User.LOGIN_TYPE_FACEBOOK)) {
            txtProfileName.setText(name);
            proPicUrl = getIntent().getStringExtra("ProfilePicture");
            Glide.with(this)
                    .load(proPicUrl)
                    .crossFade()
                    .bitmapTransform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProPic);
            Log.w("loadNavHeader","DOne Loading Navigation header");
        }
        else

            txtProfileName.setText(name+" "+surname);

        if(!email.equals("No Data"))
        {
            txtProfileEmail.setText(email);
        }

    }

    private void setUptNavigationView() throws Exception
    {
        Log.w("setUpNavView","Setting up Navigation view");
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_profile:
                        Log.w("SelectedNavItem","Show profile activity");
                        break;
                    case R.id.nav_order_history:
                        Log.w("SelectedNavItem","Show Order history activity");
                        Intent histActivityIntent = new Intent(getApplicationContext(),NavItemsActivity.class);
                        startActivity(histActivityIntent);
                        //Intent intent = new Intent(this,CartActivity.class);
                        break;
                    case R.id.nav_setting:
                        Log.w("SelectedNavItem","Show Settings activity");
                        break;
                    case R.id.nav_about_us:
                        Log.w("SelectedNavItem","Show about us activity");

                }
                return true;
            }
        });

        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer,R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        Log.w("setUpNavView","Setting up Navigation view");

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("onResume","Execuing onResume method");
        drawerLayout = (DrawerLayout) findViewById(R.id.navDrawerLayout);
        navView = (NavigationView) findViewById(R.id.nav_view);

        navHeader = navView.getHeaderView(0);
        txtProfileName = (TextView) navHeader.findViewById(R.id.txtProfileName);
        txtProfileEmail = (TextView) navHeader.findViewById(R.id.txtProfileEmail);
        imgProPic = (ImageView) navHeader.findViewById(R.id.imgProPic);


        try {
            loadNavHeader();
            setUptNavigationView();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        mTextUserName = menu.findItem(R.id.user_name);
        mSignOut = menu.findItem(R.id.action_settings);

        if(loginType.equals(User.LOGIN_TYPE_FACEBOOK))
            mTextUserName.setTitle(name.substring(0,name.indexOf(" ",1)));
        else
            mTextUserName.setTitle(name);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.w("Selected","Signing Out");
            if(loginType.equals(User.LOGIN_TYPE_FACEBOOK))
            {
                AccessToken accessToken = getIntent().getParcelableExtra("AccessToken");
                clientServerCommunicator.signOut((AccessToken) getIntent().getParcelableExtra("AccessToken"));

            }
            else


            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    private void populateRecyclerView(ArrayList<BunniesCartItem> bunniesMenu)
    {
        bunnyAdapter = new BunnyAdapter(bunniesMenu);
        bunniesRecyclerView.setAdapter(bunnyAdapter);
        bunnyAdapter.notifyDataSetChanged();

        initSwipe();
    }

    private void initSwipe()
    {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                BunniesCartItem cartItem = bunnyAdapter.getBunniesCartItem(position);

                if(direction == ItemTouchHelper.LEFT)
                {
                    if(cartItem != null){
                       try {
                           bunniesCart .removeFromCart(cartItem);
                           count = bunniesCart.getCount();
                           total = bunniesCart.getTotal();

                           txtMainCount.setText(""+bunniesCart.getCount());
                           txtMainTotal.setText("R"+bunniesCart.getTotal());

                       }
                       catch(IndexOutOfBoundsException | NullPointerException ex)
                       {
                           Toast.makeText(MainMenuActivity.this," OutOfBounds Item never added to catalog",Toast.LENGTH_LONG).show();
                           ex.printStackTrace();
                       }


                    }
                    else{
                        //throw(new NullPointerException("An invalid position was selected"));
                    }
                    bunnyAdapter.notifyDataSetChanged();
                }
                else if(direction == ItemTouchHelper.RIGHT)
                {

                    if(cartItem!= null){

                        cartItem.setBunnySessionID(appController.getBunnySessionID());
                        Log.w("CartItemBID",cartItem.getBunnySessionID()+"");
                        cartItem.setParentInd('Y');
                        cartItem.setParentID(0);
                        bunniesCart.addToCart(cartItem);
                        count = bunniesCart.getCount();
                        total = bunniesCart.getTotal();

                        txtMainCount.setText(""+bunniesCart.getCount());
                        txtMainTotal.setText("R"+bunniesCart.getTotal());

                    }
                    else{
                       throw(new NullPointerException("An invalid position was selected"));
                    }
                    bunnyAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper touchHelper = new ItemTouchHelper(simpleCallback);
        touchHelper.attachToRecyclerView(bunniesRecyclerView);
    }


    public void requestMenuItems(ArrayList<BunniesCartItem> bunniesList) {

        populateRecyclerView(bunniesList);

        /*ArrayList<BunniesCartItem> bunniesLIst = null;
        if(data == null)
            Log.w("JSON","Data from Aysnc task is null");

        else {
            try {
                bunniesLIst = new ArrayList<BunniesCartItem>();
                JSONObject bunnyJSON = null;

                Bunny bunny = null;
                BunniesCartItem cartItem = null;
                JSONArray menuArray = new JSONObject(data).getJSONArray("items");
                if (menuArray != null) {
                    for (int x = 0; x <= menuArray.length() - 1; x++) {
                        bunnyJSON = menuArray.getJSONObject(x);
                        bunny = new Bunny(bunnyJSON.getInt("id"),
                                bunnyJSON.get("descr").toString(),
                                bunnyJSON.getDouble("price"));
                        bunny.setName(bunnyJSON.get("name").toString());
                        cartItem = new BunniesCartItem(bunny,null,null);
                        cartItem.setParentInd('Y');
                        bunniesLIst.add(cartItem);

                        

                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/


    }

    @Override
    public void RequestIngredients(String ingredientsPerBun, String allIngredients) {

    }

    @Override
    public void onClick(View view) {

        if(view == mainBottomToolBar.findViewById(R.id.imgMoveToCart))
        {
            Intent intent = new Intent(this,CartActivity.class);
            if(this.bunniesCart.getCount() != 0)
            {
                this.appController.setBunniesCart(this.bunniesCart);
                intent.putExtra(BunniesCart.BUNNIES_CART,this.bunniesCart);
                startActivity(intent);
            }
            else
                Toast.makeText(this,"Shopping Basket is empty.  Please add at least one item",Toast.LENGTH_LONG).show();

        }else if(view == mainBottomToolBar.findViewById(R.id.imgClearCart))
        {
            bunniesCart.clearCart();
            txtMainTotal.setText("R"+bunniesCart.getTotal());
            txtMainCount.setText(""+bunniesCart.getCount());
        }




    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
