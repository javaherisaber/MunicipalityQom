package ir.highteam.shahrdari.qom.activity;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ir.highteam.shahrdari.qom.R;
import ir.highteam.shahrdari.qom.database.ShahrdariDB;

public class ActivityNews extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    Typeface tfSans;
    TextView txtNewsContent;
    private Toolbar toolbar;
    ImageView imgNews;
    private ShahrdariDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initToolbar();
        initObjectAndView();
        initListenersAndEvent();
        db.open();
        int newsId = getIntent().getIntExtra("newsId",0);

        Cursor cursor = db.getNewsWithID(newsId);
        cursor.moveToFirst();
        String title = cursor.getString(1);
        String newsContent = cursor.getString(3);
        String newsImageUrl = cursor.getString(4);

        collapsingToolbarLayout.setCollapsedTitleTypeface(tfSans);
        collapsingToolbarLayout.setExpandedTitleTypeface(tfSans);
        txtNewsContent.setTypeface(tfSans);
        collapsingToolbarLayout.setTitle(title);

        Picasso
                .with(getApplicationContext())
                .load(newsImageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(imgNews);

        txtNewsContent.setText(newsContent);


    }

    private void initObjectAndView(){
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        txtNewsContent =(TextView) findViewById(R.id.txtNewsContent);
        tfSans = Typeface.createFromAsset(getAssets(), "IRAN Sans.ttf");
        imgNews = (ImageView) findViewById(R.id.imgNews);
        db = new ShahrdariDB(getApplicationContext());
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initListenersAndEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityNews.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_news, menu);
        return true;
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
