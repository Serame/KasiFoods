package dev.com.jtd.toodles.background;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import dev.com.jtd.toodles.R;

public class KasiListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listHeaders_kasis;
    private HashMap<String,List<String>> listData_shops;




    public KasiListAdapter(Context context, List<String> listHeaders_kasis, HashMap<String, List<String>> listData_shops) {
        this.context = context;
        this.listHeaders_kasis = listHeaders_kasis;
        this.listData_shops = listData_shops;
    }

    @Override
    public int getGroupCount() {

        return this.listHeaders_kasis.size();

    }

    @Override
    public int getChildrenCount(int groupPosition) {


        if(listData_shops.get(listHeaders_kasis.get(groupPosition)) != null)
        {
            return this.listData_shops.get(this.listHeaders_kasis.get(groupPosition)).size();
        }

        return 0;

    }

    @Override
    public Object getGroup(int groupPosition) {

        return this.listHeaders_kasis.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {


        return this.listData_shops.get(this.listHeaders_kasis.get(groupPosition)).get(childPosition);

    }

    @Override
    public long getGroupId(int i) {

        return i;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;
    }

    @Override
    public boolean hasStableIds() {

        return true;
    }

    @Override
    public View getGroupView(int groupPostion, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String)getGroup(groupPostion);
        View groupView;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            groupView = inflater.inflate(R.layout.list_kasigroup,null);
        }
        else
            groupView = convertView;

        TextView txtKasiName = groupView.findViewById(R.id.txtKasiName);
        txtKasiName.setTypeface(null, Typeface.BOLD);
        txtKasiName.setText(headerTitle);


        return groupView;
    }

    @Override
    public View getChildView(int groupPostion, int childPostion, boolean isLastChild, View convertView, ViewGroup viewGroup) {


        final String childText = (String)getChild(groupPostion,childPostion);
        View childView;

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            childView = inflater.inflate(R.layout.list_chld_group,null);
        }
        else
            childView = convertView;

        TextView txtShopText = childView.findViewById(R.id.txtShopName);
        txtShopText.setText(childText);

        return childView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {

        return true;
    }


}
