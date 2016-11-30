package com.tmaproject.malmovieapp.logic;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by TarekMA on 11/25/16.
 * facebook/tarekkma1
 */

public class ResponsiveUi {
    public static int getScale;
    public static int calculateNoOfColumns(Context context,int itemWidthDp,boolean approximate) {
        double multiplier = (isTablet(context)) ? .5 : 1;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        dpWidth*=multiplier;
        float numF = (int) (dpWidth / itemWidthDp);
        int numI = (int) (numF);
        int firstDecimals = (int)(numF-numI)*100;
        int res = numI+((firstDecimals>=65&&approximate)?1:0);
        return (res==0)?1:res;
    }
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
