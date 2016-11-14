package com.deity.bedtimestory.entity;

/**
 * 文章内容的类型
 * Created by Deity on 2016/11/9.
 */

public enum NewBornItemType {
    十月怀胎(0,"http://www.mamabaobao.com/portal.php?mod=list&catid=104&page="),
    一朝分娩(1,"http://www.mamabaobao.com/portal.php?mod=list&catid=105&page="),
    准爸爸读本(2,"http://www.mamabaobao.com/portal.php?mod=list&catid=106&page="),
    孕前准备(3,"http://www.mamabaobao.com/portal.php?mod=list&catid=107&page="),
    孕早期(4,"http://www.mamabaobao.com/portal.php?mod=list&catid=108&page="),
    孕中晚期(5,"http://www.mamabaobao.com/portal.php?mod=list&catid=109&page="),
    六月宝宝(6,"http://www.mamabaobao.com/portal.php?mod=list&catid=95&page="),
    周年宝宝(7,"http://www.mamabaobao.com/portal.php?mod=list&catid=96&page="),
    幼儿心理(8,"http://www.mamabaobao.com/portal.php?mod=list&catid=97&page="),
    小五神童(9,"http://www.mamabaobao.com/portal.php?mod=list&catid=99&page="),
    生活照顾(10,"http://www.mamabaobao.com/portal.php?mod=list&catid=101&page="),
    营养健康(11,"http://www.mamabaobao.com/portal.php?mod=list&catid=102&page="),
    亲子共读(12,"http://www.mamabaobao.com/portal.php?mod=list&catid=119&page=");

    private int code;
    private String targetUrl;

    NewBornItemType(int code,String targetUrl){
        this.code = code;
        this.targetUrl = targetUrl;
    }

    public static NewBornItemType whichNewBornContentType(int code){
        for (NewBornItemType type: NewBornItemType.values()){
            if (type.code==code){
                return type;
            }
        }
        return NewBornItemType.十月怀胎;
    }


    public String getTargetUrl() {
        return targetUrl;
    }

    public int getCode() {
        return code;
    }
}
