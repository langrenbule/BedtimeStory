package com.deity.bedtimestory.network;


import java.util.List;

/**
 * 数据获取基类
 * Created by Deity on 2016/11/13.
 */

public interface DataBiz<T,S> {

    List<T> getArticleItems(String baseUrl, int currentPage);

    S getArticleContents(String urlStr)throws Exception;



}
