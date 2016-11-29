package com.tmaproject.malmovieapp.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.models.networking.Movie;
import com.tmaproject.malmovieapp.views.viewholder.BaseViewHolder;
import com.tmaproject.malmovieapp.views.viewholder.MovieDumbVH;
import com.tmaproject.malmovieapp.views.viewholder.MoviePosterVH;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TarekMA on 10/21/16.
 * facebook/tarekkma1
 */

public class MovieListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private boolean isThereMoreToLoad = true;
    private int currantPage = 0;
    private List<Movie> moviesList = new ArrayList<>();
    private InfiniteRecyclerView loadMoreCallback;

    private static final int MOVIE = 177;
    private static final int LOADING = 905;

    public MovieListAdapter(InfiniteRecyclerView loadMoreCallback) {
        this.loadMoreCallback = loadMoreCallback;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOADING)
            return new MovieDumbVH(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading, parent, false));
        else return new MoviePosterVH(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_movie, parent, false));
    }

    public List<Movie> getMoviesList() {
        return moviesList;
    }

    public int getCurrantPage() {
        return currantPage;
    }

    public void addPage(List<Movie> newList) {
        if(newList == null||newList.isEmpty()){
            isThereMoreToLoad = false;
            notifyItemRemoved(moviesList.size()+1);
            return;
        }else {
            currantPage++;
            int oldLastIndex = moviesList.size() - 1;
            moviesList.addAll(newList);
            notifyItemRangeChanged(oldLastIndex, moviesList.size() - 1);
        }
    }

    public void setData(List<Movie> newList,int currantPage) {
        moviesList = newList;
        this.currantPage =currantPage;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (getItemViewType(position) == LOADING) {
            holder.itemView.setVisibility(View.GONE);
            if(position==0)return;
            if (isThereMoreToLoad){
                holder.itemView.setVisibility(View.VISIBLE);
                loadMoreCallback.loadPage(currantPage+1);
            }
        } else {
            holder.bind(moviesList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size() + ((isThereMoreToLoad)?1:0) /*For loading indicator*/;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == moviesList.size()) ? LOADING : MOVIE;
    }

    public interface InfiniteRecyclerView {
        void loadPage(int pageNum);
    }

}
