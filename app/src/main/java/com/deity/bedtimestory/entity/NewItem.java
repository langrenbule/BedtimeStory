package com.deity.bedtimestory.entity;

/**
 * 网络上抓取的内容实体类
 * Created by fengwenhua on 2016/4/12.
 */
public class NewItem {

    private int id;
    /**
     * 标题
     */
    private String title;
    /**
     * 链接
     */
    private String link;
    /**
     * 发布日期
     */
    private String date;
    /**
     * 图片的链接
     */
    private String imgLink;
    /**
     * 内容
     */
    private String content;

    /**
     * 类型
     *
     */
    private int newsType;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return super.toString();
    }
}
