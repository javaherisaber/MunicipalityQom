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
import ir.highteam.shahrdari.qom.adapter.AdapterLearnRecycler;
import ir.highteam.shahrdari.qom.bundle.BundleLearn;
import ir.highteam.shahrdari.qom.database.ShahrdariDB;

/**
 * Created by mohammad on 5/19/2016.
 */

public class FragmentLearn extends Fragment {
    View rootView;
    private RecyclerView learnRecycler;
    private LinearLayoutManager linearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_learn, container, false);
        initObjectAndViews();
        initLearnRecycler();
        return rootView;
    }



    private void initLearnRecycler(){
        List<BundleLearn> learnList = new ArrayList<>();

        ShahrdariDB db = new ShahrdariDB(getActivity().getApplicationContext());
        db.open();
        Cursor mcursor = db.getAllRecords(ShahrdariDB.TABLE_LESSON, ShahrdariDB.COLUMNS_LESSON);
        mcursor.moveToFirst();

        if(mcursor.getCount() != 0)
        {
            int i = 1;
            do
            {
                BundleLearn bundleLearn = new BundleLearn();
                bundleLearn.lessonId = mcursor.getInt(0);
                bundleLearn.title = mcursor.getString(1);
                int temp;
                if(i>18){
                    temp = i - 16;
                }
                else if (i>9){
                    temp = i - 7;
                }
                else {
                    temp = i + 2;
                }
                bundleLearn.image = "a" + temp;
                bundleLearn.count = String.valueOf(i);
                learnList.add(bundleLearn);

                i++;
            } while (mcursor.moveToNext());
        }

        AdapterLearnRecycler adapterLearnRecycler = new AdapterLearnRecycler(learnList, getActivity().getApplicationContext());
        learnRecycler.setLayoutManager(linearLayoutManager);
        learnRecycler.setAdapter(adapterLearnRecycler);

    }

    private void initObjectAndViews(){
        learnRecycler = (RecyclerView) rootView.findViewById(R.id.learnRecycler);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }
}
