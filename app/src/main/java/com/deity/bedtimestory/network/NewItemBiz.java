package com.deity.bedtimestory.network;

import com.deity.bedtimestory.data.Params;
import com.deity.bedtimestory.entity.NewItem;
import com.deity.bedtimestory.entity.News;
import com.deity.bedtimestory.entity.NewsDto;

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

    public List<NewItem> getNewItems(String baseUrl, int currentPage) {
        String correctUrl = baseUrl + "/" + currentPage;
        System.out.println("访问地址" + correctUrl);
        Document document = getUrlDoc(correctUrl);
        List<NewItem> newItemList = new ArrayList<>();
        NewItem newsItem = null;
        Elements units = document.getElementsByClass("unit");
        for (int i = 0; i < units.size(); i++) {
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
            try {// 可能没有图片
                Element img_ele = newsDescriptionElement.child(0);
                String imgLink = img_ele.child(0).attr("src");
                newsItem.setImgLink(imgLink);
            } catch (IndexOutOfBoundsException e) {

            }
            Element newsDescriptionChild = newsDescriptionBlock.child(1);// dd
            String content = newsDescriptionChild.text();
            newsItem.setContent(content);
            newItemList.add(newsItem);
        }
        System.out.println("获取数据:" + newItemList.size());
        return newItemList;
    }

    public NewsDto getNews(String urlStr)throws Exception {
        NewsDto newsDto = new NewsDto();
        List<News> newses = new ArrayList();
        Document doc = getUrlDoc(urlStr);
        Element detailEle = doc.select(".left .detail").get(0);
        Element titleEle = detailEle.select("h1.title").get(0);
        News news = new News();
        news.setTitle(titleEle.text());
        news.setType(1);
        newses.add(news);

        Element summaryEle = detailEle.select("div.summary").get(0);
        news = new News();
        news.setSummary(summaryEle.text());
        newses.add(news);

        Element contentEle = detailEle.select("div.con.news_content").get(0);
        Elements childrenEle = contentEle.children();
        for (Element child : childrenEle) {
            Elements imgEles = child.getElementsByTag("img");
            if (imgEles.size() > 0) {
                for (Element imgEle : imgEles) {
                    if (!imgEle.attr("src").equals("")) {
                        news = new News();
                        news.setImageLink(imgEle.attr("src"));
                        newses.add(news);
                    }
                }
            }
            imgEles.remove();
            if (!child.text().equals("")) {
                news = new News();
                news.setType(3);
                try {
                    if (child.children().size() == 1) {
                        Element cc = child.child(0);
                        if (cc.tagName().equals("b")) {
                            news.setType(5);
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                news.setContent(child.outerHtml());
                newses.add(news);
            }
        }
        newsDto.setNewses(newses);
        return newsDto;
    }


    public Document getUrlDoc(String url) {
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
