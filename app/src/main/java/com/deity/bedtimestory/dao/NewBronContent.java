package com.deity.bedtimestory.dao;

import java.util.List;

/**
 * 正文内容
 * Created by Deity on 2016/11/13.
 */

public class NewBronContent {
    private List<NewBornContentEntity> newBornContentEntities;
    private String nextPageUrl;

    public List<NewBornContentEntity> getNewBornContentEntities() {
        return newBornContentEntities;
    }

    public void setNewBornContentEntities(List<NewBornContentEntity> newBornContentEntities) {
        this.newBornContentEntities = newBornContentEntities;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }
}
