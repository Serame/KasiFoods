package dev.com.jtd.toodles.background;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import dev.com.jtd.toodles.R;
import dev.com.jtd.toodles.model.PlacedOrders;


/**
 * Created by smoit on 2018/03/18.
 */

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.HistoryViewHolder> {

    private Context context;
    private ArrayList<PlacedOrders> placedOrders;

    public OrderHistoryAdapter(Context context, ArrayList<PlacedOrders> pls) {
        this.context = context;
        this.placedOrders = pls;


    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.w("onCreateViewHolder","onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_history,parent,false);
        return new HistoryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {


        holder.txtOrderID.setText(String.valueOf(placedOrders.get(position).getOrderID()));
        holder.txtOrderDescr.setText(placedOrders.get(position).getOrderDescription());
        holder.txtOrderDate.setText(holder.txtOrderDate.getText()+ placedOrders.get(position).getCompletionDate());
        holder.txtOrderTotal.setText(holder.txtOrderTotal.getText()+String.valueOf(placedOrders.get(position).getTotamount()));

        Log.w("AtAdapter", placedOrders.get(position).getOrderDescription().toString());
    }



    @Override
    public int getItemCount() {
        if(this.placedOrders != null)
        {
            return this.placedOrders.size();
        }
        else
        {
            return 0;
        }

    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        TextView txtOrderID,txtOrderDate,txtOrderDescr,txtOrderTotal;


        public HistoryViewHolder(View view) {
            super(view);
            txtOrderID = view.findViewById(R.id.txtHistOrderID);
            txtOrderDate = view.findViewById(R.id.txtHistOrderDate);
            txtOrderDescr = view.findViewById(R.id.txtHistOrderDes);
            txtOrderTotal = view.findViewById(R.id.txtOrderHistTotal);
        }
    }
}
