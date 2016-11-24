package com.tmaproject.malmovieapp.logic;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by TarekMA on 11/24/16.
 * facebook/tarekkma1
 */

public class IntentHelper {
    public static void watchYoutubeVideo(Context c, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            c.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            c.startActivity(webIntent);
        }
    }
}
