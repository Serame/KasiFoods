package dev.com.jtd.toodles.background;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;

import dev.com.jtd.toodles.R;
import dev.com.jtd.toodles.model.BunnyIngredients;

/**
 * Created by smoit on 2017/03/19.
 */

public class IngredientsListAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<BunnyIngredients> ingredientsList;
    private HashMap<Integer,BunnyIngredients> selectedItems;
    private SparseIntArray checkeckedItems;

    public IngredientsListAdapter(Context ctx,ArrayList<BunnyIngredients> inLst)
    {
        super(ctx,R.layout.ingredients_single_row,inLst);
        this.context = ctx;
        this.ingredientsList = inLst;
        this.selectedItems = new HashMap<Integer,BunnyIngredients>();
        checkeckedItems = new SparseIntArray();
    }

    

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ingredients_single_row,parent,false);
        final TextView txtIngrDescr = (TextView) view.findViewById(R.id.txtIngrDescr);
        TextView txtIngrPrice = (TextView) view.findViewById(R.id.txtIngrPrice);
        CheckBox chk = (CheckBox) view.findViewById(R.id.chkSelect);

        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(isChecked)
                {
                    selectedItems.put(position,ingredientsList.get(position));
                    Log.w("Selectd item",selectedItems.get(position).getDescr());

                }
                else
                {
                    Log.w("deselectd item",selectedItems.get(position).getDescr());
                    selectedItems.remove(position);

                }

            }
        });

        txtIngrDescr.setText(this.ingredientsList.get(position).getDescr());
        txtIngrPrice.setText("R"+this.ingredientsList.get(position).getPrice());



        return view;
    }

    public HashMap<Integer,BunnyIngredients>getSelectedItems()
    {
        return this.selectedItems;
    }


}