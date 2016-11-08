package com.deity.bedtimestory.network;

import com.deity.bedtimestory.dao.NewBornItemEntity;
import com.deity.bedtimestory.data.Params;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * http://www.mamabaobao.com/portal.php
 * Created by Deity on 2016/11/8.
 */

public class TechBabyBiz {
    private static TechBabyBiz instance = new TechBabyBiz();

    private TechBabyBiz(){}

    public static TechBabyBiz getInstance(){
        return instance;
    }

    public List<NewBornItemEntity> getArticleItems(String baseUrl, int currentPage){
        String currentUrl = baseUrl+currentPage;
        Document document = getUrlDoc(currentUrl);
        List<NewBornItemEntity> newItemList = new ArrayList<>();
        NewBornItemEntity entity = null;
        Elements elements = document.select("div.main_list_arc").select("div.bottom_list");
        if (null==elements||elements.isEmpty()) return null;
        for (Element element:elements){
            entity = new NewBornItemEntity();
            Element newBornTitle = element.select("div.text_content").select("h3>a").first();
            entity.setNewBornTitle(newBornTitle.text());//获取标题
            entity.setNewBornArticleUrl(newBornTitle.attr("href"));
            Element imageLink = element.select("div.img_content").select("a>div").first();
            entity.setNewBornImageUrl(imageLink.attr("style"));//获取图片地址
            Element newBronDescription = element.select("div.text_content").select("p").first();
            entity.setNewBornDescription(newBronDescription.text());
            Element newBronData = element.select("div.text_content").select("label").first();
            entity.setNewBornData(newBronData.text());
            newItemList.add(entity);
            System.out.println("title>>>"+entity.getNewBornTitle()+
                    "\n ArticleUrl>>>"+entity.getNewBornArticleUrl()+
                    "\n data>>>"+entity.getNewBornData()+
                    "\n desc>>>"+entity.getNewBornDescription()+
                    "\n imageUrl>>>"+entity.getNewBornImageUrl());
        }
        return newItemList;
    }


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
