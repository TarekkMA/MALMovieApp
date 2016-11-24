package com.tmaproject.malmovieapp.views.viewholder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.logic.ImagesAPI;
import com.tmaproject.malmovieapp.logic.IntentHelper;
import com.tmaproject.malmovieapp.models.networking.Image;
import com.tmaproject.malmovieapp.models.networking.Movie;
import com.tmaproject.malmovieapp.models.networking.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TarekMA on 10/30/16.
 */

public class MovieVideosVH extends BaseViewHolder<Movie> {
    private static final String TAG = "MovieVideosVH";
    private TextView header;
    private RecyclerView list;
    public MovieVideosVH(View v) {
        super(v);
        header = (TextView)v.findViewById(R.id.horizontal_list_header);
        list = (RecyclerView) v.findViewById(R.id.horizontal_list);
    }

    @Override
    public void bind(Movie data) {
        header.setText("Trailers");
        list.setLayoutManager(new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.HORIZONTAL,false));
        Log.d(TAG, "bind: ");

        list.setAdapter(new RecyclerView.Adapter() {
            List<Video> videos = (data.getVideos()==null)?new ArrayList<Video>():data.getVideos().getResults();
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_video,parent,false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((VH)holder).bind(videos.get(position));
            }

            @Override
            public int getItemCount() {
                return videos.size();
            }

            class VH extends BaseViewHolder<Video>{
                ImageView videoImg;
                TextView videoText;
                public VH(View v) {
                    super(v);
                    videoImg = (ImageView)v.findViewById(R.id.movie_video_img);
                    videoText = (TextView) v.findViewById(R.id.movie_video_text);
                }

                @Override
                public void bind(Video data) {
                    Picasso.with(itemView.getContext())
                            .load(ImagesAPI.getYoutubeThumbnail(data.getKey()))
                            .placeholder(R.drawable.placeholder_backdrop)
                            .into(videoImg);
                    videoText.setText(data.getName());
                    itemView.setOnClickListener(view -> IntentHelper.watchYoutubeVideo(itemView.getContext(),data.getKey()));
                }
            }
        });
    }
}
