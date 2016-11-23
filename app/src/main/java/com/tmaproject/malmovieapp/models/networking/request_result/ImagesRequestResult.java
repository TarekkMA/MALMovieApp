package com.tmaproject.malmovieapp.models.networking.request_result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tmaproject.malmovieapp.models.networking.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarekkma on 10/23/16.
 */

public class ImagesRequestResult {
    @SerializedName("backdrops")
    @Expose
    private List<Image> backdrops = new ArrayList<Image>();
    @SerializedName("posters")
    @Expose
    private List<Image> posters = new ArrayList<Image>();

    /**
     *
     * @return
     *     The backdrops
     */
    public List<Image> getBackdrops() {
        return backdrops;
    }

    /**
     *
     * @param backdrops
     *     The backdrops
     */
    public void setBackdrops(List<Image> backdrops) {
        this.backdrops = backdrops;
    }

    /**
     *
     * @return
     *     The posters
     */
    public List<Image> getPosters() {
        return posters;
    }

    /**
     *
     * @param posters
     *     The posters
     */
    public void setPosters(List<Image> posters) {
        this.posters = posters;
    }

}
