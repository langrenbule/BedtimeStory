package com.deity.bedtimestory.network;

import com.deity.bedtimestory.data.Params;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Deity on 2016/11/7.
 */

public class DuoWanBiz {

    private static DuoWanBiz instance = new DuoWanBiz();

//    public List<NewItem> getArticleItems(String baseUrl, int currentPage){
//
//    }

    public Document getUrlDoc(String url) {
        Document doc = null;
        try {
            Connection conneciton = Jsoup.connect(url);
            conneciton.timeout(10*1000);//10秒超时
            conneciton.userAgent(Params.AGENT);
            doc = conneciton.get();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("链接目标地址失败:"+e.getMessage());
            return null;
        }
        return doc;
    }
}
