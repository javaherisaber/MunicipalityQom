package ir.highteam.shahrdari.qom.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ir.highteam.shahrdari.qom.R;
import ir.highteam.shahrdari.qom.adapter.AdapterNewsRecycler;
import ir.highteam.shahrdari.qom.bundle.BundleNews;
import ir.highteam.shahrdari.qom.database.ShahrdariDB;

/**
 * Created by Admin on 11-12-2015.
 */
public class FragmentNews extends Fragment {
    View rootView;
    private RecyclerView newsRecycler;
    private LinearLayoutManager linearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_news, container, false);
        initObjectAndViews();
        initNewsRecycler();
        return rootView;
    }

    private void initNewsRecycler(){
        List<BundleNews> newsList = new ArrayList<>();

        ShahrdariDB db = new ShahrdariDB(getActivity().getApplicationContext());
        db.open();
        Cursor mcursor = db.getAllRecords(ShahrdariDB.TABLE_NEWS, ShahrdariDB.COLUMNS_NEWS);
        mcursor.moveToFirst();

        if(mcursor.getCount() != 0)
        {
            do
            {
                BundleNews bundleNews = new BundleNews();
                bundleNews.newsId = mcursor.getInt(0);
                bundleNews.title = mcursor.getString(1);
                bundleNews.shortDes = mcursor.getString(2);
                bundleNews.picture = mcursor.getString(4);
                newsList.add(bundleNews);

            } while (mcursor.moveToNext());
        }

        AdapterNewsRecycler adapterNewsRecycler = new AdapterNewsRecycler(newsList, getActivity().getApplicationContext());
        newsRecycler.setLayoutManager(linearLayoutManager);
        newsRecycler.setAdapter(adapterNewsRecycler);

    }

    private void initObjectAndViews(){
        newsRecycler = (RecyclerView) rootView.findViewById(R.id.newsRecycler);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }
}