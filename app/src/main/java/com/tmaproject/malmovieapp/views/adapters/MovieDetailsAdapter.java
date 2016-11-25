package com.tmaproject.malmovieapp.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.models.networking.Movie;
import com.tmaproject.malmovieapp.views.viewholder.BaseViewHolder;
import com.tmaproject.malmovieapp.views.viewholder.MovieDumbVH;
import com.tmaproject.malmovieapp.views.viewholder.MovieDetailsVH;
import com.tmaproject.malmovieapp.views.viewholder.MovieHeaderVH;
import com.tmaproject.malmovieapp.views.viewholder.MovieImageVH;
import com.tmaproject.malmovieapp.views.viewholder.MovieReviewVH;
import com.tmaproject.malmovieapp.views.viewholder.MovieVideosVH;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarekkma on 10/21/16.
 */

public class MovieDetailsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int POSTER = 835;
    public static final int BACKDROP = 396;
    public static final int REVIEWS_HEADER = 770;
    public static final int REVIEW = 654;
    public static final int LOADING = 784;

    boolean isThereMoreToLoad = true;
    boolean fullDataArrived = false;
    List<Integer> viewsOrder = new ArrayList<>();
    Movie movie;


    public MovieDetailsAdapter(List<Integer> viewsOrder, Movie movie) {
        this.viewsOrder = viewsOrder;
        this.movie = movie;
    }

    public void fulfilData(Movie movie){
        this.movie = movie;
        viewsOrder.add(R.layout.item_movie_video);
        viewsOrder.add(POSTER);
        viewsOrder.add(BACKDROP);
        int loadedReviewCount = movie.getReviews().getResults().size();
        if (loadedReviewCount <= movie.getReviews().getTotalResults()) isThereMoreToLoad = false;
        for (int i = 0; i < loadedReviewCount; i++) {
            viewsOrder.add(REVIEW);
        }
        fullDataArrived = true;
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case R.layout.item_movie_details:
                return new MovieDetailsVH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_details,parent,false));
            case R.layout.item_movie_video:
                return new MovieVideosVH(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_horizontal_list,parent,false));
            case POSTER:
                return new MovieImageVH(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_horizontal_list,parent,false),true);
            case BACKDROP:
                return new MovieImageVH(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_horizontal_list,parent,false),false);
            case REVIEWS_HEADER:
                return new MovieHeaderVH(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_genre_header,parent,false));
            case REVIEW:
                return new MovieReviewVH(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_movie_review,parent,false));
            case LOADING:
                return new MovieDumbVH(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_loading,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case R.layout.item_movie_details:
                holder.bind(movie);
                break;
            case R.layout.item_movie_video:
                holder.bind(movie);
                break;
            case POSTER:
                holder.bind(movie);
                break;
            case BACKDROP:
                holder.bind(movie);
                break;
            case REVIEWS_HEADER:
                holder.bind("Reviews");
                break;
            case REVIEW:
                int firstReviewPos = viewsOrder.indexOf(REVIEW);
                holder.bind(movie.getReviews().getResults().get(position-firstReviewPos));
                break;
            case LOADING:
                //Skip loading reviews if full movie data didn't arrive
                if(fullDataArrived == false)return;
                //Load More reviews if it is visible
                loadMoreReviews();
                break;
        }
    }

    private void loadMoreReviews(){
        isThereMoreToLoad = false;
        notifyItemRemoved(viewsOrder.size()); // this is more than the last index in viewsOrder by 1
        //                                       that refers to the loading view
        //TODO:Implement this, but first find that movie that have more than one page
        /**
         * load more reviews by the next page (currentPage+1)
         * add REVIEW view type to viewsOrder list
         * add review objects to currunt movie list
         * tell adapter to update this range
         */
    }

    @Override
    public int getItemCount() {
        return viewsOrder.size()+(isThereMoreToLoad?1:0);
    }

    @Override
    public int getItemViewType(int position) {
        if(position>=viewsOrder.size()){
            return LOADING;
        }else {
            return viewsOrder.get(position);
        }
    }


}
