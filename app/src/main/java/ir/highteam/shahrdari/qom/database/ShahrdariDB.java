package ir.highteam.shahrdari.qom.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ir.highteam.shahrdari.qom.bundle.BundleLottery;

/**
 * Created by Mahdi on 19/05/2016.
 */
public class ShahrdariDB {

    private static final String DB_PATH = "/data/data/ir.highteam.shahrdari.qom/databases/";
    private static final String DATABASE_NAME = "Shahrdari.db";
    private static final int    DATABASE_VERSION = 1;
    private static final String TAG = "Shahrdari_Database";

    public static final String TABLE_NEWS="news";
    public static final String TABLE_LESSON="lesson";
    public static final String TABLE_LOTTERY="lottery";

    private static final String FIELD_ID = "_id";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_SHORT_DES = "short_des";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_PICTURE = "picture";
    private static final String FIELD_LOTTERY_CODE = "lottery_code";
    private static final String FIELD_PURSUE_CODE = "pursue_code";
    private static final String FIELD_DATE_TIME = "date_time";

    public static final String[] COLUMNS_NEWS = {FIELD_ID,FIELD_TITLE,FIELD_SHORT_DES,FIELD_DESCRIPTION,FIELD_PICTURE};
    public static final String[] COLUMNS_LESSON = {FIELD_ID,FIELD_TITLE,FIELD_DESCRIPTION};
    public static final String[] COLUMNS_LOTTERY = {FIELD_ID,FIELD_LOTTERY_CODE,FIELD_PURSUE_CODE,FIELD_DATE_TIME};

    private static final String CREATE_TABLE_LOTTERY =
            "create table " + TABLE_LOTTERY + "(" + FIELD_ID + " integer primary key autoincrement, "
                    + FIELD_LOTTERY_CODE + " text not null,"
                    + FIELD_PURSUE_CODE + " text not null,"
                    + FIELD_DATE_TIME + " text not null);";


    private final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public ShahrdariDB(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
        try {

            DBHelper.createDataBase();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {

        private Context context;

        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_TABLE_LOTTERY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
        }

        private boolean checkDataBase() {
            try {
                final String DbPath = DB_PATH + DATABASE_NAME;
                final File file = new File(DbPath);//file for checking our database existence
                if (file.exists())
                {
                    Log.d(TAG, "Database already exist");
                    return true;
                }
                else
                {
                    return false;
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            }
        }

        public void createDataBase() throws IOException {
            boolean mDataBaseExist = checkDataBase();
            if (!mDataBaseExist) {
                this.getReadableDatabase();//makes our database readable
                try {
                    copyDataBase();//copy database from assets folder
                } catch (IOException mIOException) {
                    mIOException.printStackTrace();
                    throw new Error("Error copying database");
                } finally {
                    this.close();//closing this IO operation after accomplishing it
                }
            }
        }

        private void copyDataBase() throws IOException {
            try {
                //using an inputstram to open database from assets
                InputStream mInputStream = context.getAssets().open(DATABASE_NAME);
                String outFileName = DB_PATH + DATABASE_NAME;//output file dir
                OutputStream mOutputStream = new FileOutputStream(outFileName);//outputstream to make new database
                byte[] buffer = new byte[1024];//buffer size for write operation
                int length;
                while ((length = mInputStream.read(buffer)) > 0) {
                    mOutputStream.write(buffer, 0, length);//writing output byte by byte
                }
                mOutputStream.flush();//flushes the stream
                mOutputStream.close();
                mInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //---opens the database---
    public ShahrdariDB open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public synchronized void close()
    {
        DBHelper.close();
    }

    public long insertLottery(BundleLottery bundleLottery)
    {
        ContentValues values = new ContentValues();
        values.put(FIELD_LOTTERY_CODE,bundleLottery.lotteryCode);
        values.put(FIELD_PURSUE_CODE,bundleLottery.pursueCode);
        values.put(FIELD_DATE_TIME,bundleLottery.dateTime);

        return db.insert(TABLE_LOTTERY,null,values);
    }

    public Cursor getAllRecords(String table, String[] columns)
    {
        return db.query(table, columns, null, null, null, null, null);
    }

    public Cursor getLessonWithID(int id)
    {
        String sql="Select * from "+TABLE_LESSON+" where "+FIELD_ID+" = "+id;
        return db.rawQuery(sql,null);
    }

    public Cursor getNewsWithID(int id)
    {
        String sql="Select * from "+TABLE_NEWS+" where "+FIELD_ID+" = "+id;
        return db.rawQuery(sql,null);
    }

}


