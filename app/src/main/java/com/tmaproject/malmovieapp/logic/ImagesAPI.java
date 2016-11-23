package com.tmaproject.malmovieapp.logic;

import org.apache.commons.lang3.text.WordUtils;

/**
 * Created by TarekMA on 10/23/16.
 */

public class ImagesAPI {

    public static String getYoutubeThumbnail(String videoCode){
        return "http://img.youtube.com/vi/"+videoCode+"/mqdefault.jpg";
    }
}
