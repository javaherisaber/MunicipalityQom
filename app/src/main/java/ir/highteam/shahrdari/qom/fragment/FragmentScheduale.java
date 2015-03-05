package ir.highteam.shahrdari.qom.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ir.highteam.shahrdari.qom.adapter.AdapterSchedualeRecycler;
import ir.highteam.shahrdari.qom.bundle.BundleScheduale;
import ir.highteam.shahrdari.qom.R;

/**
 * Created by mohammad on 5/16/2016.
 */
public class FragmentScheduale  extends Fragment {
    View rootView;
    private RecyclerView newsRecycler;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_scheduale, container, false);
        initObjectAndViews();
        initNewsRecycler();
        return rootView;
    }
    private void initObjectAndViews(){

        newsRecycler = (RecyclerView) rootView.findViewById(R.id.newsRecycler);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    private void initNewsRecycler(){
        List<BundleScheduale> newsList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            BundleScheduale bundleScheduale = new BundleScheduale();
            bundleScheduale.day = "روز : ";
            bundleScheduale.address = "محل تحویل بازیافت";
            newsList.add(bundleScheduale);
        }

        AdapterSchedualeRecycler adapterNewsRecycler = new AdapterSchedualeRecycler(newsList, getActivity().getApplicationContext());
        newsRecycler.setLayoutManager(linearLayoutManager);
        newsRecycler.setAdapter(adapterNewsRecycler);

    }

}
