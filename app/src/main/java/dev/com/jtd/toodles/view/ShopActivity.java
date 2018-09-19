package dev.com.jtd.toodles.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.com.jtd.toodles.R;
import dev.com.jtd.toodles.background.KasiListAdapter;

public class ShopActivity extends Activity implements ExpandableListView.OnGroupExpandListener, ExpandableListView.OnChildClickListener {

    private ExpandableListView listViewKasi;
    private KasiListAdapter listKasiAdapter;
    private List<String> kasisList;
    private HashMap<String,List<String>> shopsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        listViewKasi = findViewById(R.id.listKasis);
        listViewKasi.setOnGroupExpandListener(this);
        listViewKasi.setOnChildClickListener(this);
        prepareListDate();
        listKasiAdapter = new KasiListAdapter(this,kasisList,shopsList);
        listViewKasi.setAdapter(listKasiAdapter);
    }

    private void prepareListDate()
    {
        kasisList = new ArrayList<String>();
        shopsList = new HashMap<String,List<String>>();
    
        kasisList.add("Kagiso");
        kasisList.add("Thembisa");
        kasisList.add("Protea");
        kasisList.add("Soshanguve");
        kasisList.add("Mamelodi");
        kasisList.add("Munsiville");

        List<String> kagiso = new ArrayList<>();
        kagiso.add("Star-M Foods");
        kagiso.add("Mdeshu's Chicken");
        kagiso.add("Sedibeng African Foods");
        kagiso.add("Tso's Butchery");
        kagiso.add("Oscar Kotas");
        kagiso.add("Punkies");
        kagiso.add("First Stop Vlei");

        List<String> thembisa = new ArrayList<>();
        thembisa.add("View Kotas");
        thembisa.add("Difateng");
        thembisa.add("Kaalfonteing Foods");
        thembisa.add("Ivory Kotas");
        thembisa.add("Bunny Chows");

        List<String> protea = new ArrayList<>();
        protea.add("Extension 3 Kotas");
        protea.add("Extension 5 Kotas");
        protea.add("Social link foods");
        protea.add("Second Stop");

        List<String> sosha = new ArrayList<>();
        sosha.add("Diphatlo tsa Chongo");
        sosha.add("Block M Chips");
        sosha.add("Transfer Foods");
        sosha.add("Telkom Diphatlo spot");
        sosha.add("Vick's Cafe");
        sosha.add("Short Left");
        sosha.add("McNoose Cafe");
        sosha.add("Complex Foods");

        List<String> munsiville = new ArrayList<>();
        munsiville.add("Paul's Chicken Dust");
        munsiville.add("Mpumpu kotas");
        munsiville.add("Skopo Foods");
        munsiville.add("Brick's Cafe");

        shopsList.put(kasisList.get(0),kagiso);
        shopsList.put(kasisList.get(1),thembisa);
        shopsList.put(kasisList.get(2),protea);
        shopsList.put(kasisList.get(3),sosha);
        shopsList.put(kasisList.get(4),munsiville);

    }

    @Override
    public void onGroupExpand(int i) {

    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPos, int childPos, long l) {

        Intent intent = new Intent(this,MainMenuActivity.class);
        startActivity(intent);

        return false;
    }
}
