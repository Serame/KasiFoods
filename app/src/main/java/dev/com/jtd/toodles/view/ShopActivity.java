package dev.com.jtd.toodles.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static java.lang.Math.toIntExact;

import app.AppController;
import dev.com.jtd.toodles.R;
import dev.com.jtd.toodles.background.serviceworkers.MenuClientServerComm;
import dev.com.jtd.toodles.background.KasiListAdapter;
import dev.com.jtd.toodles.background.serviceworkers.ShopClientServerComm;
import dev.com.jtd.toodles.model.Shop;


public class ShopActivity extends Activity implements ExpandableListView.OnGroupExpandListener, ExpandableListView.OnChildClickListener {

    private ExpandableListView listViewKasi;
    private KasiListAdapter listKasiAdapter;
    private List<String> kasisList;
    private HashMap<String,List<Shop>> shopsList;
    private ProgressDialog pd;
    private AppController appController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        initItems();


    }

    private void initItems()
    {

        listViewKasi = findViewById(R.id.listKasis);
        listViewKasi.setOnGroupExpandListener(this);
        listViewKasi.setOnChildClickListener(this);
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        ShopClientServerComm scsm = new ShopClientServerComm();
        scsm.setContext(this);
        scsm.setProgressDialog(pd);
        scsm.getKasiShops();
        appController = AppController.getInstance();

    }

    @Override
    public void onGroupExpand(int i) {

    }

    public void requestKasis(HashMap<String,List<Shop>> listKasiShop)
    {
        shopsList = listKasiShop;
        Set<String> keysList = listKasiShop.keySet();
        List<String> kasis = new ArrayList<>();

        for(String key : keysList){
            kasis.add(key);
        }
        this.listKasiAdapter = new KasiListAdapter(this,kasis,listKasiShop);
        this.listViewKasi.setAdapter(listKasiAdapter);
        listKasiAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPos, int childPos, long l) {

//        Log.w("ClickedChld",expandableListView.getExpandableListAdapter().getChild(groupPos,childPos).toString()+" ID: "+expandableListView.getExpandableListAdapter().getChildId(groupPos,childPos));
//        Log.w("ClickedParent",expandableListView.getExpandableListAdapter().getGroup(groupPos).toString()+" ID: "+expandableListView.getExpandableListAdapter().getGroupId(groupPos));

        List<Shop> ls = shopsList.get(expandableListView.getExpandableListAdapter().getGroup(groupPos));
        int id = (int) expandableListView.getExpandableListAdapter().getChildId(groupPos,childPos);
        Shop selectedShop = ls.get(childPos);

        Log.w("SelectedShop",selectedShop.toString());

        this.appController.setSelectedShop(selectedShop);

        Intent intent = new Intent(this,MainMenuActivity.class);
        startActivity(intent);

        return false;
    }
}
