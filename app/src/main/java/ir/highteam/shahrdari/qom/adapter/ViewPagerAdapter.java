package ir.highteam.shahrdari.qom.adapter;
 
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ir.highteam.shahrdari.qom.fragment.FragmentLearn;
import ir.highteam.shahrdari.qom.fragment.FragmentNews;
import ir.highteam.shahrdari.qom.fragment.FragmentSendCode;

/**
 * Created by Admin on 11-12-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
 
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int position) {


        if(position == 0){
            return new FragmentSendCode();
        }
        else if(position == 1){
            return new FragmentNews();
        }

//        else if (position == 2){
//            return new FragmentScheduale();
//        }
        return new FragmentLearn();

    }
 
    @Override
    public int getCount() {
        return 4;
    }
    
}