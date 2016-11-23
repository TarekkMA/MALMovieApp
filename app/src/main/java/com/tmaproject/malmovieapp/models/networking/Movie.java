package com.tmaproject.malmovieapp.models.networking;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tmaproject.malmovieapp.models.networking.request_result.ImagesRequestResult;
import com.tmaproject.malmovieapp.models.networking.request_result.MovieRequestResult;
import com.tmaproject.malmovieapp.models.networking.request_result.ReviewsRequestResult;
import com.tmaproject.malmovieapp.models.networking.request_result.VideosRequestResult;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TarekMA on 10/20/16.
 */

/**************************************
 * HUGE THANKS TO jsonschema2pojo.org *
 **************************************/
public class Movie {

    public enum MovieType {
        LATEST("latest"),
        POPULAR ("popular"),
        UPCOMING("upcoming"),
        TOP_RATED("top_rated"),
        NOW_PLAYING("now_playing"),

        FAVORITES("FAVORITES");

        private final String text;
        private MovieType(final String text) {
            this.text = text;
        }
        public String getTitle(){
            return WordUtils.capitalizeFully(text.replace("_"," "));
        }

        @Override
        public String toString() {
            return text;
        }
    }

    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("belongs_to_collection")
    @Expose
    private Collection belongsToCollection;
    @SerializedName("budget")
    @Expose
    private Integer budget;
    @SerializedName("genres")
    @Expose
    private List<Genre> genres = new ArrayList<Genre>();
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("production_companies")
    @Expose
    private List<ProductionCompany> productionCompanies = new ArrayList<ProductionCompany>();
    @SerializedName("production_countries")
    @Expose
    private List<ProductionCountry> productionCountries = new ArrayList<ProductionCountry>();
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("revenue")
    @Expose
    private Integer revenue;
    @SerializedName("runtime")
    @Expose
    private Integer runtime;
    @SerializedName("spoken_languages")
    @Expose
    private List<SpokenLanguage> spokenLanguages = new ArrayList<SpokenLanguage>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tagline")
    @Expose
    private String tagline;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("videos")
    @Expose
    private VideosRequestResult videos;
    @SerializedName("images")
    @Expose
    private ImagesRequestResult images;
    @SerializedName("similar")
    @Expose
    private MovieRequestResult similar;
    @SerializedName("reviews")
    @Expose
    private ReviewsRequestResult reviews;
    @SerializedName("recommendations")
    @Expose
    private MovieRequestResult recommendations;

    /**
     *
     * @return
     *     The adult
     */
    public Boolean getAdult() {
        return adult;
    }

    /**
     *
     * @param adult
     *     The adult
     */
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    /**
     *
     * @return
     *     The backdropPath
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     *
     * @param backdropPath
     *     The backdrop_path
     */
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    /**
     *
     * @return
     *     The belongsToCollection
     */
    public Collection getBelongsToCollection() {
        return belongsToCollection;
    }

    /**
     *
     * @param belongsToCollection
     *     The belongs_to_collection
     */
    public void setBelongsToCollection(Collection belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    /**
     *
     * @return
     *     The budget
     */
    public Integer getBudget() {
        return budget;
    }

    /**
     *
     * @param budget
     *     The budget
     */
    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    /**
     *
     * @return
     *     The genres
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     *
     * @param genres
     *     The genres
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /**
     *
     * @return
     *     The homepage
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     *
     * @param homepage
     *     The homepage
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    /**
     *
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The imdbId
     */
    public String getImdbId() {
        return imdbId;
    }

    /**
     *
     * @param imdbId
     *     The imdb_id
     */
    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    /**
     *
     * @return
     *     The originalLanguage
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     *
     * @param originalLanguage
     *     The original_language
     */
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    /**
     *
     * @return
     *     The originalTitle
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     *
     * @param originalTitle
     *     The original_title
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     *
     * @return
     *     The overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     *
     * @param overview
     *     The overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     *
     * @return
     *     The popularity
     */
    public Double getPopularity() {
        return popularity;
    }

    /**
     *
     * @param popularity
     *     The popularity
     */
    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    /**
     *
     * @return
     *     The posterPath
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     *
     * @param posterPath
     *     The poster_path
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     *
     * @return
     *     The productionCompanies
     */
    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    /**
     *
     * @param productionCompanies
     *     The production_companies
     */
    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    /**
     *
     * @return
     *     The productionCountries
     */
    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    /**
     *
     * @param productionCountries
     *     The production_countries
     */
    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    /**
     *
     * @return
     *     The releaseDate
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     *
     * @param releaseDate
     *     The release_date
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     *
     * @return
     *     The revenue
     */
    public Integer getRevenue() {
        return revenue;
    }

    /**
     *
     * @param revenue
     *     The revenue
     */
    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    /**
     *
     * @return
     *     The runtime
     */
    public Integer getRuntime() {
        return runtime;
    }

    /**
     *
     * @param runtime
     *     The runtime
     */
    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    /**
     *
     * @return
     *     The spokenLanguages
     */
    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    /**
     *
     * @param spokenLanguages
     *     The spoken_languages
     */
    public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    /**
     *
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     *     The tagline
     */
    public String getTagline() {
        return tagline;
    }

    /**
     *
     * @param tagline
     *     The tagline
     */
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    /**
     *
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     *     The video
     */
    public Boolean getVideo() {
        return video;
    }

    /**
     *
     * @param video
     *     The video
     */
    public void setVideo(Boolean video) {
        this.video = video;
    }

    /**
     *
     * @return
     *     The voteAverage
     */
    public Double getVoteAverage() {
        return voteAverage;
    }

    /**
     *
     * @param voteAverage
     *     The vote_average
     */
    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    /**
     *
     * @return
     *     The voteCount
     */
    public Integer getVoteCount() {
        return voteCount;
    }

    /**
     *
     * @param voteCount
     *     The vote_count
     */
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    /**
     *
     * @return
     *     The videos
     */
    public VideosRequestResult getVideos() {
        return videos;
    }

    /**
     *
     * @param videos
     *     The videos
     */
    public void setVideos(VideosRequestResult videos) {
        this.videos = videos;
    }

    /**
     *
     * @return
     *     The images
     */
    public ImagesRequestResult getImages() {
        return images;
    }

    /**
     *
     * @param images
     *     The images
     */
    public void setImages(ImagesRequestResult images) {
        this.images = images;
    }

    /**
     *
     * @return
     *     The similar
     */
    public MovieRequestResult getSimilar() {
        return similar;
    }

    /**
     *
     * @param similar
     *     The similar
     */
    public void setSimilar(MovieRequestResult similar) {
        this.similar = similar;
    }

    /**
     *
     * @return
     *     The reviews
     */
    public ReviewsRequestResult getReviews() {
        return reviews;
    }

    /**
     *
     * @param reviews
     *     The reviews
     */
    public void setReviews(ReviewsRequestResult reviews) {
        this.reviews = reviews;
    }

    /**
     *
     * @return
     *     The recommendations
     */
    public MovieRequestResult getRecommendations() {
        return recommendations;
    }

    /**
     *
     * @param recommendations
     *     The recommendations
     */
    public void setRecommendations(MovieRequestResult recommendations) {
        this.recommendations = recommendations;
    }
}
