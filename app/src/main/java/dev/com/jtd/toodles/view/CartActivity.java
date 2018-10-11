package dev.com.jtd.toodles.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.AppController;
import dev.com.jtd.toodles.R;
import dev.com.jtd.toodles.background.BunniesNetworkManager;
import dev.com.jtd.toodles.background.serviceworkers.MenuClientServerComm;
import dev.com.jtd.toodles.background.NetworkAsyncTask;
import dev.com.jtd.toodles.background.BunniesCart;
import dev.com.jtd.toodles.background.BunniesCartAdapter;
import dev.com.jtd.toodles.model.BunniesCartItem;
import dev.com.jtd.toodles.model.Bunny;
import dev.com.jtd.toodles.model.BunnyIngredients;

public class CartActivity extends AppCompatActivity implements BunniesNetworkManager, IngredientsFragment.IngredientsPasser, View.OnClickListener {

    private BunniesCart bunniesCart;
    private BunniesCartAdapter bunniesCartAdapter;
    private RecyclerView recyclerView;

    private ProgressDialog pd;
    private NetworkAsyncTask bunniesTask;
    private ArrayList<Bunny> ingredients;
    private ArrayList<BunnyIngredients> ingredientsPerBun;
    private String data;
    private ArrayList<BunnyIngredients> bunnyIngredientses ;
    private AppController appController;

    private Toolbar cartBottomToolBar;
    private TextView txtCartTotal, txtCartCount;
    private ImageView imgMovetToOrders,imgCartClear;
    public static final String INGREDIENTS_REQ_ID = "INGREDIENTS";
    public static final String CLASS_NAME = "dev.com.jtd.toodles.view.CartActivity";
    private MenuClientServerComm csm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initItems();


    }

    private void initItems()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cartBottomToolBar = (Toolbar) findViewById(R.id.cartBottomToolbar);

        txtCartCount = (TextView) cartBottomToolBar.findViewById(R.id.txtCartCount);
        txtCartTotal = (TextView) cartBottomToolBar.findViewById(R.id.txtCartTotal);
        imgCartClear = (ImageView) cartBottomToolBar.findViewById(R.id.imgCartClear);
        imgMovetToOrders = (ImageView) cartBottomToolBar.findViewById(R.id.imgMoveToOrders);
        imgMovetToOrders.setOnClickListener(this);
        imgCartClear.setOnClickListener(this);

        this.appController = AppController.getInstance();

        bunniesCartAdapter = new BunniesCartAdapter(appController.getBunniesCart().getAllBunniesItem(), this);
        recyclerView = (RecyclerView) findViewById(R.id.cartRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1,dpToPx(5),false));
        recyclerView.setAdapter(bunniesCartAdapter);
        recyclerView.setHasFixedSize(true);
        bunniesCart = bunniesCartAdapter.getUpdatedBunniesCart();
        txtCartCount.setText(""+bunniesCart.getCount());
        txtCartTotal.setText("R"+bunniesCart.getTotal());
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
       // appController.setBunniesCart(bunniesCart);
      /*  csm = new MenuClientServerComm();
        csm.setContext(this);
        csm.setProgressDialog(pd);
        csm.requestBunnies(Bunny.ITEMTYPE_INGREDIENT);
        csm.requestIngredientsPerBun();*/


    }

    public void requestIngredientsPerBun(ArrayList<BunnyIngredients> ingrPerBun)
    {
       /* ingredientsPerBun = ingrPerBun;
        Log.w("requestIngrPerBun()","ingredients per bun is empty"+ingredientsPerBun.isEmpty());*/
    }

    public void requestMenuItems(ArrayList<Bunny> ingr)
    {
        /*ingredients = ingr;
        Log.w("requestMenuItems()","ingredients empty"+ingredients.isEmpty()+" size: "+ingredients.size());*/


    }






    public BunniesCart getBunniesCart()
    {
        return this.bunniesCart;
    }

    public void refreshAdapter()
    {
        this.bunniesCartAdapter.notifyDataSetChanged();
        txtCartTotal.setText("R"+AppController.getInstance().getBunniesCart().getTotal());
        txtCartCount.setText(AppController.getInstance().getBunniesCart().getCount()+" ");

    }


    @Override
    public void passIngredientsData(BunniesCartItem cartItem, int position) {
       // this.bunniesCart.replaceBunny(cartItem,position);
        bunniesCartAdapter.notifyDataSetChanged();
    }

    @Override
    public void passAddedRemovedItems(BunniesCart bunniesCart) {
        this.bunniesCart = bunniesCart;
    }

    @Override
    public void testDataPass(String data)
    {

        bunniesCartAdapter.notifyDataSetChanged();

    }


    @Override
    public void RequestIngredients(String ingredientsPerBun, String allIngredients) {

    }

    @Override
    public void onClick(View view) {

        if(view == cartBottomToolBar.findViewById(R.id.imgMoveToOrders))
        {
            Intent intent  = new Intent(this,OrdersActivity.class);
            ArrayList<BunniesCartItem> items = (ArrayList<BunniesCartItem>) bunniesCart.getAllBunniesItem();
            for(int x = 0; x<=  items.size()-1; x++)
            {
                Log.w("Intent",items.get(x).getBunny().getName()+" "+items.get(x).getBunnyItemTotal());
            }
            intent.putExtra("BunniesCartItems",items);
            intent.putExtra("BunniesCart",bunniesCart);
            startActivity(intent);
        }
        else if(view == cartBottomToolBar.findViewById(R.id.imgCartClear))
        {
            AppController.getInstance().getBunniesCart().clearCart();
            txtCartCount.setText(""+0);
            txtCartTotal.setText("R"+0.00);

        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
