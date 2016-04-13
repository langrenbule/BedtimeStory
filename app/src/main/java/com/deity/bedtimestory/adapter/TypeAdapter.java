package com.deity.bedtimestory.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.deity.bedtimestory.fragment.MainFragment;

/**
 * 栏目类别
 * Created by fengwenhua on 2016/4/12.
 */
public class TypeAdapter extends FragmentPagerAdapter {
    private final static String[] MY_TITLES ={"故事会","宝宝睡前故事","段子手"};

    public TypeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new MainFragment(1);
    }

    @Override
    public int getCount() {
        return MY_TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return MY_TITLES[position%MY_TITLES.length];
    }
}
