package com.deity.bedtimestory.network;

import android.text.TextUtils;

import com.deity.bedtimestory.dao.NewBornContentEntity;
import com.deity.bedtimestory.dao.NewBornItemEntity;
import com.deity.bedtimestory.dao.NewBronContent;
import com.deity.bedtimestory.data.Params;
import com.deity.bedtimestory.entity.NewBornContentType;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * http://www.mamabaobao.com/portal.php
 * Created by Deity on 2016/11/8.
 */

public class TechBabyBiz implements DataBiz<NewBornItemEntity,NewBronContent>{
    private static TechBabyBiz instance = new TechBabyBiz();

    private TechBabyBiz(){}

    public static TechBabyBiz getInstance(){
        return instance;
    }

    public String getCorrectImageUrl(String imageUrlFromWebsite){
        String prefix = "http://www.mamabaobao.com/";
        String imageUrlTemp = getImageUrlContent(imageUrlFromWebsite);
        if (!TextUtils.isEmpty(imageUrlTemp)&&!imageUrlTemp.contains(prefix)){
            imageUrlTemp = prefix+imageUrlTemp;
        }
        return imageUrlTemp;
    }

    private String getImageUrlContent(String wait2match){
        Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");
        Matcher matcher = pattern.matcher(wait2match);
        if (matcher.find()){
            return matcher.group();
        }
        return "";
    }

    public List<NewBornItemEntity> getArticleItems(String baseUrl, int currentPage){
        String currentUrl = baseUrl+currentPage;
        Document document = getUrlDoc(currentUrl);
        List<NewBornItemEntity> newItemList = new ArrayList<>();
        NewBornItemEntity entity = null;
        if (null==document) return null;
        Elements elements = document.select("div.main_list_arc").select("div.bottom_list");
        if (null==elements||elements.isEmpty()) return null;
        for (Element element:elements){
            entity = new NewBornItemEntity();
            Element newBornTitle = element.select("div.text_content").select("h3>a").first();
            entity.setNewBornTitle(newBornTitle.text());//获取标题
            entity.setNewBornArticleUrl(newBornTitle.attr("href"));
            Element imageLink = element.select("div.img_content").select("a>div").first();
            entity.setNewBornImageUrl(getCorrectImageUrl(imageLink.attr("style")));//获取图片地址
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

    public NewBronContent getArticleContents(String baseUrl){
        Document document = getUrlDoc(baseUrl);
//        List<NewBornContentEntity> newBornContentEntityList = new ArrayList<>();
        NewBronContent content = new NewBronContent();
        if (null==document) return null;
        Element articleElement = document.select("div.bm.vw").first();
        if (null==articleElement) return null;
        Element elementTitle = articleElement.select("div.h.hm").select("h1.ph").first();
        if (null!=elementTitle) {
            NewBornContentEntity entity = new NewBornContentEntity();
            entity.setNewBornContent(elementTitle.text());
            entity.setNewBornType(NewBornContentType.NEW_BORN_CONTENT_TYPE_TITLE.getCode());
            content.getNewBornContentEntities().add(entity);
            //System.out.println(entity.getNewBornContent());
        }
        Element elementSummary = articleElement.select("div.h.hm").select("p.xg1").first();
        if (null!=elementSummary){
            NewBornContentEntity entity = new NewBornContentEntity();
            entity.setNewBornContent(elementSummary.text());
            entity.setNewBornType(NewBornContentType.NEW_BORN_CONTENT_TYPE_SUMMARY.getCode());
            content.getNewBornContentEntities().add(entity);
            //System.out.println(entity.getNewBornContent());
        }
        Element elementDescription = articleElement.select("div.s").select("div").first();
        if (null!=elementDescription){
            NewBornContentEntity entity = new NewBornContentEntity();
            entity.setNewBornContent(elementDescription.text());
            entity.setNewBornType(NewBornContentType.NEW_BORN_CONTENT_TYPE_DESCRIPTION.getCode());
            content.getNewBornContentEntities().add(entity);
            //System.out.println(entity.getNewBornContent());
        }

//        Elements elementContents = articleElement.select("div.d").select("table>tbody>tr>td#article_content").first().children();
        Elements elementContents = articleElement.getElementById("article_content").children();
        if (null!=elementContents&&!elementContents.isEmpty()) {
            for (Element element:elementContents) {
                NewBornContentEntity entity = new NewBornContentEntity();
                Element elementImage = element.select("div>div>p>a>img").first();
                if (null!=elementImage) {
                    entity.setNewBornContent(elementImage.attr("src"));
                    entity.setNewBornType(NewBornContentType.NEW_BORN_CONTENT_IMAGEURL.getCode());
                    content.getNewBornContentEntities().add(entity);
                    //System.out.println(entity.getNewBornContent());
                }
                if (!TextUtils.isEmpty(element.text())){
                    entity.setNewBornContent(element.text());
                    entity.setNewBornType(NewBornContentType.NEW_BORN_CONTENT_TYPE_CONTENT.getCode());
                    content.getNewBornContentEntities().add(entity);
                    //System.out.println(entity.getNewBornContent());
                }

            }
        }

    ;    return content;
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
            //System.out.println("链接目标地址失败:"+e.getMessage());
            return null;
        }
        return doc;
    }
}
