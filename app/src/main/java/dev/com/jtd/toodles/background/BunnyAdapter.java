package dev.com.jtd.toodles.background;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import dev.com.jtd.toodles.R;
import dev.com.jtd.toodles.model.BunniesCartItem;
import dev.com.jtd.toodles.model.Bunny;

/**
 * Created by serame on 10/5/2016.
 */
public class BunnyAdapter  extends RecyclerView.Adapter<BunnyAdapter.BunnyViewHolder>{

    private List<BunniesCartItem> bunnies;
    private HashMap<Integer,BunniesCartItem> listOnDisplay;
    public BunnyAdapter() {



    }
    public BunnyAdapter(List<BunniesCartItem> mBunnies)
    {

        this.bunnies = mBunnies;
        listOnDisplay = new HashMap<Integer,BunniesCartItem>();
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
        listOnDisplay.put(new Integer(position),bunniesCartItem);

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


        BunnyViewHolder(View view)
        {
            super(view);
            this.lblItemName = view.findViewById(R.id.lblItemName);
            this.lblPrice = view.findViewById(R.id.lblPrice);
            this.lblDescription = view.findViewById(R.id.lbDescription);
        }



    }
}
