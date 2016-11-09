package com.deity.bedtimestory.entity;

/**
 * 文章内容的类型
 * Created by Deity on 2016/11/9.
 */

public enum NewBornItemType {
    NEW_BORN_CONTENT_TYPE_10月怀胎(0),
    NEW_BORN_CONTENT_TYPE_一朝分娩(1),
    NEW_BORN_CONTENT_TYPE_准爸爸读本(2),
    NEW_BORN_CONTENT_TYPE_孕前准备(3),
    NEW_BORN_CONTENT_TYPE_孕早期(4),
    NEW_BORN_CONTENT_TYPE_孕中晚期(5),
    NEW_BORN_CONTENT_TYPE_6月内(6),
    NEW_BORN_CONTENT_TYPE_12月内(7),
    NEW_BORN_CONTENT_TYPE_幼儿心理(8),
    NEW_BORN_CONTENT_TYPE_1到4岁(9),
    NEW_BORN_CONTENT_TYPE_生活照顾(10),
    NEW_BORN_CONTENT_TYPE_营养健康(11),
    NEW_BORN_CONTENT_TYPE_亲子共读(12);

    private int code;

    NewBornItemType(int code){
        this.code = code;
    }

    public static NewBornItemType whichNewBornContentType(int code){
        for (NewBornItemType type: NewBornItemType.values()){
            if (type.code==code){
                return type;
            }
        }
        return NewBornItemType.NEW_BORN_CONTENT_TYPE_10月怀胎;
    }



}
