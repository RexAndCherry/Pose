package com.program.vjzuo.pose.utils;

import android.widget.ImageView;

import com.umeng.comm.core.imageloader.UMImageLoader;
import com.umeng.comm.core.sdkmanager.ImageLoaderManager;

/**
 * Created by vj.zuo on 2016/4/21.
 */
public class ImageUtils {

    public static void displayImgByUrl(String url, ImageView imageView) {
        ((UMImageLoader) ImageLoaderManager.getInstance().getCurrentSDK()).displayImage(url, imageView);
    }
}
