package org.anchorer.l.c01;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Anchorer/duruixue on 2015/9/28.
 */
public class Utils {
    public static int dp2px(Context context, float dpValue) {
        if(context != null) {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int)(dpValue * scale + 0.5F);
        } else {
            return (int)dpValue;
        }
    }

    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
    }

    public static int getScreenWidthPixels(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    public static int getScreenWidthDps(Activity activity) {
        return px2dp(activity, (float)getScreenWidthPixels(activity));
    }

    public static int getScreenHeightPixels(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    public static int getScreenHeightDps(Activity activity) {
        return px2dp(activity, (float)getScreenHeightPixels(activity));
    }
}
