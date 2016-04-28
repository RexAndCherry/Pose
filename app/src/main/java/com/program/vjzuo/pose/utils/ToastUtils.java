package com.program.vjzuo.pose.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by vj.zuo on 2016/4/21.
 */
public class ToastUtils {

    public static void showShort(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static void showShort(Context context, int msgId) {
        Toast.makeText(context, msgId, Toast.LENGTH_SHORT).show();
    }
}
