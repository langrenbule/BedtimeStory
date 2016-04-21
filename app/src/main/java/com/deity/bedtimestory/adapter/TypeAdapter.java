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
    private final static String[] MY_TITLES ={"人物传记","人生路程","情感故事","成长经历","为人处世","职场法则","环球视野","青春无悔"};

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
