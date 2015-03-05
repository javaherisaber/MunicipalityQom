package ir.highteam.shahrdari.qom.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import ir.highteam.shahrdari.qom.R;
import ir.highteam.shahrdari.qom.bundle.BundleLearn;
import ir.highteam.shahrdari.qom.database.ShahrdariDB;

public class ActivityLearn extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    private Typeface tfSans;
    TextView txtContentLearn, txtNextCourse;
    private Toolbar toolbar;
    FrameLayout btnNextCourse;
    private ShahrdariDB db;


    BundleLearn bundleNextLesson;
    BundleLearn bundleCurrentLesson;

    int lessonPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        initToolbar();
        initObjectAndView();
        initListenersAndEvent();

        db.open();

        bundleCurrentLesson.lessonId = getIntent().getIntExtra("lessonId",0);
        lessonPosition = getIntent().getIntExtra("lessonPosition", 0);

        Cursor cursor = db.getLessonWithID(bundleCurrentLesson.lessonId);
        cursor.moveToFirst();
        bundleCurrentLesson.title = cursor.getString(1);
        bundleCurrentLesson.desc = cursor.getString(2);

        cursor.close();

        Cursor allLessons = db.getAllRecords(ShahrdariDB.TABLE_LESSON,ShahrdariDB.COLUMNS_LESSON);
        allLessons.moveToFirst();
        int lessonNextPosition = lessonPosition + 1;
        if (lessonNextPosition != allLessons.getCount()) {
            for (int i = 0; i < allLessons.getCount(); i++) {
                allLessons.moveToNext();

                if (i == lessonPosition) {

                    bundleNextLesson.lessonId = allLessons.getInt(0);
                    bundleNextLesson.title = allLessons.getString(1);
                    bundleNextLesson.desc = allLessons.getString(2);
                    txtNextCourse.setText("آموزش بعدی : " + allLessons.getString(1));

                    break;
                }
            }
        }

        else
        {
            btnNextCourse.setVisibility(View.INVISIBLE);
        }

        allLessons.close();


        collapsingToolbarLayout.setCollapsedTitleTypeface(tfSans);
        collapsingToolbarLayout.setExpandedTitleTypeface(tfSans);
        collapsingToolbarLayout.setTitle(bundleCurrentLesson.title);
        txtContentLearn.setText(bundleCurrentLesson.desc);
        txtContentLearn.setTypeface(tfSans);
        txtNextCourse.setTypeface(tfSans);
    }

    private void initToolbar(){
        tfSans = Typeface.createFromAsset(getAssets(), "IRAN Sans.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initListenersAndEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityLearn.this.finish();
            }
        });
        btnNextCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityLearn.class);
                intent.putExtra("lessonId", bundleNextLesson.lessonId);
                intent.putExtra("lessonPosition", ++lessonPosition);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initObjectAndView(){
        bundleNextLesson = new BundleLearn();
        bundleCurrentLesson = new BundleLearn();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        txtContentLearn = (TextView) findViewById(R.id.txtLearnContent);
        txtNextCourse = (TextView) findViewById(R.id.txtNextCourse);
        btnNextCourse = (FrameLayout) findViewById(R.id.btnNextCourse);
        db = new ShahrdariDB(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_learn, menu);
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
