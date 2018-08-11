package dev.com.jtd.toodles.background;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dev.com.jtd.toodles.model.BunniesCartItem;
import dev.com.jtd.toodles.model.Bunny;

/**
 * Created by serame on 10/25/2016.
 */
public class BunniesCart  implements Serializable{

    private int count;
    private double total;
    private ArrayList<BunniesCartItem> bunniesCart;
    private ArrayList<BunniesCartItem> ingredientsCart;
    public static final String BUNNIES_CART =  "bunniesCart";
    public BunniesCart()
    {
        this.count = 0;
        this.total = 0;
        this.bunniesCart = new ArrayList<BunniesCartItem>();//This cart is for holding all MenuItems of type 1 which are actuall bunnies
        this.ingredientsCart = new ArrayList<BunniesCartItem>();//This cart is for holding extrass added to the bunny

    }

    public boolean addToCart(BunniesCartItem bunnyItem)
    {
        boolean added = false;

        if(bunnyItem.getParentInd() == 'Y')
        {
            added = this.bunniesCart.add(bunnyItem);
            this.total += bunnyItem.getBunny().getPrice();

        }
        else if(bunnyItem.getParentInd() == 'N')
        {
            added = this.ingredientsCart.add(bunnyItem);
            this.total += bunnyItem.getBunny().getPrice();

        }

        return added;
    }

    public boolean removeFromCart(BunniesCartItem bunnyItem)
    {

        boolean removed = this.bunniesCart.remove(bunnyItem);

        if(removed == true) {

            total -= bunnyItem.getBunny().getPrice();
            Bunny bunny;

            for(int x = 0 ; x<= ingredientsCart.size()-1; x++)
            {
                //This is to remove any ingredients added to the bunny being removed

                double price = 0;
                if(ingredientsCart.get(x).getParentInd() == 'N' && ingredientsCart.get(x).getParentID() == bunnyItem.getBunnySessionID())
                {
                    bunny = ingredientsCart.get(x).getBunny();
                    price = bunny.getPrice();
                    Log.w("BunniesCart","Removing "+bunny.getDescr());
                    Log.w("IngredientPrice","R"+price);
                    total = total - price;
                    //ingredientsCart.remove(bunny);

                }
            }

        }

        return removed;
    }


    public BunniesCartItem getCartItemAt(int position)
    {
        return this.bunniesCart.get(position);
    }

    //This method will return all the extra added ingredients of a bunny
    public ArrayList<BunniesCartItem> getBunnyIngredients(int parentID)
    {
        ArrayList<BunniesCartItem> addedItems = new ArrayList<BunniesCartItem>();
        for(int x = 0; x <= ingredientsCart.size()-1; x++)
        {
            if(ingredientsCart.get(x).getParentID() == parentID)
            {
                addedItems.add(ingredientsCart.get(x));
            }
        }

        return  addedItems;
    }

    public List<BunniesCartItem> getAllBunniesItem()
    {
        return this.bunniesCart;
    }

    public ArrayList<BunniesCartItem>getAllAddedItems()
    {
        return this.ingredientsCart;
    }

    public int getCount()
    {
        return this.bunniesCart.size();

    }

    public void clearCart()
    {
        this.bunniesCart.clear();
        this.ingredientsCart.clear();
        this.total = 0;
        this.count = 0;
    }

    public double getTotal()
    {
        return this.total;
    }
}
