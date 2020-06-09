package com.unity3d.player;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AlarmMainPagerAdapter extends FragmentPagerAdapter {
    private static int pagecount  = 4;
    public AlarmMainPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                sa_TabFragment_Info tabFragment_info = new sa_TabFragment_Info();
                return tabFragment_info;
            case 1:
                sa_TabFragment_Alarm tabFragment_alarm = new sa_TabFragment_Alarm();
                return tabFragment_alarm;
            case 2:
                sa_TabFragment_Statistic tabFragment_statistic = new sa_TabFragment_Statistic();
                return tabFragment_statistic;
            case 3:
                sa_TabFragment_Sysset tabFragment_setting = new sa_TabFragment_Sysset();
                return tabFragment_setting;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pagecount;
    }
}
