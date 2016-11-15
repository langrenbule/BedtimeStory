package com.deity.bedtimestory;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.deity.bedtimestory.network.TechBabyBiz;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

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


    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }


}