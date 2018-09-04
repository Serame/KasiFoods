package dev.com.jtd.toodles.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import app.AppController;
import dev.com.jtd.toodles.R;
import dev.com.jtd.toodles.background.BunniesCart;
import dev.com.jtd.toodles.background.IngredientsListAdapter;
import dev.com.jtd.toodles.model.BunniesCartItem;
import dev.com.jtd.toodles.model.Bunny;
import dev.com.jtd.toodles.model.BunnyIngredients;

/**
 * Created by smoit on 2017/02/19.
 */

public class IngredientsFragment extends DialogFragment{

    private ListView ingredientsListView;
    private Button btnOk, btnCancel;
    private ArrayList<BunnyIngredients> bunnyIngredientsPerBun;
    private ArrayList<Bunny> ingredients;
    private int id;
    private BunniesCartItem bunniesCartItem;
    private String action;
    private IngredientsListAdapter ad;
    private BunniesCart bunniesCart;
    private CartActivity hostActivity;
    public static final String CLASS_NAME = "IngredientsFragment";
    private Context context;
    private AppController appController;

    public IngredientsFragment()
    {

    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);

        bunnyIngredientsPerBun = (ArrayList<BunnyIngredients>) args.get("BunniesIngredients");
        ingredients = (ArrayList<Bunny>) args.get("AllIngredients");

        id = args.getInt("Parent_ID");
        Log.w("PassedID",id+"");
        bunniesCartItem = (BunniesCartItem) args.getSerializable(BunniesCartItem.CLASS_NAME);
        bunniesCart = (BunniesCart) args.getSerializable("BunniesCart");

        action = args.getString("ACTION");
        this.context = (Context) args.get("Context");


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appController = AppController.getInstance();
        bunniesCart = appController.getBunniesCart();
        bunnyIngredientsPerBun =  appController.getBunniesIngredientsPerBun();
        ingredients = appController.getIngredients();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ingredients_fragment,null,false);
        ingredientsListView = (ListView)view.findViewById(R.id.listIngredients);
        btnOk = (Button)view.findViewById(R.id.btnOk);
        btnCancel = (Button)view.findViewById(R.id.btnCancell);
        ArrayList<BunnyIngredients> temp = SortOutIngredientsList();
        ingredientsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ad = new IngredientsListAdapter(this.getActivity(),temp);

        ingredientsListView.setAdapter(ad);
        HandleButtonEvents();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(!activity.equals(null))
        {
            hostActivity = (CartActivity) activity;
        }
        else
        {
            Log.w("ActivityName","The activity name is null");
        }
    }

    private ArrayList<BunnyIngredients> SortOutIngredientsList()
    {

        ArrayList<BunnyIngredients> lii = null;
        if(action.equals("ADD"))
        {

            lii = new ArrayList<BunnyIngredients>();
            BunnyIngredients in = null;

            for(int x = 0; x<= ingredients.size()-1; x++)
            {
                in = new BunnyIngredients(0,ingredients.get(x).getItem_id(),
                                          ingredients.get(x).getDescr(),
                                           ingredients.get(x).getPrice());
                lii.add(in);

            }


        }
        else if(action.equals("SUBTRACT"))
        {

            lii = new ArrayList<BunnyIngredients>();
            if(bunnyIngredientsPerBun == null)
            {
                Log.w("SortItems()","bunnyIngredientsPerBun is null");
            }
            else
            {
                Log.w("SortItems()","bunnyIngredientsPerBun is not null");

            }

            for(int x = 0; x<= bunnyIngredientsPerBun.size()-1;x++)
            {
                if(bunnyIngredientsPerBun.get(x).getBunny_id() == bunniesCartItem.getBunny().getItem_id())
                {

                    lii.add(bunnyIngredientsPerBun.get(x));
                }
            }

        }
        Log.w("List",lii.toString());
        return  lii;

    }



    private void HandleButtonEvents()
    {

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();

            }
        });


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(action.equals("ADD")) {

                    HashMap<Integer,BunnyIngredients> items = ad.getSelectedItems();
                    BunnyIngredients bunIngr = null;
                    Bunny bunny = null;
                    BunniesCartItem bCartItem = null;
                    for (Integer integer : items.keySet()) {

                        bunIngr = items.get(integer);
                        bunny = new Bunny(bunIngr.getIngr_id(), bunIngr.getDescr(), bunIngr.getPrice());
                        bunny.setName(bunIngr.getDescr());
                        Log.w("SelectedAction","Addition");

                        bCartItem = new BunniesCartItem(bunny, null, null);
                        bCartItem.setParentID(id);
                        bCartItem.setParentInd('N');
                        bCartItem.setBunnySessionID(0);
                        Log.w("ValueofID",id+"");

                        Log.w("BCartItem", bCartItem.toString());
                        Log.w("BCatItemPIND", bCartItem.getParentInd() + "PID" + bCartItem.getParentID());
                        bunniesCart.addToCart(bCartItem);
                            bunniesCartItem.addToAddedItems(bCartItem.getBunnyItemTotal());
                        //bunniesCartItem.setTotalWithAddedItems(bunniesCartItem.getTotalWithAddedItems()+bCartItem.getBunnyItemTotal());

                    }



                }

                else {


                }


              //  hostActivity.passIngredientsData(bunniesCartItem,id);
                appController.setBunniesCart(bunniesCart);
                hostActivity.refreshAdapter();
                dismiss();


            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.hostActivity = (CartActivity)getActivity();



    }

    public interface IngredientsPasser
    {
        public void passIngredientsData(BunniesCartItem cartItem, int position);
        public void passAddedRemovedItems(BunniesCart bunniesCart);
        public void testDataPass(String data);

    }
}
