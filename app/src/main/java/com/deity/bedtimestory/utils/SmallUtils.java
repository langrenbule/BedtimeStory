package com.deity.bedtimestory.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by fengwenhua on 2016/4/12.
 */
public class SmallUtils {

    public static DisplayImageOptions imageOptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.ic_stub)
//                .showImageForEmptyUri(R.drawable.empty_photo)
//                .showImageOnFail(R.drawable.load_fail)
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .considerExifParams(false)//是否考虑JPEG图像EXIF参数(旋转,翻转)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return options;
    }
}
