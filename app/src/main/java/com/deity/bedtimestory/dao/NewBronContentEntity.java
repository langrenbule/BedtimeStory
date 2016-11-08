package com.deity.bedtimestory.dao;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Deity on 2016/11/8.
 */

public class NewBronContentEntity extends RealmObject {
    @PrimaryKey
    private long newBornContentId = System.currentTimeMillis();
    @Index
    private String newBornContentTitle;
}
