package com.deity.bedtimestory.data;

/**
 * Created by Deity on 2016/4/13.
 */
public class Params {
    public static final String AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79";


    /**网络地址*/
    public static enum TargetUrl{

        STORY_MAGAZINE(0,"http://www.85nian.net/renwu/page/"),//读者图书
        BABY_BED(1,"http://cloud.csdn.net/cloud"),
        STORY_PUNSTER(2,"http://cloud.csdn.net/cloud");

        public int newType;
        public String urlStr;

        TargetUrl(int newType,String urlStr){
            this.newType = newType;
            this.urlStr = urlStr;
        }
    }
}
