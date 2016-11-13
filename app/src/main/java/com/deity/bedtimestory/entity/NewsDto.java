package com.deity.bedtimestory.entity;

import java.util.List;

public class NewsDto {
    private List<News> newses;
    private String nextPageUrl;

    public List<News> getNewses() {
        return this.newses;
    }

    public void setNewses(List<News> newses) {
        this.newses = newses;
    }

    public String getNextPageUrl() {
        return this.nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }
}