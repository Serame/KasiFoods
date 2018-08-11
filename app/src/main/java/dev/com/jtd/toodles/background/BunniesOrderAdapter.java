package dev.com.jtd.toodles.background;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dev.com.jtd.toodles.R;
import dev.com.jtd.toodles.model.BunniesCartItem;

/**
 * Created by smoit on 2017/02/28.
 */

public class BunniesOrderAdapter extends RecyclerView.Adapter<BunniesOrderAdapter.BunnyOrderViewHolder>{

    private ArrayList<BunniesCartItem> orderItems;

    public BunniesOrderAdapter(ArrayList<BunniesCartItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public BunnyOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_single_row,parent,false);
        return new BunnyOrderViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BunnyOrderViewHolder holder, int position) {

        BunniesCartItem item = this.orderItems.get(position);
        holder.txtOrderBunnyTotal.setText("R"+(item.getBunnyItemTotal()+item.getAddedItemsTotal()));
        holder.txtOrderBunnyPrice.setText("R"+item.getBunny().getPrice());
        holder.txtAddedItemsTotal.setText("R"+item.getAddedItemsTotal()+"");
        holder.txtOrderName.setText(item.getBunny().getName());
        holder.txtOrderBunnyDescr.setText(item.getBunny().getDescr());



    }

    @Override
    public int getItemCount() {

        if(this.orderItems != null)
        {
            return orderItems.size();
        }
        else
        return 0;
    }

    class BunnyOrderViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtOrderName;
        TextView txtOrderBunnyDescr;
        TextView txtOrderBunnyPrice;
        TextView txtOrderBunnyTotal;
        TextView txtAddedItemsTotal;



        BunnyOrderViewHolder(View itemView) {
            super(itemView);

            txtOrderName =  itemView.findViewById(R.id.txtOrderBunnyName);
            txtOrderBunnyDescr = itemView.findViewById(R.id.txtOrderBunnyDescr);
            txtOrderBunnyPrice = itemView.findViewById(R.id.txtOrderBunnyPrice);
            txtOrderBunnyTotal = itemView.findViewById(R.id.txtOrderBunnyTotal);
            txtAddedItemsTotal = itemView.findViewById(R.id.txtAddedItemsTotal);
        }
    }
}
