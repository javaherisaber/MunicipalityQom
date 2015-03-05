package ir.highteam.shahrdari.qom.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ir.highteam.shahrdari.qom.R;
import ir.highteam.shahrdari.qom.adapter.AdapterMessageRecycler;
import ir.highteam.shahrdari.qom.bundle.BundleMessage;
import ir.highteam.shahrdari.qom.database.ShahrdariDB;

public class ActivityMessage extends AppCompatActivity {

    private RecyclerView learnRecycler;
    private LinearLayoutManager linearLayoutManager;
    private Toolbar toolbar;
    ShahrdariDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initToolbar();
        initObjectAndViews();
        initListenersAndEvent();
        initLearnRecycler();

    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initLearnRecycler(){
        List<BundleMessage> messageList = new ArrayList<>();


        db.open();
        Cursor mcursor = db.getAllRecords(ShahrdariDB.TABLE_LOTTERY, ShahrdariDB.COLUMNS_LOTTERY);
        mcursor.moveToFirst();

        if(mcursor.getCount() != 0)
        {
            do
            {
                BundleMessage bundleMessage = new BundleMessage();

                bundleMessage.lotteryCode = mcursor.getString(1);
                bundleMessage.pursueCode = mcursor.getString(2);
                bundleMessage.date = mcursor.getString(3);

                messageList.add(bundleMessage);
            } while (mcursor.moveToNext());
        }

        AdapterMessageRecycler adapterLearnRecycler = new AdapterMessageRecycler(messageList, getApplicationContext());
        learnRecycler.setLayoutManager(linearLayoutManager);
        learnRecycler.setAdapter(adapterLearnRecycler);

    }

    private void initListenersAndEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityMessage.this.finish();
            }
        });
    }

    private void initObjectAndViews(){
        db = new ShahrdariDB(getApplicationContext());
        learnRecycler = (RecyclerView) findViewById(R.id.messageRecycler);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        db.close();
    }

}
