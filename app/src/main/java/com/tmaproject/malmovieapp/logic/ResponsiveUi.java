package com.tmaproject.malmovieapp.logic;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by TarekMA on 11/25/16.
 * facebook/tarekkma1
 */

public class ResponsiveUi {
    public static int getScale;
    public static int calculateNoOfColumns(Context context,int itemWidthDp,boolean approximate) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float numF = (int) (dpWidth / itemWidthDp);
        int numI = (int) (numF);
        int firstDecimals = (int)(numF-numI)*100;
        return numI+((firstDecimals>=65&&approximate)?1:0);
    }
}
