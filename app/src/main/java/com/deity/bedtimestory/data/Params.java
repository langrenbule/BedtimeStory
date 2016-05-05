package com.deity.bedtimestory.data;

import android.util.SparseArray;

/**
 * Created by Deity on 2016/4/13.
 */
public class Params {
    public static final String AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79";
    public static final String LOAD_REFRESH="load_refresh";
    public static final String LOAD_MORE="load_more";

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

    //腾讯广告相关参数
    public static final String APPID = "1105304677";
    public static final String SplashPosID = "5080714150321210";
}
