package dev.com.jtd.toodles.background;

import android.content.Context;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.AppController;
import dev.com.jtd.toodles.R;
import dev.com.jtd.toodles.model.BunniesCartItem;
import dev.com.jtd.toodles.model.Bunny;
import dev.com.jtd.toodles.model.BunnyIngredients;
import dev.com.jtd.toodles.view.CartActivity;
import dev.com.jtd.toodles.view.IngredientsFragment;

/**
 * Created by smoit on 2016/12/31.
 */

public class BunniesCartAdapter extends RecyclerView.Adapter<BunniesCartAdapter.BunniesCartViewHolder> implements BunniesNetworkManager{

    private List<BunniesCartItem> cartItemList;
    private HashMap<Integer,BunniesCartItem> listOnDisplay;
    private Context context;
    private ArrayList<BunnyIngredients> bunniesIngredientsPerBun;
    private ArrayList<Bunny> ingredients;
    private NetworkAsyncTask bunniesTask;
    private BunniesCart bunniesCart;
    private AppController appController;
    private CartActivity cartActivity;
    public static final String CLASS_NAME = "dev.com.jtd.toodles.background.BunniesCartAdapter";


    public BunniesCartAdapter(List<BunniesCartItem> cartItemList,Context context) {

        appController = AppController.getInstance();
        this.bunniesCart = appController.getBunniesCart();
        this.cartItemList = bunniesCart.getAllBunniesItem();
        Log.w("NumberOFBunnies",cartItemList.size()+"");
        this.context = context;
        listOnDisplay = new HashMap<Integer, BunniesCartItem>();
        cartActivity = (CartActivity)context;

        bunniesIngredientsPerBun = appController.getBunniesIngredientsPerBun();
//        Log.w("BunniesAdapter()","BunniesIngredeitnsPerBun is empty "+bunniesIngredientsPerBun.isEmpty());
        ingredients = appController.getIngredients();
 //       Log.w("BunniesAdapter()","Ingredients is empty "+ingredients.isEmpty());
     /*   bunniesTask = new NetworkAsyncTask(null, this);
        bunniesTask.execute(new Pair<Context, String>(context, CartActivity.INGREDIENTS_REQ_ID));*/
    }


    @Override
    public BunniesCartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_single_row,parent,false);

        return new BunniesCartViewHolder(view);
    }




    @Override
    public void onBindViewHolder(BunniesCartViewHolder holder, int position) {

        Log.w("onBindView","Entered onBindViewMethod");

       // bunniesCart.getCartItemAt(position).setParentInd('Y'); //This is where the Bunnies get their ParentIND status set
    //    bunniesCart.getCartItemAt(position).setParentID(0);     //The ParentID for All Parents will be Zero
      //  bunniesCart.getCartItemAt(position).setBunnySessionID(position); // The CartPosition will always be the child's ParentID
        //BunniesCartItem cartItem = cartItemList.get(position);
        BunniesCartItem cartItem = bunniesCart.getAllBunniesItem().get(position);
        Log.w("CartIemDisplay",cartItem.getParentInd()+" "+cartItem.getBunny().toString());
        listOnDisplay.put(new Integer(position),cartItem);    /*Instead of using the cartItem instance, I use the one in the listOnDisplay because
                                                                it makes it possible for us to update the modified items, the list on displays
                                                                 holds an ID to every item on the list*/

        StringBuffer addedItems = new StringBuffer("");
        ArrayList<BunniesCartItem> ai = bunniesCart.getBunnyIngredients(cartItem.getBunnySessionID());

        for(int x = 0; x <= ai.size()-1; x++)
        {

            Log.w("AI",ai.get(x).getBunny().getDescr()+" ParentID: "+ai.get(x).getParentID()+"- ParentIND: "+ai.get(x).getParentInd());
            if(x == 0)
                addedItems.append(ai.get(x).getBunny().getDescr());
            else
                addedItems.append(","+ai.get(x).getBunny().getDescr());

        }

       // cartItem = listOnDisplay.get(new Integer(position));
        if(cartItem.getBunny().getName() != null)
            holder.txtCartName.setText(cartItem.getBunny().getName());
        else
            holder.txtCartName.setText("No Name");

        holder.txtAdded.setText(addedItems.toString());
        holder.txtSubtracted.setText("");
        holder.txtCartPrice.setText("R"+cartItem.getBunny().getPrice()+"");
        holder.txtCartDescr.setText(cartItem.getBunny().getDescr());
        holder.position = position;
        holder.cartItem = cartItem;

    }


    //This method returns the BunniesCartThas has items that have been given, ParentIDs,ParentINDs and CartPositions
    public BunniesCart getUpdatedBunniesCart()
    {
        return bunniesCart;
    }



    @Override
    public int getItemCount() {

        if(this.cartItemList != null)
        {
            return cartItemList.size();
        }
        else
            return 0;


    }

    public ArrayList<BunnyIngredients> getIngredients()
    {
        return this.bunniesIngredientsPerBun;

    }



    @Override
    public void RequestIngredients(String ingredientsPerBun, String allIngredients) {

        if(ingredientsPerBun == null && allIngredients == null)
        {
            Log.w("Ingredients ","Ingredients list is empty");
        }
        else
        {
           /* Log.w("Ingredients","Data is not empty");
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
            }*/

        }


    }

    class BunniesCartViewHolder extends RecyclerView.ViewHolder {
            public TextView txtCartName;
            public TextView txtCartPrice;
            public TextView txtCartDescr;
            public Button btnAdd;
            public Button btnSubtract;
            public TextView txtAdded;
            public TextView txtSubtracted;
            public int position;
            public BunniesCartItem cartItem;

        public BunniesCartViewHolder(View view)
        {
            super(view);


            this.btnAdd = (Button)view.findViewById(R.id.btnAddExtras);
            this.btnSubtract = (Button)view.findViewById(R.id.btnRemoveSides);
            this.txtAdded = (TextView)view.findViewById(R.id.txtAdded);
            this.txtSubtracted = (TextView)view.findViewById(R.id.txtSubtracted);
            this.txtCartDescr = (TextView)view.findViewById(R.id.txtCartDescr);
            this.txtCartName = (TextView)view.findViewById(R.id.txtCartName);
            this.txtCartPrice = (TextView)view.findViewById(R.id.txtCartPrice);

            this.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.w("Item being modified",cartItem.getBunny().getName()+ " SessionID "+cartItem.getBunnySessionID());
                    CartActivity cartActivity = (CartActivity)context;
                    FragmentManager fm = cartActivity.getFragmentManager();
                    IngredientsFragment inf = new IngredientsFragment();
                    Bundle args = new Bundle();
                    args.putSerializable("BunniesCart",cartActivity.getBunniesCart());
                    args.putSerializable("BunniesIngredients", bunniesIngredientsPerBun);
                   // Log.w("Adding args",""+bunniesIngredientsPerBun.isEmpty());
                    args.putSerializable("AllIngredients",ingredients);
                    args.putString("ACTION","ADD");
                    args.putInt("Parent_ID",cartItem.getBunnySessionID());

                    args.putSerializable(BunniesCartItem.CLASS_NAME,cartItem);


                    inf.setArguments(args);
                    inf.show(fm,IngredientsFragment.CLASS_NAME);



                }
            });
            this.btnSubtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CartActivity cartActivity = (CartActivity)context;
                    FragmentManager fm = cartActivity.getFragmentManager();
                    IngredientsFragment inf = new IngredientsFragment();
                    Bundle args = new Bundle();
                    args.putSerializable("BunniesIngredients", bunniesIngredientsPerBun);

                    args.putString("ACTION","SUBTRACT");
                    args.putInt("ID",position);
                    args.putSerializable(BunniesCartItem.CLASS_NAME,cartItem);
                    Log.w("Args",""+args.isEmpty());
                    inf.setArguments(args);
                    inf.show(fm,IngredientsFragment.CLASS_NAME);



                }
            });

        }


    }


    public void RequestData(ArrayList<BunniesCartItem> cartItemList) {


    }
}
