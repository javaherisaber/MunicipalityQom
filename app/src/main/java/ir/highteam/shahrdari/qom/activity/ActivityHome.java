package ir.highteam.shahrdari.qom.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import ir.highteam.shahrdari.qom.R;
import ir.highteam.shahrdari.qom.adapter.ViewPagerAdapter;
import ir.highteam.shahrdari.qom.database.ShahrdariDB;
import ir.highteam.shahrdari.qom.view.NonSwipeViewPager;

public class ActivityHome extends AppCompatActivity {

    private BottomBar bottomNavigationMenu;
    private NonSwipeViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private Toolbar toolbar;

    private ImageView btnMailBox;

    public TextView txtMailCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initToolbar();
        initObjectAndView();
        initListenerAndEvent();
        initViewPager();
        initBottomNavigationMenu(savedInstanceState);
        initMailCount();

    }

    private void initObjectAndView(){
        viewPager = (NonSwipeViewPager) findViewById(R.id.viewpager);
        btnMailBox = (ImageView) findViewById(R.id.btnMails);
        txtMailCount = (TextView) findViewById(R.id.txtMailCount);
    }

    private void initMailCount(){
        ShahrdariDB db = new ShahrdariDB(getApplicationContext());
        db.open();
        Cursor cursor = db.getAllRecords(ShahrdariDB.TABLE_LOTTERY,ShahrdariDB.COLUMNS_LOTTERY);
        int count = cursor.getCount();
        if(count == 0){
            txtMailCount.setText("0");
            txtMailCount.setVisibility(View.INVISIBLE);
        }
        txtMailCount.setText(String.valueOf(count));
        db.close();
    }

    private void initViewPager(){
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void initListenerAndEvent(){
        btnMailBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityMessage.class);
                startActivity(intent);
            }
        });
    }

    private void initBottomNavigationMenu(Bundle savedInstanceState){
        bottomNavigationMenu = BottomBar.attach(this,savedInstanceState);
        bottomNavigationMenu.useFixedMode();
        bottomNavigationMenu.setItemsFromMenu(R.menu.bottom_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    viewPager.setCurrentItem(0);
                }
                if (menuItemId == R.id.bottomBarItemOne1) {
                    viewPager.setCurrentItem(1);
                }
//                if (menuItemId == R.id.bottomBarItemOne2) {
//                    viewPager.setCurrentItem(2);
//                }
                if (menuItemId == R.id.bottomBarItemOne3) {
                    viewPager.setCurrentItem(3);
                }
//                if (menuItemId == R.id.bottomBarItemOne4) {
//                    viewPager.setCurrentItem(0);
//                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {

                }
            }
        });

        bottomNavigationMenu.setTypeFace("yekan.ttf");

        bottomNavigationMenu.selectTabAtPosition(1,true);
    }

    private void initToolbar(){
        Typeface tfSans = Typeface.createFromAsset(getAssets(), "IRAN Sans.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(tfSans);
        toolbarTitle.setText(getResources().getString(R.string.app_name));
        toolbarTitle.setTextSize(20);
        ImageView imViewSetting = (ImageView) toolbar.findViewById(R.id.btnSetting);
        imViewSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityHome.this, "Test", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bottomNavigationMenu.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
