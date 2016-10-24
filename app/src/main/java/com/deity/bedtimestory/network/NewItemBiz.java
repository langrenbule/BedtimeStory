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
 * http://v.gxdxw.cn/
 * Created by Deity on 2016/4/13.
 */
public class NewItemBiz {

    public List<NewItem> getArticleItems(String baseUrl, int currentPage){
        String correctUrl = baseUrl+ currentPage;
        System.out.println("访问地址" + correctUrl);

        Document document = getUrlDoc(correctUrl);
        List<NewItem> newItemList = new ArrayList<>();
        NewItem newsItem = null;
        //-----------------------------------------
        Elements articles = document.select("div#content article");
        if (null==articles) return null;
        for (int i=0;i<articles.size();i++){//Element article:articles
            Element article = articles.get(i);
            newsItem = new NewItem();
            /**获取标题及连接地址*/

            Element titleElement = article.select(".entry-header").first().select(".entry-title").first();//.entry-header.entry-title
            if (null==titleElement) continue;
            String title = titleElement.text();
            String href = titleElement.getElementsByTag("a").attr("href");
            newsItem.setLink(href);
            newsItem.setTitle(title);

            System.out.println("文章链接地址:" + href);
            /**图片连接*/
            try {
                Element picElement = article.select(".entry-summary").first().select(".ta-pageimg-right").first().select("a[href]").first();
                Element picUrl = picElement.select("img").first();
                newsItem.setImgLink(picUrl.attr("src"));
            }catch (Exception e){}
            /**概要描述*/
        try {
            Element summary = article.select(".entry-summary").first().select("p").last();
            newsItem.setContent(summary.text());
        }catch (Exception e){}

            /**日期*/
            Element publishData = article.select(".entry-meta").first();
            newsItem.setDate(publishData.text());
            newItemList.add(newsItem);
        }
        //----------------------------------------
        System.out.println("获取数据:" + newItemList.size());
        return newItemList;
    }

//    public List<NewItem> getNewItems(String baseUrl, int currentPage) {
//        String correctUrl = baseUrl + "/" + currentPage;
//        System.out.println("访问地址" + correctUrl);
//        Document document = getUrlDoc(correctUrl);
//        List<NewItem> newItemList = new ArrayList<>();
//        NewItem newsItem = null;
//        Elements units = document.getElementsByClass("unit");
//        for (int i = 0; i < units.size(); i++) {
//            newsItem = new NewItem();
//            newsItem.setNewsType(0);
//            /**获取标题*/
//            Element newsBlock = units.get(i);
//            Element newsTitle = newsBlock.getElementsByTag("h1").get(0);
//            Element newsTitleData = newsTitle.child(0);
//            String title = newsTitleData.text();
//            String href = newsTitleData.attr("href");
//            System.out.println("文章链接地址:"+href);
//            newsItem.setLink(href);
//            newsItem.setTitle(title);
//            /**获取发表时间*/
//            Element getTimeBlock = newsBlock.getElementsByTag("h4").get(0);
//            Element getTimeElement = getTimeBlock.getElementsByClass("ago").get(0);
//            String getTime = getTimeElement.text();
//
//            newsItem.setDate(getTime);
//            /**获取描述*/
//            Element newsDescriptionBlock = newsBlock.getElementsByTag("dl").get(0);// dl
//            Element newsDescriptionElement = newsDescriptionBlock.child(0);// dt
//            try {// 可能没有图片
//                Element img_ele = newsDescriptionElement.child(0);
//                String imgLink = img_ele.child(0).attr("src");
//                newsItem.setImgLink(imgLink);
//            } catch (IndexOutOfBoundsException e) {
//
//            }
//            Element newsDescriptionChild = newsDescriptionBlock.child(1);// dd
//            String content = newsDescriptionChild.text();
//            newsItem.setContent(content);
//            newItemList.add(newsItem);
//        }
//        System.out.println("获取数据:" + newItemList.size());
//        return newItemList;
//    }

//    public NewsDto getNews(String urlStr)throws Exception {
//        NewsDto newsDto = new NewsDto();
//        List<News> newses = new ArrayList();
//        Document doc = getUrlDoc(urlStr);
//        Element detailEle = doc.select(".left .detail").get(0);
//        Element titleEle = detailEle.select("h1.title").get(0);
//        News news = new News();
//        news.setTitle(titleEle.text());
//        news.setType(1);
//        newses.add(news);
//
//        Element summaryEle = detailEle.select("div.summary").get(0);
//        news = new News();
//        news.setSummary(summaryEle.text());
//        newses.add(news);
//
//        Element contentEle = detailEle.select("div.con.news_content").get(0);
//        Elements childrenEle = contentEle.children();
//        for (Element child : childrenEle) {
//            Elements imgEles = child.getElementsByTag("img");
//            if (imgEles.size() > 0) {
//                for (Element imgEle : imgEles) {
//                    if (!imgEle.attr("src").equals("")) {
//                        news = new News();
//                        news.setImageLink(imgEle.attr("src"));
//                        newses.add(news);
//                    }
//                }
//            }
//            imgEles.remove();
//            if (!child.text().equals("")) {
//                news = new News();
//                news.setType(3);
//                try {
//                    if (child.children().size() == 1) {
//                        Element cc = child.child(0);
//                        if (cc.tagName().equals("b")) {
//                            news.setType(5);
//                        }
//                    }
//                } catch (IndexOutOfBoundsException e) {
//                    e.printStackTrace();
//                }
//                news.setContent(child.outerHtml());
//                newses.add(news);
//            }
//        }
//        newsDto.setNewses(newses);
//        return newsDto;
//    }

    /**
     * 选择器：
     选择器概要（Selector overview）
     Tagname：通过标签查找元素（例如：a）
     ns|tag：通过标签在命名空间查找元素，例如：fb|name查找<fb:name>元素
     #id：通过ID查找元素，例如#logo
     .class：通过类型名称查找元素，例如.masthead
     [attribute]：带有属性的元素，例如[href]
     [^attr]：带有名称前缀的元素，例如[^data-]查找HTML5带有数据集（dataset）属性的元素
     [attr=value]：带有属性值的元素，例如[width=500]
     [attr^=value]，[attr$=value]，[attr*=value]：包含属性且其值以value开头、结尾或包含value的元素，例如[href*=/path/]
     [attr~=regex]：属性值满足正则表达式的元素，例如img[src~=(?i)\.(png|jpe?g)]
     *：所有元素，例如*
     选择器组合方法
     el#id:：带有ID的元素ID，例如div#logo
     el.class：带类型的元素，例如. div.masthead
     el[attr]：包含属性的元素，例如a[href]
     任意组合：例如a[href].highlight
     ancestor child：继承自某祖（父）元素的子元素，例如.body p查找“body”块下的p元素
     parent > child：直接为父元素后代的子元素，例如: div.content > pf查找p元素，body > * 查找body元素的直系子元素
     siblingA + siblingB：查找由同级元素A前导的同级元素，例如div.head + div
     siblingA ~ siblingX：查找同级元素A前导的同级元素X例如h1 ~ p
     el, el, el：多个选择器组合，查找匹配任一选择器的唯一元素，例如div.masthead, div.logo
     伪选择器（Pseudo selectors）
     :lt(n)：查找索引值（即DOM树中相对于其父元素的位置）小于n的同级元素，例如td:lt(3)
     :gt(n)：查找查找索引值大于n的同级元素，例如div p:gt(2)
     :eq(n) ：查找索引值等于n的同级元素，例如form input:eq(1)
     :has(seletor)：查找匹配选择器包含元素的元素，例如div:has(p)
     :not(selector)：查找不匹配选择器的元素，例如div:not(.logo)
     :contains(text)：查找包含给定文本的元素，大小写铭感，例如p:contains(jsoup)
     :containsOwn(text)：查找直接包含给定文本的元素
     :matches(regex)：查找其文本匹配指定的正则表达式的元素，例如div:matches((?i)login)
     :matchesOwn(regex)：查找其自身文本匹配指定的正则表达式的元素
     注意：上述伪选择器是0-基数的，亦即第一个元素索引值为0，第二个元素index为1等
     * @param urlStr
     * @return
     * @throws Exception
     */
//    public NewsDto getNews(String urlStr)throws Exception {
//        NewsDto newsDto = new NewsDto();
//        List<News> newses = new ArrayList();
//        Document doc = getUrlDoc(urlStr);
//        Element detailEle = doc.select(".left .detail").get(0);
//        Element titleEle = detailEle.select("h1.title").get(0);
//        News news = new News();
//        news.setTitle(titleEle.text());
//        news.setType(1);
//        newses.add(news);
//
//        Element summaryEle = detailEle.select("div.summary").get(0);
//        news = new News();
//        news.setSummary(summaryEle.text());
//        newses.add(news);
//
//        Element contentEle = detailEle.select("div.con.news_content").get(0);
//        Elements childrenEle = contentEle.children();
//        for (Element child : childrenEle) {
//            Elements imgEles = child.getElementsByTag("img");
//            if (imgEles.size() > 0) {
//                for (Element imgEle : imgEles) {
//                    if (!imgEle.attr("src").equals("")) {
//                        news = new News();
//                        news.setImageLink(imgEle.attr("src"));
//                        newses.add(news);
//                    }
//                }
//            }
//            imgEles.remove();
//            if (!child.text().equals("")) {
//                news = new News();
//                news.setType(3);
//                try {
//                    if (child.children().size() == 1) {
//                        Element cc = child.child(0);
//                        if (cc.tagName().equals("b")) {
//                            news.setType(5);
//                        }
//                    }
//                } catch (IndexOutOfBoundsException e) {
//                    e.printStackTrace();
//                }
//                news.setContent(child.outerHtml());
//                newses.add(news);
//            }
//        }
//
//        newsDto.setNewses(newses);
//        return newsDto;
//    }

    public NewsDto getNews(String urlStr)throws Exception {
        NewsDto newsDto = new NewsDto();
        List<News> newses = new ArrayList();
        Document doc = getUrlDoc(urlStr);
        Element detailEle = doc.getElementsByTag("article").get(0);

        Element titleEle = detailEle.select(".entry-header").first().select(".entry-title").first();
        News news = new News();
        news.setTitle(titleEle.text());
        news.setType(1);
        newses.add(news);

        Element summaryEle = detailEle.select(".entry-header").first().select(".below-title-meta").first().select(".adt").first();
        news = new News();
        news.setSummary(summaryEle.text());
        newses.add(news);

        Elements childrenEle = detailEle.select(".entry-content").first().children();
        for (Element child : childrenEle) {
            try {
                Element imgEle = child.select("div.article_img").first().select("img").first();
                if (!imgEle.attr("src").equals("")) {
                    news = new News();
                    news.setImageLink(imgEle.attr("src"));
                    newses.add(news);
                }
            }catch (Exception e){}
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
                news.setContent(child.outerHtml().trim());
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
