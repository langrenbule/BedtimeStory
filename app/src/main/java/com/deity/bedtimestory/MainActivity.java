package com.deity.bedtimestory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.deity.bedtimestory.adapter.TypeAdapter;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
//    @Bind(R.id.indicator)
//    public TabPageIndicator mIndicator;
    @Bind(R.id.pager)
    public ViewPager mContentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_material);
        ButterKnife.bind(this);
        TypeAdapter typeAdapter = new TypeAdapter(getSupportFragmentManager());
        mContentPage.setAdapter(typeAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        mIndicator.setViewPager(mContentPage);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mContentPage);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("是否退出?")
                    .setMessage("你要退出应用吗?")
                    .setNegativeButton("取消",null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    }).show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
