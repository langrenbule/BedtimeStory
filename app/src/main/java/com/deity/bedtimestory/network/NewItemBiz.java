package com.deity.bedtimestory.network;

import com.deity.bedtimestory.data.Params;
import com.deity.bedtimestory.entity.NewItem;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取新闻数据
 * Created by Deity on 2016/4/13.
 */
public class NewItemBiz {

    public List<NewItem> getNewItems(String baseUrl,int currentPage){
        String correctUrl = baseUrl+"/"+currentPage;
        System.out.println("访问地址" + correctUrl);
        Document document = getUrlDoc(correctUrl);
        List<NewItem> newItemList = new ArrayList<>();
        NewItem newsItem = null;
        Elements units = document.getElementsByClass("unit");
        for (int i = 0; i < units.size(); i++){
            newsItem = new NewItem();
            newsItem.setNewsType(0);
            /**获取标题*/
            Element newsBlock = units.get(i);
            Element newsTitle = newsBlock.getElementsByTag("h1").get(0);
            Element newsTitleData = newsTitle.child(0);
            String title = newsTitleData.text();
            String href = newsTitleData.attr("href");

            newsItem.setLink(href);
            newsItem.setTitle(title);
            /**获取发表时间*/
            Element getTimeBlock = newsBlock.getElementsByTag("h4").get(0);
            Element getTimeElement = getTimeBlock.getElementsByClass("ago").get(0);
            String getTime = getTimeElement.text();

            newsItem.setDate(getTime);
            /**获取描述*/
            Element newsDescriptionBlock = newsBlock.getElementsByTag("dl").get(0);// dl
            Element newsDescriptionElement = newsDescriptionBlock.child(0);// dt
            try
            {// 可能没有图片
                Element img_ele = newsDescriptionElement.child(0);
                String imgLink = img_ele.child(0).attr("src");
                newsItem.setImgLink(imgLink);
            } catch (IndexOutOfBoundsException e){

            }
            Element newsDescriptionChild = newsDescriptionBlock.child(1);// dd
            String content = newsDescriptionChild.text();
            newsItem.setContent(content);
            newItemList.add(newsItem);
        }
        System.out.println("获取数据:" + newItemList.size());
        return newItemList;
    }


    public Document getUrlDoc(String url){
        Document doc = null;
        try {
            Connection conneciton = Jsoup.connect(url);
            conneciton.userAgent(Params.AGENT);
            doc = conneciton.get();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("链接目标地址失败");
            return null;
        }
        return doc;
    }
}
