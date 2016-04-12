package com.deity.bedtimestory;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.deity.bedtimestory.adapter.TypeAdapter;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.indicator)
    public TabPageIndicator mIndicator;
    @Bind(R.id.pager)
    public ViewPager mContentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        TypeAdapter typeAdapter = new TypeAdapter(getSupportFragmentManager());
        mContentPage.setAdapter(typeAdapter);
        mIndicator.setViewPager(mContentPage);
    }
}
