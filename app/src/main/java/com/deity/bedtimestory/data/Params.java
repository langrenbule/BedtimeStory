package com.deity.bedtimestory.data;

import android.util.SparseArray;

/**
 * Created by Deity on 2016/4/13.
 */
public class Params {
    public static final String AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79";
    public static final String LOAD_REFRESH="load_refresh";
    public static final String LOAD_MORE="load_more";

    public static enum NewType{
        NEW_RENWU(0,TargetUrl.url_renwu),
        NEW_RENSHENG(1,TargetUrl.url_rensheng),
        NEW_QINGGAN(2,TargetUrl.url_qinggan),
        NEW_CHENGZHANG(3,TargetUrl.url_chengzhang),
        NEW_CHUSHI(4,TargetUrl.url_chushi),
        NEW_ZHICHANG(5,TargetUrl.url_zhichang),
        NEW_SHIYE(6,TargetUrl.url_shiye),
        NEW_QINGCHUN(7,TargetUrl.url_qingchun);

        NewType(int code,String destUrl){
            this.code = code;
            this.destUrl = destUrl;
        }
        private int code;
        private String destUrl;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDestUrl() {
            return destUrl;
        }

        public void setDestUrl(String destUrl) {
            this.destUrl = destUrl;
        }

        public static NewType whichOne(String destUrl){
            for (NewType newType:NewType.values()){
                if (newType.getDestUrl().equals(destUrl)){
                    return newType;
                }
            }
            return NewType.NEW_RENWU;
        }
    }
    /**网络地址*/
    public static class TargetUrl{
        public static String url_renwu="http://www.85nian.net/renwu/page/";
        public static String url_rensheng="http://www.85nian.net/rensheng/page/";
        public static String url_qinggan="http://www.85nian.net/qinggan/page/";
        public static String url_chengzhang="http://www.85nian.net/chengzhang/page/";
        public static String url_chushi="http://www.85nian.net/chushi/page/";
        public static String url_zhichang="http://www.85nian.net/zhichang/page/";
        public static String url_shiye="http://www.85nian.net/shiye/page/";
        public static String url_qingchun="http://www.85nian.net/qingchun/page/";

        public static SparseArray<String> mUrl = new SparseArray<>();
        static{
            mUrl.put(0,url_renwu);
            mUrl.put(1,url_rensheng);
            mUrl.put(2,url_qinggan);
            mUrl.put(3,url_chengzhang);
            mUrl.put(4,url_chushi);
            mUrl.put(5,url_zhichang);
            mUrl.put(6,url_shiye);
            mUrl.put(7,url_qingchun);
        }
    }
}
