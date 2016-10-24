package com.deity.bedtimestory.dao;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Deity on 2016/10/24.
 */

public class NewItemEntity extends RealmObject{
    @PrimaryKey
    private long newItemId;//唯一标识符
    /** 标题*/
    @Index
    private String title;
    /** 链接*/
    private String link;
    /**发布日期*/
    private String date;
    /**图片的链接*/
    private String imgLink;
    /**内容*/
    private String content;
    /**类型*/
    private int newsType;

    public long getNewItemId() {
        return newItemId;
    }

    public void setNewItemId(long newItemId) {
        this.newItemId = newItemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }
}
