package com.tmaproject.malmovieapp.models.networking.request_result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tmaproject.malmovieapp.models.networking.Video;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarekkma on 10/21/16.
 */
@Parcel(Parcel.Serialization.BEAN)
public class VideosRequestResult {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<Video> results = new ArrayList<Video>();

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The results
     */
    public List<Video> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<Video> results) {
        this.results = results;
    }
}
