package com.deity.bedtimestory;

import com.deity.bedtimestory.network.TechBabyBiz;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testGetNewBornActicle(){
        String baseUrl = "http://www.mamabaobao.com/portal.php?mod=list&catid=95&page=";
        TechBabyBiz.getInstance().getArticleItems(baseUrl,2);
    }
    @Test
    public void testGetNewBornArticleContents(){
        String baseUrl = "http://www.mamabaobao.com/article-2984-1.html";
        TechBabyBiz.getInstance().getArticleContents(baseUrl);
    }
}