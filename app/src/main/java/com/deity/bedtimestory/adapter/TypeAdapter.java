package com.deity.bedtimestory.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.deity.bedtimestory.entity.NewBornItemType;
import com.deity.bedtimestory.fragment.MainFragment;

/**
 * 栏目类别
 * Created by fengwenhua on 2016/4/12.
 */
public class TypeAdapter extends FragmentPagerAdapter {
//    private final static String[] MY_TITLES ={"人物传记","人生路程","情感故事","成长经历","为人处世","职场法则","环球视野","青春无悔","日常生活","人生智慧","生活乐趣","作文素材"};
    private final static String[] MY_TITLES_BABY ={NewBornItemType.十月怀胎.toString(),NewBornItemType.一朝分娩.toString(),NewBornItemType.亲子共读.toString(),NewBornItemType.六月宝宝.toString(),NewBornItemType.准爸爸读本.toString(),NewBornItemType.周年宝宝.toString(),NewBornItemType.孕中晚期.toString(),NewBornItemType.孕前准备.toString(),NewBornItemType.孕早期.toString(),NewBornItemType.小五神童.toString(),NewBornItemType.幼儿心理.toString(),NewBornItemType.生活照顾.toString(),NewBornItemType.营养健康.toString()};

    public TypeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        return new MainFragment(Params.NewType.whichOne(Params.TargetUrl.mUrl.get(position)));
        return new MainFragment(NewBornItemType.whichNewBornContentType(position));
    }

    @Override
    public int getCount() {
        return MY_TITLES_BABY.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return MY_TITLES_BABY[position%MY_TITLES_BABY.length];
    }
}
