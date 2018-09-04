package dev.com.jtd.toodles.background;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import app.AppController;
import dev.com.jtd.toodles.R;
import dev.com.jtd.toodles.model.BunniesCartItem;
import dev.com.jtd.toodles.model.Bunny;
import dev.com.jtd.toodles.view.MainMenuActivity;

/**
 * Created by serame on 10/5/2016.
 */
public class BunnyAdapter  extends RecyclerView.Adapter<BunnyAdapter.BunnyViewHolder>{

    private List<BunniesCartItem> bunnies;
    private HashMap<Integer,BunniesCartItem> listOnDisplay;
    private BunniesCart bunniesCart;
    private AppController appController;
    private MainMenuActivity mma;
    public BunnyAdapter() {


    }
    public BunnyAdapter(List<BunniesCartItem> mBunnies,MainMenuActivity mma)
    {

        this.bunnies = mBunnies;
        listOnDisplay = new HashMap<Integer,BunniesCartItem>();
        appController = AppController.getInstance();
        bunniesCart = appController.getBunniesCart();
        this.mma = mma;

    }
    @Override
    public BunnyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_row,parent,false);

        return new BunnyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BunnyViewHolder holder, int position) {

        BunniesCartItem bunniesCartItem = bunnies.get(position);
        Bunny bunny = bunniesCartItem.getBunny();
        holder.lblPrice.setText("R"+bunny.getPrice()+"");
        holder.lblDescription.setText(bunny.getDescr());
        holder.lblItemName.setText(bunny.getName());
        holder.cartItem = bunniesCartItem;
        holder.lblIndItemCount.setText(String.valueOf(0));
        listOnDisplay.put(position,bunniesCartItem);

    }



    @Override
    public int getItemCount() {

        if (bunnies != null) {

            return bunnies.size();
        }
        else
        {
            return 0;
        }


    }

    public BunniesCartItem getBunniesCartItem(int position)
    {
        BunniesCartItem bunniesCartItem = null;
        bunniesCartItem = listOnDisplay.get(new Integer(position));
        return bunniesCartItem;

    }

    class BunnyViewHolder extends RecyclerView.ViewHolder{

        TextView lblPrice;
        TextView lblDescription;
        TextView lblItemName;
        TextView lblIndItemCount;
        ImageButton btnAddToCart;
        ImageButton btnRemoveFromCart;
        BunniesCartItem cartItem;
        int indItemCount = 0;


        BunnyViewHolder(View view)
        {
            super(view);
            this.lblItemName = view.findViewById(R.id.lblItemName);
            this.lblPrice = view.findViewById(R.id.lblPrice);
            this.lblDescription = view.findViewById(R.id.lbDescription);
            this.lblIndItemCount = view.findViewById(R.id.txtIndItemCount);
            this.btnAddToCart = view.findViewById(R.id.btnImgAddToCart);
            this.btnRemoveFromCart = view.findViewById(R.id.btnImgRemoveFromCart);

            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    indItemCount++;
                    lblIndItemCount.setText(String.valueOf(indItemCount));
                    int sessionID = appController.getBunnySessionID();
                    cartItem.setBunnySessionID(sessionID);
                    cartItem.setParentInd('Y');
                    cartItem.setParentID(0);
                    bunniesCart.addToCart(cartItem);
                    mma.updateLabels(bunniesCart.getCount(),bunniesCart.getTotal());

                    Log.w("CartItemSessionID",String.valueOf(sessionID));

                }

            });

            btnRemoveFromCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(indItemCount > 0) {
                        indItemCount--;
                        lblIndItemCount.setText(String.valueOf(indItemCount));
                        bunniesCart.removeFromCart(cartItem);
                        mma.updateLabels(bunniesCart.getCount(),bunniesCart.getTotal());
                    }

                }
            });
        }



    }
}
