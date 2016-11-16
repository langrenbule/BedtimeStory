package com.deity.bedtimestory.data;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by fengwenhua on 2016/4/12.
 */
public class MyApplication extends Application{
    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        initDB();
    }

    public void initDB(){
        //不关心数据库更新，数据库变动，则删除数据库
        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext()).deleteRealmIfMigrationNeeded() .build();
        Realm.setDefaultConfiguration(config);
    }

}
