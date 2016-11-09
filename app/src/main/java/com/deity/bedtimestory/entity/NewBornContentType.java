package com.deity.bedtimestory.entity;

/**
 * 内容类型
 * Created by Deity on 2016/11/9.
 */

public enum  NewBornContentType {
    /**图片URL地址*/
    NEW_BORN_CONTENT_IMAGEURL(0),
    /**描述*/
    NEW_BORN_CONTENT_TYPE_DESCRIPTION(1),
    /**摘要*/
    NEW_BORN_CONTENT_TYPE_SUMMARY(2),
    /**正文*/
    NEW_BORN_CONTENT_TYPE_CONTENT(3),
    /**标题*/
    NEW_BORN_CONTENT_TYPE_TITLE(4);

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    NewBornContentType(int code){
        this.code = code;
    }
}
