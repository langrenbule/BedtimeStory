package com.deity.bedtimestory.dao;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * 育儿资讯内容摘要
 * Created by Deity on 2016/11/8.
 */

public class NewBornItemEntity extends RealmObject {
    @PrimaryKey
    private long newBornId = System.currentTimeMillis();
    @Index
    private String newBornTitle;
    private String newBornArticleUrl;
    private String newBornDescription;
    /***
     * width:150px;height:155px;background-image:url(data/attachment/portal/201403/03/090539zmpazy8mi6n58x6i.jpg.thumb.jpg);background-position:-70px;
     * 需要对该内容进行转义
     */
    private String newBornImageUrl;
    private String newBornData;

    public String getNewBornTitle() {
        return newBornTitle;
    }

    public void setNewBornTitle(String newBornTitle) {
        this.newBornTitle = newBornTitle;
    }

    public String getNewBornDescription() {
        return newBornDescription;
    }

    public void setNewBornDescription(String newBornDescription) {
        this.newBornDescription = newBornDescription;
    }

    public String getNewBornImageUrl() {
        return newBornImageUrl;
    }

    public void setNewBornImageUrl(String newBornImageUrl) {
        this.newBornImageUrl = newBornImageUrl;
    }

    public String getNewBornData() {
        return newBornData;
    }

    public void setNewBornData(String newBornData) {
        this.newBornData = newBornData;
    }

    public String getNewBornArticleUrl() {
        return newBornArticleUrl;
    }

    public void setNewBornArticleUrl(String newBornArticleUrl) {
        this.newBornArticleUrl = newBornArticleUrl;
    }

    public long getNewBornId() {
        return newBornId;
    }

    public void setNewBornId(long newBornId) {
        this.newBornId = newBornId;
    }
}
