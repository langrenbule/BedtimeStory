package com.deity.bedtimestory.dao;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Deity on 2016/11/9.
 */

public class NewBornContentEntity extends RealmObject {
    @PrimaryKey
    private long newBornContentId = System.currentTimeMillis();
    /**正文内容*/
    @Index
    private String newBornContent;
    /**类型*/
    private int newBornType;

    public String getNewBornContent() {
        return newBornContent;
    }

    public void setNewBornContent(String newBornContent) {
        this.newBornContent = newBornContent;
    }

    public int getNewBornType() {
        return newBornType;
    }

    public void setNewBornType(int newBornType) {
        this.newBornType = newBornType;
    }
}
